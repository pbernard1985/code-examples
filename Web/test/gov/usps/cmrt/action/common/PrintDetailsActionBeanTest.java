package gov.usps.cmrt.action.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import gov.usps.cmrt.datalayer.equipment.bean.EquipmentBean;
import gov.usps.cmrt.datalayer.equipment.mapper.EquipmentMapper;
import gov.usps.cmrt.datalayer.personnel.bean.PersonnelBean;
import gov.usps.cmrt.datalayer.personnel.mapper.PersonnelMapper;
import gov.usps.cmrt.datalayer.positions.bean.PositionsBean;
import gov.usps.cmrt.datalayer.positions.mapper.PositionsMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrintDetailsActionBeanTest {
	
	@InjectMocks
	protected PrintDetailsActionBean printActionBean;
	
	@Mock
	protected CmrtActionBeanContextImpl context;
	@Mock
	protected PositionsMapper positionsMapper;
	@Mock
	protected PersonnelMapper personnelMapper;
	@Mock
	protected EquipmentMapper equipmentMapper;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		context = null;
		positionsMapper = null;
		personnelMapper = null;
		equipmentMapper = null;
	}
	
	@Test
	public void testGettersSetters() {
		
		printActionBean.setEin("TEST");
		assertEquals("TEST", printActionBean.getEin());
		
		printActionBean.setPositionId(new Integer(3));
		assertEquals(3, printActionBean.getPositionId(), 0.0);
		
		printActionBean.setPersonnel(new PersonnelBean());
		assertNotNull(printActionBean.getPersonnel());
		
		printActionBean.setPosition(new PositionsBean());
		assertNotNull(printActionBean.getPosition());
		
		printActionBean.setEquipment(new EquipmentBean());
		assertNotNull(printActionBean.getEquipment());
		
		printActionBean.setContext(context);
		assertNotNull(printActionBean.getContext());

	}

}
