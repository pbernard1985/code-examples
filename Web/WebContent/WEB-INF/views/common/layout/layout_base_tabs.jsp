<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<stripes:layout-definition>
 	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	
<html>
<head>
        <meta http-equiv="X-UA-Compatible" content="IE=8; IE=9; IE=10" >
<title>Complement Manager Report System</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/images/favicon.ico"
	type="image/x-icon">
<jsp:include page="common-css.jsp" />
<script>
	dojoConfig = {
		has : {
			"dojo-firebug" : false
		},
		parseOnLoad : true,
		foo : "bar",
		async : true
	};
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/dojo/dojo.js"></script>
<script
	src="${pageContext.request.contextPath}/scripts/commonFunctions/commonFunctions.js"></script>
<script 
	type="text/javascript" 
	src="${pageContext.request.contextPath}/scripts/datepicker/datetimepicker_css.js"></script>
</head>

<body id="pageBodyId" class="claro">

	<!-- begin #page -->
	<div id="page">
		<stripes:layout-render
			name="/WEB-INF/views/common/layout/utility-bar.jsp"
			username="${sessionScope.user.firstName}"
			title="${title }">
		</stripes:layout-render>
		<stripes:layout-render
			name="/WEB-INF/views/common/layout/header-bar.jsp">
		</stripes:layout-render>
		<div id="main">
			<div id="main-inner">
				<div class="page-section">
					<div class="clearfix">
						<div id="inside-wrap">
							<div class="page-section">
								<div class="cap-middle cap-middle-large">
									<div class="clearfix">
										<div class="left toptab" id="tab-block">
											<!-- Vertical  Menu-->
											<div id="vtabs1" class="vtabs" style="border: none;">
												<stripes:layout-render
													name="/WEB-INF/views/common/layout/v-menu.jsp">
												</stripes:layout-render>

												<div class="innerTabWrap">
													<!-- end Vertical  Menu-->
													<stripes:layout-component name="pageContents"></stripes:layout-component>
													<!-- end account content-->
												</div>
											</div>
										</div>
										<!-- end #tab-block-->
									</div>
									<!-- end clearfix-->
								</div>
								<!-- end cap-middle-large-->
							</div>
							<!-- end page-section-->
						</div>
						<!-- end #inside-wrap-->
					</div>
					<!-- end clearfix-->
				</div>
				<!-- end page-section-->
			</div>
			<!-- end #main-inner -->
		</div>
		<!-- End #main -->
	</div>
</body>
	</html>
</stripes:layout-definition>