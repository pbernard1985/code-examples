require(
		[ "dijit/registry", "dojo/parser", "dojo/ready", "dojo/query", "dojo/dom-class", 'dojo/dom', "dojo/dom-construct", "dojo/_base/array",
				"dijit/Dialog", "dojo/_base/event", "dojo/has", "dojo/on", "dojo/_base/sniff", "dojo/string", "dojo/dom-attr", "dojo/io-query" ],
		function(registry, parser, ready, query, domClass, dom, constructDom, array, dialog, event, has, on, sniff, string, attr, ioQuery) {

			var uMap = {};

			uMap["AP"] = "/admin/action/personnel/assign/submit";
			uMap["PD"] = "/admin/action/personnel/positionInfo";

			ready(function() {
				var lastNameSearch = "";
				var einSearch = "";
				var aceIdSearch = "";
				if (dom.byId('lastNameSearch') != null) {
					lastNameSearch = dom.byId('lastNameSearch').value;
				}
				if (dom.byId('einSearch') != null) {
					einSearch = dom.byId('einSearch').value;
				}
				if (dom.byId('aceIdSearch') != null) {
					aceIdSearch = dom.byId('aceIdSearch').value;
				}

				if (lastNameSearch != "" && einSearch != "" && aceIdSearch != "") {
					searchPersonnelResults();
				}

				dojo.connect(dom.byId("lastNameSearch"), "onkeypress", function(evt) {
					if (evt.keyCode && evt.keyCode == 13) {
						searchPersonnelResults();
					}
				});
				dojo.connect(dom.byId("einSearch"), "onkeypress", function(evt) {
					if (evt.keyCode && evt.keyCode == 13) {
						searchPersonnelResults();
					}
				});
				dojo.connect(dom.byId("aceIdSearch"), "onkeypress", function(evt) {
					if (evt.keyCode && evt.keyCode == 13) {
						searchPersonnelResults();
					}
				});
				dojo.connect(dom.byId("viewDetails"), "click", function(evt) {
					dom.byId("positionTab").value ="P";
					dom.byId("returnToPersonnel").action = "/admin/action/positions/viewDetails";
					dom.byId("returnToPersonnel").submit();
				});

			});

			goToPersonnelSearch = function(positionId, ein) {
				dom.byId("personnelSearchForm").action = "/admin/action/personnel/goToSearchPersonnel?positionId=" + positionId + "&ein=" + ein;
				dom.byId("personnelSearchForm").submit();
			};

			submitAssignPersonnel = function(ein) {
				function processRequest(responseData, ioArgs) {
					dom.byId("assignPersonnelForm").action = "/admin/action/positions/viewDetails?positionTab=P&ein=" + ein;
					dom.byId("newPositionIds").value = responseData.positionIds;
					dom.byId("assignPersonnelForm").submit();
				}
				dojo.xhrPost({
					url : uMap['AP'],
					form : dom.byId('assignPersonnelForm'),
					handleAs : 'json',
					preventCache : "true",
					load : processRequest,
					error : function(error) {
						dom.byId("assignPersonnelForm").action = "/admin";
						dom.byId("assignPersonnelForm").submit();
					}
				});
			};

			createPersonnel = function(formName) {
				var actionTaken = dom.byId("actionTaken").value;
				var einNew = dom.byId("einNew").value;
				var positionId = dom.byId("positionId").value;
				var submitUri = "/admin/action/personnel/add/submitCreatePersonnel" + "?actionTaken=" + actionTaken;
				var positionRedirectUri = "/admin/action/personnel/add/cancel";
				var personnelRedirectUri = "/admin/action/personnel/add/continueToAssign" + "?einNew=" + einNew + "&positionId=" + positionId;
				registry.byId("modal_dialog_popup").hide();
				function processRequest(responseData, ioArgs) {
					if (typeof responseData !='object' && responseData.indexOf('id="loginFormId"') > -1) {
						dom.byId("positionDetailsFormId").action = "/admin";
						dom.byId("positionDetailsFormId").submit();
					}
					dom.byId("dataToBeReplaced").innerHTML = responseData;
					on(dom.byId("cancelButtonId"), "click", function() {
						window.location.replace(positionRedirectUri);
					});
					on(dom.byId("goToAssign"), "click", function() {
						window.location.replace(personnelRedirectUri);
					});
					registry.byId("modal_dialog_popup").show();
				}
				dojo.xhrGet({
					url : submitUri,
					form : dom.byId(formName),
					handleAs : 'text',
					preventCache : "true",
					load : processRequest,
					error : function(error) {
						dom.byId("positionsFormId").action = "/admin";
						dom.byId("positionsFormId").submit();
					}
				});
			};

			viewAssignmentDetails = function(tab, action, formName, positionId, ein) {
				var uri = uMap['PD'];

				uri += "?positionId=" + positionId + "&ein=" + ein

				registry.byId("modal_dialog_popup").hide();
				function processRequest(responseData, ioArgs) {
					if (typeof responseData !='object' && responseData.indexOf('id="loginFormId"') > -1) {
						dom.byId("positionDetailsFormId").action = "/admin";
						dom.byId("positionDetailsFormId").submit();
					}
					dom.byId("dataToBeReplaced").innerHTML = responseData;
					on(dom.byId("cancelButtonId"), "click", function() {
						registry.byId("modal_dialog_popup").hide();
					});
					registry.byId("modal_dialog_popup").show();
				}
				dojo.xhrPost({
					url : uri,
					form : dom.byId(formName),
					handleAs : 'text',
					preventCache : "true",
					load : processRequest,
					error : function(error) {
						dom.byId("positionsFormId").action = "/admin";
						dom.byId("positionsFormId").submit();
					}
				});
			};

			goToPersonnel = function(tab) {
				var uri = "";
				array.forEach(query(".showPageTR"), function(item, i) {
					if (item.id.indexOf("head_table_") > -1) {
						var t = item.id.split("_");
						dom.byId("positionId").value = t[2];
					}
				});
				if (tab == "P") {
					uri = "/admin/action/personnel/load";
				} else if (tab == "E") {
					uri = "/admin/action/equipment/load";
				} else if (tab == "D") {
					uri = "/admin/action/positions/viewPositionTab";
				}
				function processRequest(responseData, ioArgs) {
					if (typeof responseData !='object' && responseData.indexOf('id="loginFormId"') > -1) {
						dom.byId("positionDetailsFormId").action = "/admin";
						dom.byId("positionDetailsFormId").submit();
					}
					destroyWidgets("positionDetailsFormId");
					dom.byId("position_details_div_id").innerHTML = responseData;
					parser.parse(dom.byId("positionDetailsFormId"));
				}
				dojo.xhrPost({
					url : uri,
					form : dom.byId("positionDetailsFormId"),
					handleAs : 'text',
					load : processRequest,
					error : function(error) {
						dom.byId("positionsFormId").action = "/admin";
						dom.byId("positionsFormId").submit();
					}
				});
			};

			searchPersonnelResults = function() {
				dojo
						.xhrPost({
							url : "/admin/action/personnel/searchPersonnel",
							form : "personnelSearchForm",
							handleAs : "text",
							load : function(responseSearch) {
								if (responseSearch.indexOf('id="loginFormId"') > -1) {
									dom.byId("positionDetailsFormId").action = "/admin";
									dom.byId("positionDetailsFormId").submit();
								}
								if (responseSearch.indexOf("At Least One Search Parameter is Required") > -1) {
									dom.byId("searchError").innerHTML = "<span style='color:red;font-size:12px;font-weight:bold;'>At Least One Search Parameter is Required</span>";
									dom.byId("personnelSearchResults").innerHTML = "";
								} else {
									dom.byId("personnelSearchResults").innerHTML = responseSearch;
									dom.byId("searchError").innerHTML = "";
								}
							},
							error : function(error) {
								dom.byId("positionsFormId").action = "/admin";
								dom.byId("positionsFormId").submit();
							}
						});
			};

		});