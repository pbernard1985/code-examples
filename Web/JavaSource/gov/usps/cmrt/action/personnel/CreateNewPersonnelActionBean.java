package gov.usps.cmrt.action.personnel;


import javax.annotation.security.RolesAllowed;

import gov.usps.cmrt.action.common.BaseActionBean;
import gov.usps.cmrt.action.common.CmrtActionBeanContextImpl;
import gov.usps.cmrt.action.positions.PositionsActionBean;
import gov.usps.cmrt.datalayer.personnel.bean.PersonnelBean;
import gov.usps.cmrt.datalayer.personnel.mapper.PersonnelMapper;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.action.Wizard;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import org.apache.log4j.Logger;

@Wizard(startEvents = {"form", "cancel", "cancelPersonnel", "continueToAssign"})
@RolesAllowed({ "SYSADMIN", "USER" })
@UrlBinding("/action/personnel/add/{$event}")
public class CreateNewPersonnelActionBean extends BaseActionBean implements ValidationErrorHandler {
	
	protected CmrtActionBeanContextImpl context;
	private static final Logger logger = Logger.getLogger(CreateNewPersonnelActionBean.class);
	
	private static final String FORM = "/WEB-INF/views/personnel/createNewPersonnel.jsp";
	private static final String CONFIRM = "/WEB-INF/views/personnel/createNewPersonnelConfirmation.jsp";
	private static final String RETURN = "/action/positions/";
	private static final String SHOW_RESULTS = "/WEB-INF/views/personnel/showResultsMessage.jsp";
	private static final String CONTINUE_TO_ASSIGN = "/action/personnel/assign/form";
	private static final String CANCEL = "/action/personnel/goToSearchPersonnel";
	
	@Validate(required = true, on = {"continue"}) private String lastName;
	@Validate(required = true, on = {"continue"}) private String firstName;
	private String middleInitial;
	private String einNew;
	@Validate(required = true, on = {"continue"}) private String aceId;
	private String comments;
	
	private PersonnelBean personnel;
	private String actionTaken;
	private String message;
	private int positionId;

	@SpringBean
	private PersonnelMapper personnelMapper;
	
	@DefaultHandler
	@HandlesEvent("form")
	public Resolution form() {
		logger.debug("CreateNewPersonnelActionBean.form()");
		String referrer = context.getRequest().getHeader("referer");
		if (referrer.contains("positions") || "positionSearch".equals(actionTaken)) {
			actionTaken = "positionSearch";
		}
		else {
			actionTaken = "personnelSearch";
		}
		return new ForwardResolution(FORM);
	}
	
	@HandlesEvent("continue")
	public Resolution confirmCreateNewPersonnel() {
		logger.debug("CreateNewPersonnelActionBean.confirmCreateNewPersonnel()");
		return new ForwardResolution(CONFIRM);
	}
	
	@HandlesEvent("revise")
	public Resolution reviseDetails() {
		logger.debug("CreateNewPersonnelActionBean.reviseDetails()");
		return new ForwardResolution(FORM);
	}
	
	@HandlesEvent("submitCreatePersonnel")
	public Resolution submitCreatePersonnel() {
		logger.debug("CreateNewPersonnelActionBean.submitCreatePersonnel()");
		
		personnel = new PersonnelBean();
		personnel.setFirstName(firstName);
		personnel.setLastName(lastName);
		personnel.setMiddleInitial(middleInitial);
		personnel.setEin(einNew);
		personnel.setAceId(aceId);
		personnel.setPersonnelComments(comments);
		personnel.setUserId(context.getUserId());

		String middleText = middleInitial == null ? "" : middleInitial + ". ";
		
		int insertCount = personnelMapper.createPersonnel(personnel);
		
		if (insertCount == 1) {
			message = "Personnel " + firstName + " " + middleText
					+ lastName + " was successfully created.";
		}
		else {
			message = "There was an error processing the creation of Personnel " + firstName + " "
					+ middleText + lastName + ".  Please Try Again." ;
		}

		return new ForwardResolution(SHOW_RESULTS)
			.addParameter("message", message).addParameter("actionTaken", actionTaken);
	}
	
	@HandlesEvent("cancel")
	public Resolution cancel() {
		logger.debug("CreateNewPersonnelActionBean.cancel()");
		return new RedirectResolution(RETURN);
	}
	
	@HandlesEvent("cancelPersonnel")
	public Resolution cancelPersonnel() {
		logger.debug("CreateNewPersonnelActionBean.cancelPersonnel()");
		return new RedirectResolution(CANCEL).addParameter("positionId", positionId).addParameter("actionTaken", actionTaken);
	}
	
	@HandlesEvent("continueToAssign")
	public Resolution continueToAssign() {
		logger.debug("CreateNewPersonnelActionBean.continueToAssign()");
		return new ForwardResolution(CONTINUE_TO_ASSIGN)
			.addParameter("ein", einNew);
	}
	
	@ValidationMethod(on = { "continue" }, when = ValidationState.ALWAYS)
	public void validateForm(ValidationErrors errors) {
		int count = personnelMapper.getPersonnelCount(einNew);
		if (einNew == null) {
			errors.add("einNew", new SimpleError("EIN is a required field"));
		}
		if (count > 0) {
			errors.add("einNew", new SimpleError("This EIN already exists."));
		}
		
		if (firstName != null && !firstName.matches("[a-zA-Z\\s.'-]+")) {
			errors.add("firstName", new SimpleError("First Name may only consist of Letters, Apostrophes, Periods, Dashes, or Spaces"));
		}
		if (lastName != null && !lastName.matches("[a-zA-Z\\s.'-]+")) {
			errors.add("lastName", new SimpleError("Last Name may only consist of Letters, Apostrophes, Periods, Dashes, or Spaces"));
		}
		if (middleInitial != null && !middleInitial.matches("[a-zA-Z.'-]+")) {
			errors.add("middleInitial", new SimpleError("Middle Initial may only consist of Letters, Apostrophes, Periods, or Dashes"));
		}

		if (einNew != null && !einNew.matches("[0-9]{8}")) {
			errors.add("einNew", new SimpleError("EIN must be a valid 8-digit number"));
		}
		if (aceId != null && !aceId.matches("[A-Za-z0-9]+")) {
			errors.add("aceId", new SimpleError("ACE ID can only consist of letters and numbers"));
		}
	}

	@Override
	public Resolution handleValidationErrors(ValidationErrors arg0)
			throws Exception {
		return new ForwardResolution(FORM);
	}	
	
	public CmrtActionBeanContextImpl getContext() {
		return context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = (CmrtActionBeanContextImpl) context;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}


	public String getEinNew() {
		return einNew;
	}

	public void setEinNew(String einNew) {
		this.einNew = einNew;
	}

	public String getAceId() {
		return aceId;
	}

	public void setAceId(String aceId) {
		this.aceId = aceId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}	
	
	
}
