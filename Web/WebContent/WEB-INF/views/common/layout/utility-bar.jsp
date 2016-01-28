<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
	<stripes:layout-component name="utilityBar">

		<!-- begin Utility-Bar -->
		<div id="utility-bar">
			<div class="page-section">
				<div class="right">
					<ul id="contact-list">
						<li>
							<p>Hello, ${username}</p></li>
						<li class="link"><a href="mailto:ComplementManagementReportingTool@usps.gov">Contact us</a></li>
						<li class="link"><a
							href="${pageContext.request.contextPath}/action/logout">Logout</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</stripes:layout-component>
</stripes:layout-definition>
<!-- end Utility-Bar -->
