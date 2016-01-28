<!-- Incude CSS for Vertical Tabs -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@taglib prefix="security" uri="http://www.stripes-stuff.org/security.tld"%>
<%@ page import="gov.usps.cmrt.common.CommonPropertiesProvider;"%>
<stripes:layout-definition>	
	<stripes:layout-component name="vmenu">
	 <% CommonPropertiesProvider cpp = new CommonPropertiesProvider(); %>
		<div class="vtabs-tab-column">
			<ul class="tabs-slider">
				<li class="closed" id="url_v_menu_item_1">
					<a class="closed" id="url_v_menu_item_1"
					href="${pageContext.request.contextPath}/action/notifications"
					title="Notifications"> Notifications</a>
				</li>
				
			<%-- 	<stripes:useActionBean var="myBean1" beanclass="gov.usps.cmrt.action.notifications.NotificationsActionBean" />
				<security:allowed bean="myBean1" event="showServiceManagement"> --%>
				<li class="closed" id="url_v_menu_item_2">
					<a class="closed" id="url_v_menu_item_2"
					href="${pageContext.request.contextPath}/action/positions/positionMenuItem"
					title="Position Search"> Position Search</a>
				</li>
 		 		<%-- </security:allowed>	 --%>	
  		
<!-- 				<li class="closed" id="url_v_menu_item_3"> -->
<!-- 					<a class="closed" id="url_v_menu_item_3"  -->
<!-- 					href="https://crpl.usps.gov/uspsedw/servlet/mstrWeb?Server=EAGNMNMEP9C4&Project=Corporate+Analytics+Provisioning+System+%28CAPS%29&Port=34953&evt=2048001&src=mstrWeb.2048001&documentID=BCA3A51A11E5362C00000080EFD5ED96&visMode=0" -->
<!-- 					title="Reports"> Reports </a> -->
<!-- 				</li>  -->
				
				<li class="closed" id="url_v_menu_item_3">
					<a class="closed" id="url_v_menu_item_3"
					href=""
					title="Reports" disabled> Reports </a>
				</li> 
						
			</ul>
		</div>
	</stripes:layout-component>
</stripes:layout-definition>