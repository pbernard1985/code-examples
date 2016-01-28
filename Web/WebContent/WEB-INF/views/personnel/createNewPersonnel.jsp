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
	<script	src="${pageContext.request.contextPath}/scripts/personnel/personnel.js"></script>
	<div class="centerContent">
				<h2>Create New Personnel</h2>
				<!-- start  message --->
				<div class="pageDesc">Enter the Personnel Details and then click the &lt;Continue&gt; button to continue.</div>
				<div style="width:447px;">
				<div class="reqField">
				<span class="required">*</span> Indicates a Required Field
				</div>
				
				<stripes:form action="/action/personnel/add/continue" 
					beanclass="gov.usps.cmrt.action.personnel.CreateNewPersonnelActionBean">
				<input type="hidden" name="pageId" id="pageId" value="_2" />
				<input type="hidden" name="actionTaken" value="${actionBean.actionTaken }" />
				<table class="editDetailsTable">
					<tr>
						<td><span class="required">*</span><label>Last Name:</label></td>
						<td><stripes:errors field="lastName" />
							<div class="input-text-wrapper input-text-lg">
								<span class="input-cap-left"></span> <span class="input-field"
									style="width: 230px"> <stripes:text name="lastName"
										id="lastName" maxlength="50" class="text-input-large"
										tabindex="1" /> </span>
								<span class="input-cap-right"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td><span class="required">*</span><label>First Name:</label></td>
						<td><stripes:errors field="firstName" />
							<div class="input-text-wrapper input-text-lg">
								<span class="input-cap-left"></span> <span class="input-field"
									style="width: 230px"> <stripes:text name="firstName"
										id="firstName" maxlength="50" class="text-input-large"
										tabindex="2" /> </span>
								<span class="input-cap-right"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td><label>Middle Initial:</label></td>
						<td><stripes:errors field="middleInitial" />
							<div class="input-text-wrapper input-text-lg">
								<span class="input-cap-left"></span> <span class="input-field"
									style="width: 230px"> <stripes:text name="middleInitial"
										id="middleInitial" maxlength="1" class="text-input-large"
										tabindex="3" /> </span>
								<span class="input-cap-right"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td><span class="required">*</span><label>EIN:</label></td>
						<td><stripes:errors field="einNew" />
							<div class="input-text-wrapper input-text-lg">
								<span class="input-cap-left"></span> <span class="input-field"
									style="width: 230px"> <stripes:text name="einNew"
										id="einNew" maxlength="8" class="text-input-large"
										tabindex="4" /> </span>
								<span class="input-cap-right"></span>
							</div>				
						</td>
					</tr>
					<tr>
						<td><span class="required">*</span><label>ACE ID:</label></td>
						<td><stripes:errors field="aceId" />
							<div class="input-text-wrapper input-text-lg">
								<span class="input-cap-left"></span> <span class="input-field"
									style="width: 230px"> <stripes:text name="aceId"
										id="aceId" maxlength="10" class="text-input-large"
										tabindex="5"  /> </span>
								<span class="input-cap-right"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top"><label>Comments:</label></td>
						<td><stripes:errors field="comments" /> <stripes:textarea 
								name="comments" style="width:235px; height:75px" tabindex="13" /></td>
					</tr>
				</table>
				<div class="button marginLeft10" style="margin-right: 53px;">
					<span class="button-link button-left btn-reg btn-blue-reg"> <span
						class="hasHover"> <stripes:submit name="continue" class="buttons"
							id="continue"
							tabindex="14">Continue</stripes:submit></span> </span>
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
						<stripes:form action="/admin/action/personnel/add/cancelPersonnel" 
							beanclass="gov.usps.cmrt.action.personnel.CreateNewPersonnelActionBean">
							<input type="hidden" name="actionTaken" value="${actionBean.actionTaken }" />
						<stripes:submit id="cancelPersonnel" name="cancelPersonnel" class="buttons" title="Cancel" tabindex="14">Cancel</stripes:submit> </span> </span>
						</stripes:form>
				</div>
				</c:otherwise>
			</c:choose>
				</div>	
		</div>
	</stripes:layout-component>
</stripes:layout-render>