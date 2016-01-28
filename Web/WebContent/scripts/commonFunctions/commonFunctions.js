require(
		[ "dijit/registry", "dojo/parser", "dojo/ready", "dojo/query", "dojo/dom-class", 'dojo/dom', "dojo/dom-construct",
				"dojo/_base/array", "dijit/Dialog", "dojo/_base/event", "dojo/has", "dojo/on", "dojo/_base/sniff", "dojo/string",
				"dojo/dom-attr" ], function(registry, parser, ready, query, domClass, dom, constructDom, array, dialog, event, has, on,
				sniff, string, attr) {
			// Records per page
			var recordsPerPage = 10;

			// ********************************************************************************************

			ready(function() {
				selectMenuItem();
			});

			// ********************************************************************************************

			selectMenuItem = function() {
				query(".open").forEach(function(node) {
					domClass.remove(node, "open");
					domClass.add(node, "closed");
				});
				var pageId = "v_menu_item" + dom.byId("pageId").value;
				query("[id$=" + pageId + "]").forEach(function(node) {
					domClass.remove(node, "closed");
					domClass.add(node, "open");
				});
				return false;
			};

			// ********************************************************************************************

			linkRefreshIE8 = function() {
				if (has("ie") == 8) {
					// refreshes page. fix for IE8
					array.forEach(document.getElementsByTagName("link"), function(link) {
						if (link.rel === "stylesheet") {
							link.href += "?";
						}
					});
				}
			};

			// *********************************************************************************

			toggleUI = function(node, showClass, hideClass) {
				try {
					if (showClass != null && showClass != "") {
						domClass.add(node, showClass);
					}
					if (hideClass != null && hideClass != "") {
						domClass.remove(node, hideClass);
					}
				} catch (e) {
					console.log("toggleUI Error\n" + e);
				}
			};

			// *********************************************************************************

			insertItems = function(trr) {
				array.forEach(trr, function(item, i) {
					domClass.remove(item, "even");
					domClass.remove(item, "odd");
					if (i % 2 == 0) {
						domClass.add(item, "odd");
					} else {
						domClass.add(item, "even");
					}
					constructDom.place(item, "tbody_id");
				});
			};

			// *****************************************************************************************

			cursorWait = function(check) {
				if (check) {
					registry.byId('loadingImage').show();
				} else {
					registry.byId('loadingImage').hide();
				}
				return check;
			};

			// *****************************************************************************************

			searchFilter = function(searchParamList, selectedFilter) {
				filteredList = dojo.query("[id^='tbl_row_']");
				var counter = 0;
				var tmpList = array.filter(filteredList, function(item) {
					var check = array.some(searchParamList, function(node, i) {
						var field1 = string.trim(attr.get(query(node, item)[0], 'innerHTML')).toLowerCase();
						if (field1.indexOf(selectedFilter.toLowerCase()) > -1) {
							return true;
						}
						return false;
					});
					if (check ) {
						toggleUI(item, "searchBy_Filter_Class", "");
						if(counter < recordsPerPage){
							toggleUI(item, "showPageTR", "hidePageTR");
						}else{
							toggleUI(item, "hidePageTR", "showPageTR");
						}	
						counter++;
					} else {
						toggleUI(item, "", "searchBy_Filter_Class");
						toggleUI(item, "hidePageTR", "showPageTR");
					}
					
					return check;
				});

				// if there are matching records don't display text that data
				// not found
				if (tmpList.length > 0) {
					constructDom.destroy(dom.byId("noRecordsId"));
				}
				// if no matching records
				else {
					if (!dom.byId("noRecordsId")) {
						var cells = dom.byId("tr_header_id").cells;
						constructDom.create("tr", {
							id : "noRecordsId",
							className : "ActsAsLink",
							innerHTML : "<td colspan='" + cells.length + "' style='text-align:center'>No data has been found</td>"
						}, dom.byId("tbody_id"));
					}
				}
				replacePageLink(1);
			};

			// *********************************************************************************

			replacePage = function(newPage) {
				var tmpList = query("[id^='tbl_row_']");
				var maxNum = parseInt(newPage * recordsPerPage);
				if (newPage == "0") {
					maxNum = tmpList.length;
				} else if (newPage == null || newPage == '') {
					newPage = 1;
				}
				dom.byId("currentPage").value = newPage;
				var minNum = 0;
				if (newPage > 1) {
					minNum = parseInt((newPage - 1) * recordsPerPage);
				}
				array.forEach(tmpList, function(item, i) {
					if (i >= minNum && i < maxNum) {
						toggleUI(item, "showPageTR", "hidePageTR");
					} else {
						toggleUI(item, "hidePageTR", "showPageTR");
					}
					insertItems(item, i, "tbody_id");
				});
				if (dom.byId("displayAllId") != null && dom.byId("displayAllId") != undefined) {
					domClass.add(dom.byId("displayAllId"), "paginationDiv");
				}
				replacePageLink(newPage);
				return false;
			};

			// *********************************************************************************

			replacePageLink = function(pageNumber) {
				var recordList = query('.searchBy_Filter_Class');
				if (recordList.length == 0 && (dom.byId('searchInput') == undefined || dom.byId('searchInput').value == '')) {
					recordList = query("[id^='tbl_row_']");
				}
				var totalPages = calculateNumberOfPages(recordList.length);
				if (totalPages > 1) {
					array.forEach(query(".page-link-div"), function(item, i) {
						if ((i + 1) != pageNumber) {
							toggleUI(item, "showPageDiv", "hidePageDiv");
						} else {
							toggleUI(item, "hidePageDiv", "showPageDiv");
						}
					});
					array.forEach(query(".page-nonlink-div"), function(item, i) {
						if ((i + 1) != pageNumber) {
							toggleUI(item, "hidePageDiv", "showPageDiv");
						} else {
							toggleUI(item, "showPageDiv", "hidePageDiv");
						}
					});
					toggleUI(dom.byId("paginationId"), "showPageDiv", "hidePageDiv");
				} else {
					toggleUI(dom.byId("paginationId"), "hidePageDiv", "showPageDiv");
				}
			};

			// *****************************************************************************************

			calculateNumberOfPages = function(arrayOfRecords) {
				var numberOfPages = 0;
				if (arrayOfRecords != undefined) {
					if (recordsPerPage < arrayOfRecords) {
						numberOfPages = Math.ceil(arrayOfRecords / recordsPerPage);
					}
				}
				return numberOfPages;
			};

			// *****************************************************************************************

			destroyWidgets = function(name) {
				var widgets = dijit.findWidgets(dom.byId(name));
				// Before update
				if (widgets) {
					array.forEach(widgets, function(widget, i) {
						widget.destroyRecursive();
					});
				}
			}
		});