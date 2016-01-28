<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="security"
	uri="http://www.stripes-stuff.org/security.tld"%>

<c:set var="position" value="${actionBean.position }" />
<c:set var="personnel" value="${actionBean.personnel }" />
<c:set var="equipment" value="${actionBean.equipment }" />

<div style="float: right; margin-right: 0px;">
	<span class="pill-btn button-link pill-btn-white"> <span
		class="hasHover"> <input value="Print"
			title="Print All Details" class="buttons" type="button" id="print" name="print"
			onclick="printDiv('printDiv');"> </span> </span>
</div>
<br>
<div name="wrap" style="width:670px;">
<div name="printDiv" id="printDiv" style="width:100%;">
<span style="float:left;" class="printHeader">Position Details</span>
<table class="detailsTable" style="width:100%;border:1px solid black;">
		<tr>
			<td><label>Position Number:</label></td>
			<td><span class="positionNumSpan">${position.positionNumber}</span>
			</td>
		</tr>
		<tr>
			<td><label>Job Title:</label></td>
			<td>${position.jobTitle }</td>
		</tr>
		<tr>
			<td><label>Level:</label></td>
			<td>${position.paySchedName }</td>
		</tr>
		<tr>
			<td><label>Occ Code:</label></td>
			<td>${position.occCode }</td>
		</tr>
		<tr>
			<td><label>Finance Number:</label></td>
			<td>${position.financeNumber }</td>
		</tr>
		<tr>
			<td><label>Area/Functional Group:</label></td>
			<td>${position.areaGroupName }</td>
		</tr>
		<tr>
			<td><label>Org Unit Name:</label></td>
			<td>${position.orgUnitName }</td>
		</tr>
		<tr>
			<td><label>Org Unit ID:</label></td>
			<td>${position.orgUnitId }</td>
		</tr>
		<tr>
			<td><label>Duty Station:</label></td>
			<td>${position.dutyStation }</td>
		</tr>
		<tr>
			<td><label>Status:</label></td>
			<td>${position.statusName }</td>
		</tr>
		<tr>
			<td><label>Exempt:</label></td>
			<td>${position.exempt }</td>
		</tr>
		<tr>
			<td><label>Comments:</label></td>
			<td>${position.comments }</td>
		</tr>
</table>
<br />
<span style="float:left;" class="printHeader">Personnel Details</span>
<table class="detailsTable" style="width:100%;border:1px solid black;">
	<tr>
		<td><label>Last Name:</label></td>
		<td>${personnel.lastName }</td>
	</tr>
	<tr>
		<td><label>First Name:</label></td>
		<td>${personnel.firstName }</td>
	</tr>
	<tr>
		<td><label>Middle Initial:</label></td>
		<td>${personnel.middleInitial }</td>
	</tr>
	<tr>
		<td><label>EIN:</label></td>
		<td>${personnel.ein}</td>
	</tr>
	<tr>
		<td><label>ACE ID:</label></td>
		<td>${personnel.aceId}</td>
	</tr>
	<tr>
		<td><label>Fulfillment Type:</label></td>
		<td>${personnel.fulfillmentName}</td>
	</tr>
	<tr>
		<td><label>Detail Status:</label></td>
		<td>${personnel.detailStatus}</td>
	</tr>
		<tr>
		<td><label>Begin Date:</label></td>
		<c:choose>
			<c:when test="${personnel.beginDateBold}">
				<td><span style="font-weight: bold;">${personnel.beginDate}</span>
				</td>
			</c:when>
			<c:otherwise>
				<td>${personnel.beginDate}</td>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td><label>End Date:</label></td>
		<td>${personnel.endDate}</td>
	</tr>
	<c:if test="${not empty personnel.endDate}">
		<tr>
			<td><label>Reason for Turnover:</label></td>
			<td>${personnel.reasonName}</td>
		</tr>
	</c:if>
	<tr>
		<td><label>Detail Exception:</label></td>
		<td>${personnel.detailException}</td>
	</tr>
	<tr>
		<td><label>Domicile Status:</label></td>
		<td>${personnel.domicileStatus}</td>
	</tr>
	<tr>
		<td><label>Domicile Duty Station:</label></td>
		<td>${personnel.domicileDutyStation }</td>
	</tr>
	<tr>
		<td><label>Comments:</label></td>
		<td>${personnel.personnelComments}</td>
	</tr>
</table>
<br />
<span style="float:left;" class="printHeader">Equipment Details</span>
<table class="detailsTable" style="width:100%;border:1px solid black;">
	<tr>
		<td><label>Exception Requested:</label></td>
		<td>${equipment.exceptionRequested}</td>
	</tr>
	<tr>
		<td><label>Exception Request Date:</label></td>
		<td>${equipment.exceptionRequestDate}</td>
	</tr>
	<tr>
		<td><label>Exception Granted:</label></td>
		<td>${equipment.exceptionGranted}</td>
	</tr>
	<tr>
		<td><label>Exception Granted Date:</label></td>
		<td>${equipment.exceptionGrantedDate}</td>
	</tr>
	<tr>
		<td><label>Laptop Needed:</label></td>
		<td>${equipment.laptopNeeded}</td>
	</tr>
	<tr>
		<td><label>Laptop Request Date:</label></td>
		<td>${equipment.laptopRequestDate}</td>
	</tr>
	<tr>
		<td><label>Phone Needed:</label></td>
		<td>${equipment.phoneNeeded}</td>
	</tr>
	<tr>
		<td><label>Phone Request Date:</label></td>
		<td>${equipment.phoneRequestDate}</td>
	</tr>
	<tr>
		<td><label>iPad Needed:</label></td>
		<td>${equipment.ipadNeeded}</td>
	</tr>
	<tr>
		<td><label>iPad Request Date:</label></td>
		<td>${equipment.ipadRequestDate}</td>
	</tr>
	<tr>
		<td><label>Comments:</label></td>
		<td>${equipment.equipmentComments}</td>
	</tr>
</table>
</div>
</div>