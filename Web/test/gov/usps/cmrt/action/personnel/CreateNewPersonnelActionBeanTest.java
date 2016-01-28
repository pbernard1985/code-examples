package gov.usps.cmrt.action.personnel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gov.usps.cmrt.action.common.CmrtActionBeanContextImpl;
import gov.usps.cmrt.common.utils.CommonUtils;
import gov.usps.cmrt.datalayer.common.DropDownValueMapper;
import gov.usps.cmrt.datalayer.common.FulfillmentTypeBean;
import gov.usps.cmrt.datalayer.common.TurnoverReasonBean;
import gov.usps.cmrt.datalayer.personnel.bean.PersonnelBean;
import gov.usps.cmrt.datalayer.personnel.mapper.PersonnelMapper;
import gov.usps.cmrt.datalayer.positions.mapper.PositionsMapper;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ValidationErrors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateNewPersonnelActionBeanTest {
	
	@Mock
	protected CmrtActionBeanContextImpl context;
	@Mock
	protected PersonnelMapper personnelMapper;
	@Mock
	protected DropDownValueMapper dropDownMapper;
	@Mock
	protected PositionsMapper positionMapper;
	@Mock
	protected CommonUtils commonUtils;
	
	@InjectMocks
	private CreateNewPersonnelActionBean cnpab;
	private HttpServletResponse mockResponse;
	private HttpServletRequest mockRequest;
	private HttpSession mockSession;
	
	@Before
	public void setUp() {
		mockRequest = mock(HttpServletRequest.class);
		mockSession = mock(HttpSession.class);
		mockResponse = mock(HttpServletResponse.class);
		context.setRequest(mockRequest);
		context.setResponse(mockResponse);
		
		when(context.getResponse()).thenReturn(mockResponse);
		when(context.getRequest()).thenReturn(mockRequest);
		when(mockRequest.getSession()).thenReturn(mockSession);
	}
	
	@After
	public void tearDown() {
		context = null;
		personnelMapper = null;
		dropDownMapper = null;
	}
	
	@Test
	public void testGettersSetters() {
		cnpab.setFirstName("TEST");
		assertEquals("TEST", cnpab.getFirstName());
		
		cnpab.setLastName("TEST");
		assertEquals("TEST", cnpab.getLastName());
		
		cnpab.setMiddleInitial("TEST");
		assertEquals("TEST", cnpab.getMiddleInitial());		
		
		cnpab.setEinNew("TEST");
		assertEquals("TEST", cnpab.getEinNew());
		
		cnpab.setAceId("TEST");
		assertEquals("TEST", cnpab.getAceId());
		
		cnpab.setComments("TEST");
		assertEquals("TEST", cnpab.getComments());
		
		cnpab.setMessage("TEST");
		assertEquals("TEST", cnpab.getMessage());
		
		cnpab.setActionTaken("TEST");
		assertEquals("TEST", cnpab.getActionTaken());
		
		cnpab.setContext(context);
		assertNotNull(cnpab.getContext());
		
		cnpab.setPositionId(3);
		assertEquals(3, cnpab.getPositionId(), 0.0);
	}
	
	@Test
	public void testFormFromPositionSearch() {
		when(context.getRequestSessionAttribute(anyString())).thenReturn(null);
		when(dropDownMapper.getAllFulfillmentTypes()).thenReturn(new ArrayList<FulfillmentTypeBean>());
		when(dropDownMapper.getAllTurnoverReasons()).thenReturn(new ArrayList<TurnoverReasonBean>());
		when(context.getRequest().getHeader(anyString())).thenReturn("positions");
		Resolution result = cnpab.form();
		assertEquals("/WEB-INF/views/personnel/createNewPersonnel.jsp", ((ForwardResolution) result).getPath());
	}
	
	@Test
	public void testFormFromPersonnelSearch() {
		when(context.getRequestSessionAttribute(anyString())).thenReturn(null);
		when(dropDownMapper.getAllFulfillmentTypes()).thenReturn(new ArrayList<FulfillmentTypeBean>());
		when(dropDownMapper.getAllTurnoverReasons()).thenReturn(new ArrayList<TurnoverReasonBean>());
		when(context.getRequest().getHeader(anyString())).thenReturn("personnel");
		Resolution result = cnpab.form();
		assertEquals("/WEB-INF/views/personnel/createNewPersonnel.jsp", ((ForwardResolution) result).getPath());
	}
	
	@Test
	public void testContinue() {
		ArrayList<FulfillmentTypeBean> fTypes = new ArrayList<FulfillmentTypeBean>();
		FulfillmentTypeBean bean1 = new FulfillmentTypeBean();
		FulfillmentTypeBean bean2 = new FulfillmentTypeBean();
		bean1.setFulfillmentId("A");
		bean1.setFulfillmentName("TEST");
		bean2.setFulfillmentId("B");
		bean2.setFulfillmentName("TEST 2");
		fTypes.add(bean1);
		fTypes.add(bean2);
		ArrayList<TurnoverReasonBean> reasons = new ArrayList<TurnoverReasonBean>();
		TurnoverReasonBean bean3 = new TurnoverReasonBean();
		TurnoverReasonBean bean4 = new TurnoverReasonBean();
		bean3.setReasonId("A");
		bean3.setReasonName("TEST");
		bean4.setReasonId("B");
		bean4.setReasonName("TEST 2");
		reasons.add(bean3);
		reasons.add(bean4);
		when(dropDownMapper.getAllFulfillmentTypes()).thenReturn(fTypes);
		when(dropDownMapper.getAllTurnoverReasons()).thenReturn(reasons);
		Resolution result = cnpab.confirmCreateNewPersonnel();
		assertEquals("/WEB-INF/views/personnel/createNewPersonnelConfirmation.jsp", ((ForwardResolution) result).getPath());
	}
	
	@Test
	public void testRevise() {
		when(context.getRequestSessionAttribute("fulfillmentTypes")).thenReturn(new ArrayList<FulfillmentTypeBean>());
		when(context.getRequestSessionAttribute("turnoverReasons")).thenReturn(new ArrayList<TurnoverReasonBean>());
		Resolution result = cnpab.reviseDetails();
		assertEquals("/WEB-INF/views/personnel/createNewPersonnel.jsp", ((ForwardResolution) result).getPath());
	}
	
	@Test
	public void testSubmitCreatePersonnel() {
		when(personnelMapper.createPersonnel((PersonnelBean) anyObject())).thenReturn(1);
		Resolution result = cnpab.submitCreatePersonnel();
		assertEquals("/WEB-INF/views/personnel/showResultsMessage.jsp", ((ForwardResolution) result).getPath());
	}
	
	@Test
	public void testSubmitCreatePersonnelValidValues() {
		when(personnelMapper.createPersonnel((PersonnelBean) anyObject())).thenReturn(1);
		cnpab.setFirstName("firstName");
		cnpab.setLastName("lastName");
		cnpab.setMiddleInitial("middleInitial");
		Resolution result = cnpab.submitCreatePersonnel();
		assertEquals("/WEB-INF/views/personnel/showResultsMessage.jsp", ((ForwardResolution) result).getPath());
	}
	
	@Test
	public void testSubmitCreatePersonnelCreateReturnsZero() {
		when(personnelMapper.createPersonnel((PersonnelBean) anyObject())).thenReturn(0);
		cnpab.setFirstName("firstName");
		cnpab.setLastName("lastName");
		cnpab.setMiddleInitial("middleInitial");
		Resolution result = cnpab.submitCreatePersonnel();
		assertEquals("/WEB-INF/views/personnel/showResultsMessage.jsp", ((ForwardResolution) result).getPath());
	}
	
	@Test
	public void testCancel() {
		Resolution result = cnpab.cancel();
		assertEquals("/action/positions/", ((RedirectResolution) result).getPath());
	}
	
	@Test
	public void testValidationValidValues() {
		setPersonnelValues();
		ValidationErrors errors = new ValidationErrors();
		cnpab.validateForm(errors);
		assertEquals(0, errors.size(), 0.0);
	}
	
	@Test
	public void testValidationInvalidValues() {
		when(personnelMapper.getPersonnelCount(anyString())).thenReturn(1);
		setPersonnelValuesInvalid();
		ValidationErrors errors = new ValidationErrors();
		cnpab.validateForm(errors);
		assertEquals(4, errors.size(), 0.0);
	}
	
	@Test
	public void testValidationNullValues() {
		when(personnelMapper.getPersonnelCount(anyString())).thenReturn(1);
		ValidationErrors errors = new ValidationErrors();
		cnpab.validateForm(errors);
		assertEquals(1, errors.size(), 0.0);
	}
	
	@Test
	public void testHandleValidationErrors() throws Exception {
		ValidationErrors errors = new ValidationErrors();
		Resolution result = cnpab.handleValidationErrors(errors);
		assertEquals("/WEB-INF/views/personnel/createNewPersonnel.jsp", ((ForwardResolution) result).getPath());
	}
	
	private void setPersonnelValues() {
		cnpab.setFirstName("firstName");
		cnpab.setLastName("lastName");
		cnpab.setMiddleInitial("A");
		cnpab.setAceId("1234");
		cnpab.setEinNew("88444");
	}
	
	private void setPersonnelValuesInvalid() {
		cnpab.setFirstName("firstName123");
		cnpab.setLastName("lastName321");
		cnpab.setMiddleInitial("5");
		cnpab.setEinNew("abcdefg");
	}	

}
