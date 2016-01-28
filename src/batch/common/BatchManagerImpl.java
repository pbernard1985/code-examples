package gov.usps.cmrt.batch.common;

import gov.usps.cmrt.datalayer.equipment.bean.EquipmentBean;
import gov.usps.cmrt.datalayer.equipment.service.EquipmentService;
import gov.usps.cmrt.datalayer.notifications.service.NotificationsService;
import gov.usps.cmrt.datalayer.personnel.bean.PersonnelBean;
import gov.usps.cmrt.datalayer.personnel.service.PersonnelService;
import gov.usps.cmrt.datalayer.positions.bean.PositionsBean;
import gov.usps.cmrt.datalayer.positions.service.PositionsService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("batchManager")
public class BatchManagerImpl implements BatchManager {
	private static Logger LOGGER = Logger.getLogger(BatchManagerImpl.class);

	public int returnCode = 0;

	@Autowired
	protected PositionsService positionsService;
	@Autowired
	protected NotificationsService notificationsService;
	@Autowired
	protected PersonnelService personnelService;
	@Autowired
	protected EquipmentService equipmentService;

	// if position that was previously assigned position is vacant for 14 or 28 days,
	// create notification
	@Override
	public void vacantNotificationsBatch() {
		LOGGER.debug("BatchManagerImpl.vacantNotificationsBatch()");
		// get all  notifications that are vacant for 14 or 28 days
		List<Integer> vacantPosIds = positionsService.getVacantPositions();
		// find who was the last personnel assigned to vacant notifications from
		// audit table
		if (vacantPosIds != null && !vacantPosIds.isEmpty()) {
			List<PositionsBean> posEinAudits = positionsService
					.getPositionEinAudit(vacantPosIds);
			// generate notification
			if (posEinAudits != null && posEinAudits.size() > 0) {
				for (PositionsBean posEin : posEinAudits) {
					try {
						 notificationsService.addVacantNotification(posEin.getEin(), posEin.getPositionId(),
						  "batchProcess");
						 
					} catch (Exception e) {
						LOGGER.error("Error creating notification in vacantNotificationsBatch for position ID "
								+ posEin.getPositionId()
								+ "and EIN "
								+ posEin.getEin() + ": " + e.getMessage());
					}
				}
			}
		}

	}

	// Vacate any personnel assignments who's assignment end date is <= today
	// If no successor is present, generate a Vacant Notification
	// If successor is present, generate a New Personnel Assignment Notification
	@Override
	public void vacateEndedPersonnelAssignments() {
        LOGGER.debug("BatchManagerImpl.vacateEndedPersonnelAssignments()");
        List<PersonnelBean> endedAssignments = personnelService.getAllEndedPersonnelAssignments();
        if (endedAssignments.size() > 0) {
               LOGGER.debug("Ended Personnel Assignments Found : " + endedAssignments.size());
               int vacateCount = personnelService.vacateEndedPersonnelAssignments(endedAssignments);
               List<EquipmentBean> equipmentList = new ArrayList<EquipmentBean>();
               EquipmentBean eBean = new EquipmentBean();
               for(PersonnelBean pBean : endedAssignments) {
                     eBean.setEin(pBean.getEin());
                     eBean.setPositionId(pBean.getPositionId());
                     equipmentList.add(eBean);
               }
              equipmentService.clearEquipmentDetails(equipmentList);
               if (vacateCount == endedAssignments.size()) {
                     for (PersonnelBean p : endedAssignments) {
                            Integer positionId = p.getPositionId();
                            String ein = p.getEin();
                            PersonnelBean successor = personnelService.getSuccessor(positionId);
                            if (successor == null) {
                                   notificationsService.addVacantNotification(ein, positionId, "batchProcess");
                                   LOGGER.debug("No Successor Found.  Vacant Notification Created for Position ID : " + positionId);
                                   returnCode = 2;
                            }
                            else {
                                   notificationsService.addNewPersonnelNotification(successor.getEin(), 
                                                 successor.getPositionId(), "batchProcess");
                                   LOGGER.debug("Successor Found.  New Personnel Notification Created for Position ID : " + positionId);
                                   returnCode = 3;
                            }
                     }
               }
        }
        else {
               LOGGER.debug("No Ended Personnel Assignments Found.  Exiting . . .");
               returnCode = 1;
        }

	}

	// Notification generated 10 days prior to the end date being reached.
	@Override
	public void endDateApproachingBatch() {
		LOGGER.debug("BatchManagerImpl.endDateApproachingBatch()");
		List<PersonnelBean> endDateApproaching = personnelService
				.getAllEndDateApproachingAssignments();
		for (PersonnelBean p : endDateApproaching) {
			notificationsService.addEndDateNotification(p.getEin(),
					p.getPositionId(), "batchProcess");
			returnCode++;
		}
	}

	@Override
	public void exceptionsRequestBatch() {
		LOGGER.debug("BatchManagerImpl.exceptionsRequestBatch()");
		List<EquipmentBean> equipment = equipmentService
				.getRecordsWithoutExceptionGranted();
		for (EquipmentBean e : equipment) {
			String ein = e.getEin();
			Integer positionId = e.getPositionId();
			notificationsService.addExceptionsRequestNotification(ein,
					positionId, "batchProcess");
			returnCode++;
		}
	}

	@Override
	public int getReturnCode() {
		return returnCode;
	}

	public PositionsService getPositionsService() {
		return positionsService;
	}

	public void setPositionsService(PositionsService positionsService) {
		this.positionsService = positionsService;
	}

	public NotificationsService getNotificationsService() {
		return notificationsService;
	}

	public void setNotificationsService(NotificationsService notificationsService) {
		this.notificationsService = notificationsService;
	}

	public PersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public EquipmentService getEquipmentService() {
		return equipmentService;
	}

	public void setEquipmentService(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	
}
