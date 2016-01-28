package gov.usps.cmrt.batch.common;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import gov.usps.cmrt.datalayer.equipment.bean.EquipmentBean;
import gov.usps.cmrt.datalayer.equipment.service.EquipmentService;
import gov.usps.cmrt.datalayer.notifications.service.NotificationsService;
import gov.usps.cmrt.datalayer.personnel.bean.PersonnelBean;
import gov.usps.cmrt.datalayer.personnel.service.PersonnelService;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BatchManagerImplTest {
	
	@Mock
	protected PersonnelService personnelService;	
	@Mock
	protected NotificationsService notificationsService;
	@Mock
	protected EquipmentService equipmentService;
	
	@InjectMocks
	private BatchManagerImpl batchManager;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		batchManager = null;
		personnelService = null;
		notificationsService = null;
		equipmentService = null;
	}

	@Test
	public void testVacateEndedPersonnelAssignmentsReturn1() {
		when(personnelService.getAllEndedPersonnelAssignments()).thenReturn(new ArrayList<PersonnelBean>());
		batchManager.vacateEndedPersonnelAssignments();
		assertEquals(1, batchManager.getReturnCode());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testVacateEndedPersonnelAssignmentsReturn0() {
		ArrayList<PersonnelBean> endedAssignments = new ArrayList<PersonnelBean>();
		endedAssignments.add(new PersonnelBean());
		endedAssignments.add(new PersonnelBean());
		when(personnelService.getAllEndedPersonnelAssignments()).thenReturn(endedAssignments);
		when(personnelService.vacateEndedPersonnelAssignments((List<PersonnelBean>) anyObject())).thenReturn(1);
		when(equipmentService.clearEquipmentDetails((List<EquipmentBean>) anyObject())).thenReturn(2);
		batchManager.vacateEndedPersonnelAssignments();
		assertEquals(0, batchManager.getReturnCode());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testVacateEndedPersonnelAssignmentsReturn2() {
		ArrayList<PersonnelBean> endedAssignments = new ArrayList<PersonnelBean>();
		endedAssignments.add(new PersonnelBean());
		endedAssignments.add(new PersonnelBean());
		when(personnelService.getAllEndedPersonnelAssignments()).thenReturn(endedAssignments);
		when(personnelService.vacateEndedPersonnelAssignments((List<PersonnelBean>) anyObject())).thenReturn(2);
		when(personnelService.getSuccessor(anyInt())).thenReturn(null);
		when(notificationsService.addVacantNotification(anyString(), anyInt(), anyString())).thenReturn(1);
		when(equipmentService.clearEquipmentDetails((List<EquipmentBean>) anyObject())).thenReturn(2);
		batchManager.vacateEndedPersonnelAssignments();
		assertEquals(2, batchManager.getReturnCode());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testVacateEndedPersonnelAssignmentsReturn3() {
		ArrayList<PersonnelBean> endedAssignments = new ArrayList<PersonnelBean>();
		endedAssignments.add(new PersonnelBean());
		endedAssignments.add(new PersonnelBean());
		when(personnelService.getAllEndedPersonnelAssignments()).thenReturn(endedAssignments);
		when(personnelService.vacateEndedPersonnelAssignments((List<PersonnelBean>) anyObject())).thenReturn(2);
		when(personnelService.getSuccessor(anyInt())).thenReturn(new PersonnelBean());
		when(notificationsService.addNewPersonnelNotification(anyString(), anyInt(), anyString())).thenReturn(1);
		when(equipmentService.clearEquipmentDetails((List<EquipmentBean>) anyObject())).thenReturn(2);
		batchManager.vacateEndedPersonnelAssignments();
		assertEquals(3, batchManager.getReturnCode());
	}
	
	@Test
	public void testEndDateApproachingBatch() {
		ArrayList<PersonnelBean> endDateApproaching = new ArrayList<PersonnelBean>();
		endDateApproaching.add(new PersonnelBean());
		endDateApproaching.add(new PersonnelBean());
		when(personnelService.getAllEndDateApproachingAssignments()).thenReturn(endDateApproaching);
		when(notificationsService.addEndDateNotification(anyString(), anyInt(), anyString())).thenReturn(1);
		batchManager.endDateApproachingBatch();
		assertEquals(2, batchManager.getReturnCode());
	}
	
	@Test
	public void testExceptionsRequestBatch() {
		ArrayList<EquipmentBean> equipment = new ArrayList<EquipmentBean>();
		equipment.add(new EquipmentBean());
		equipment.add(new EquipmentBean());
		equipment.add(new EquipmentBean());
		when(equipmentService.getRecordsWithoutExceptionGranted()).thenReturn(equipment);
		when(notificationsService.addExceptionsRequestNotification(anyString(), anyInt(), anyString())).thenReturn(1);
		batchManager.exceptionsRequestBatch();
		assertEquals(3, batchManager.getReturnCode());
	}

}
