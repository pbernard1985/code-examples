<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security"
	uri="http://www.stripes-stuff.org/security.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<stripes:layout-render
	name="/WEB-INF/views/common/layout/layout_base_tabs.jsp"
	title="Positions">
	<stripes:layout-component name="pageContents">
	<script
			src="${pageContext.request.contextPath}/scripts/personnel/personnel.js"></script>
			<div class="centerContent">
				<h2>Create New Personnel</h2>
				<!-- start  message --->
				<br>
				<span style="font-size:16px;padding-left:0px;color:grey;">Confirm the details and click &lt;Submit&gt; to create the new personnel.  Click &lt;Back&gt; to return to the previous screen and make changes.</span>
				<br><br>
				<div class="pageText" style="margin-right:auto; margin-left:auto;">
				<stripes:form action="/action/personnel/add/submitCreatePersonnel" id="createPersonnelForm"
					beanclass="gov.usps.cmrt.action.personnel.CreateNewPersonnelActionBean">
					<input type="hidden" name="actionTaken" value="${actionBean.actionTaken }" id="actionTaken" />
					<input type="hidden" name="einNew" value="${actionBean.einNew }" id="einNew" />
					<input type="hidden" name="positionId" value="${actionBean.positionId }" id="positionId" />
					<table class="confirm">
						<tr>
							<td><label>Last Name:</label></td>
							<td>${actionBean.lastName }</td>
						</tr>
						<tr>
							<td><label>First Name:</label></td>
							<td>${actionBean.firstName }</td>
						</tr>
						<tr>
							<td><label>Middle Initial:</label></td>
							<td>${actionBean.middleInitial }</td>
						</tr>
						<tr>
							<td><label>EIN:</label></td>
							<td>${actionBean.einNew}</td>
						</tr>
						<tr>
							<td><label>ACE ID:</label></td>
							<td>${actionBean.aceId }</td>
						</tr>
						<tr>
							<td><label>Comments:</label></td>
							<td>${actionBean.comments }</td>						
						</tr>
					</table>
						<input type="hidden" name="pageId" id="pageId" value="_2" />
						<div class="button marginLeft10">
							<span class="button-link button-left btn-reg btn-blue-reg"> <span
								class="hasHover"> <input type="button" id="submitButtonId" onclick="createPersonnel('createPersonnelForm');"
									class="buttons" title="Submit" value="Submit" tabindex="14" /> </span> </span>
						</div>
						</stripes:form>
						<c:choose>
				<c:when test="${actionBean.actionTaken eq 'positionSearch' }">
				<div class="button">
					<span class="button-link button-left btn-reg btn-blue-reg"> <span
						class="hasHover"> 
						<stripes:form action="/admin/action/personnel/add/cancel" 
							beanclass="gov.usps.cmrt.action.personnel.CreateNewPersonnelActionBean">
							<input type="hidden" name="actionTaken" value="${actionBean.actionTaken }" />
						<stripes:submit id="cancel" name="cancel" class="buttons" title="Cancel" tabindex="14">Cancel</stripes:submit> </span> </span>
						</stripes:form>
				</div>
				</c:when>
				<c:otherwise>
				<div class="button">
					<span class="button-link button-left btn-reg btn-blue-reg"> <span
						class="hasHover"> 
						<stripes:form action="/admin/action/personnel/assign/cancel" 
							beanclass="gov.usps.cmrt.action.personnel.AssignPersonnelActionBean">
							<input type="hidden" name="actionTaken" value="${actionBean.actionTaken }" />
						<stripes:submit id="cancel" name="cancel" class="buttons" title="Cancel" tabindex="14">Cancel</stripes:submit> </span> </span>
						</stripes:form>
				</div>
				</c:otherwise>
			</c:choose>
				<stripes:form action="/action/personnel/add/revise" 
					beanclass="gov.usps.cmrt.action.personnel.CreateNewPersonnelActionBean">
				<div class="button" style="float:left;">
							<span class="button-link button-left btn-reg btn-blue-reg">
							<span class="hasHover"> <stripes:submit name="goBack" class="buttons" title="Back" tabindex="53">Back</stripes:submit> </span> </span>
						</div>
				</stripes:form>
				</div>	
		</div>
		<stripes:layout-render name="/WEB-INF/views/common/modalDialog.jsp"
			caption="dialog_box" />
	</stripes:layout-component>
</stripes:layout-render>