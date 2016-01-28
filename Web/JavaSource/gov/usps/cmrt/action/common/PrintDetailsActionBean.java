package gov.usps.cmrt.action.common;


import gov.usps.cmrt.datalayer.equipment.bean.EquipmentBean;
import gov.usps.cmrt.datalayer.equipment.mapper.EquipmentMapper;
import gov.usps.cmrt.datalayer.personnel.bean.PersonnelBean;
import gov.usps.cmrt.datalayer.personnel.mapper.PersonnelMapper;
import gov.usps.cmrt.datalayer.positions.bean.PositionsBean;
import gov.usps.cmrt.datalayer.positions.mapper.PositionsMapper;

import org.apache.log4j.Logger;

import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

@UrlBinding("/action/print/{$event}")
public class PrintDetailsActionBean extends BaseActionBean {
	
	protected CmrtActionBeanContextImpl context;
	private static final Logger logger = Logger.getLogger(PrintDetailsActionBean.class);
	
	private static final String VIEW_DETAILS = "/WEB-INF/views/common/printDetails.jsp";
	
	private String ein;
	private Integer positionId;
	
	private PersonnelBean personnel;
	private PositionsBean position;
	private EquipmentBean equipment;
	
	@SpringBean
	protected PersonnelMapper personnelMapper;
	
	@SpringBean
	protected PositionsMapper positionsMapper;
	
	@SpringBean
	protected EquipmentMapper equipmentMapper;
	
	@DefaultHandler
	public Resolution viewPrintDetails() {
		logger.debug("View Print Details : PrintDetailsActionBean.viewPrintDetails()");
		position = positionsMapper.getPositionDetails(positionId, ein);
		if (ein != null) {
			personnel = personnelMapper.getPersonnelDetails(positionId, ein);
			equipment = equipmentMapper.getEquipmentDetails(positionId, ein);
		}
		return new ForwardResolution(VIEW_DETAILS);
	}

	public CmrtActionBeanContextImpl getContext() {
		return context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = (CmrtActionBeanContextImpl) context;
	}

	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public PersonnelBean getPersonnel() {
		return personnel;
	}

	public void setPersonnel(PersonnelBean personnel) {
		this.personnel = personnel;
	}

	public PositionsBean getPosition() {
		return position;
	}

	public void setPosition(PositionsBean position) {
		this.position = position;
	}

	public EquipmentBean getEquipment() {
		return equipment;
	}

	public void setEquipment(EquipmentBean equipment) {
		this.equipment = equipment;
	}
}
