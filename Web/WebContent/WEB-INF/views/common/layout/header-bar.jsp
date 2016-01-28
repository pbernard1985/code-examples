<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
	<stripes:layout-component name="headerBar">
		<!-- Header-Bar -->
		<div id="header-bar">
			<div class="page-section">
				<div class="clearfix">
					<div id="cmrtHead">
						<div class="logo-img left">
							<a href="${pageContext.request.contextPath}/action/notifications">
								<img
								src="${pageContext.request.contextPath}/images/gui/USPS-logo-133-27.png"
								height="27" width="133" alt="USPS logo" /> </a>
						</div>
						<div class="floatLeft" >
							<h1>Complement Manager Reporting Tool</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-definition>
<!-- end Header-Bar -->
