<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<s:set var="maxMenuLimit" value="9" />
<s:set var="maxSubMenuLimit" value="9" />
<s:set var="newsubmenucounter" value="0" />
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/scriptaculous.js?load=effects"></script>
<script type="text/javascript">
var maxMenuLimit = ${maxMenuLimit};
var maxSubMenuLimit = ${maxSubMenuLimit};
var menuCounter = ${switchboardmenus.size()};

function addMenu() {
	if(menuCounter == 0) {
		menuCounter = 1;
	}
	if(menuCounter >= maxMenuLimit) {
		alert("Maximum of "+ maxMenuLimit + " menu allowed within a switchboard");
	}
	else {
		var outerMenu = document.getElementById('outerMenu');
		var innerMenu = outerMenu.rows[1+menuCounter];
		innerMenu.appear();
		menuCounter++;
	}
}

function deleteMenu(myNode) {
	if(menuCounter == 1) {
		alert("Minimum of 1 main menu allowed within a switchboard");
	}
	else {
		if(confirm("Deleting one menu will reorder the rest of the menus. Continue?")) {
			var outerMenu = document.getElementById('outerMenu');
			var menuTr = myNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;

			var trIndex = 1;
			for(var singleTr; singleTr = outerMenu.rows[trIndex]; trIndex++) {
				if(singleTr == menuTr) {
					break;
				}
			}
			var originalMenukey = outerMenu.rows[trIndex].cells[0].childNodes[0].rows[0].cells[0].childNodes[1].value;
			var originalSubmenukeys = [];
			for(var i=1; i <= maxSubMenuLimit; i++) {
				originalSubmenukeys[i] = outerMenu.rows[trIndex].cells[1].childNodes[0].rows[i].cells[0].childNodes[1].value;
			}
			
			var trIndexRearrange = trIndex + 1;
			for(var singleTr; (singleTr = outerMenu.rows[trIndexRearrange]) && singleTr.style.display != "none" && trIndexRearrange <= maxMenuLimit; trIndexRearrange++) {
				copyMenuFields(singleTr, outerMenu.rows[trIndexRearrange-1]);
			}
			resetMenuFields(outerMenu.rows[trIndexRearrange-1]);
			outerMenu.rows[trIndexRearrange-1].cells[0].childNodes[0].rows[0].cells[0].childNodes[1].value = originalMenukey;
			for(var i=1; i <= maxSubMenuLimit; i++) {
				outerMenu.rows[trIndexRearrange-1].cells[1].childNodes[0].rows[i].cells[0].childNodes[1].value = originalSubmenukeys[i];
			}
			
			outerMenu.rows[menuCounter].hide();
			menuCounter--;
		}
	}
	return false;
}

function copyMenuFields(fromMenuTr, toMenuTr) {
	var fromMenuTable = fromMenuTr.cells[0].childNodes[0];
	var toMenuTable = toMenuTr.cells[0].childNodes[0];
	
	toMenuTable.rows[0].cells[0].childNodes[1].value = fromMenuTable.rows[0].cells[0].childNodes[1].value;
	toMenuTable.rows[0].cells[2].childNodes[0].value = fromMenuTable.rows[0].cells[2].childNodes[0].value;
	toMenuTable.rows[0].cells[3].childNodes[1].value = fromMenuTable.rows[0].cells[3].childNodes[1].value;
	
	var fromSubMenuTable = fromMenuTr.cells[1].childNodes[0];
	var toSubMenuTable = toMenuTr.cells[1].childNodes[0];
	
	toSubMenuTable.rows[0].cells[1].childNodes[0].selectedIndex = fromSubMenuTable.rows[0].cells[1].childNodes[0].selectedIndex;
	
	for(var i=1; i <= maxSubMenuLimit; i++) {
		copySubMenuFields(fromSubMenuTable.rows[i], toSubMenuTable.rows[i]);
		if(fromSubMenuTable.rows[i].style.display == "none") {
			toSubMenuTable.rows[i].hide();
		}
		else {
			toSubMenuTable.rows[i].appear();
		}
	}
	
	changeMenuOption(toMenuTable.rows[0].cells[2].childNodes[0]);
}

function resetMenuFields(toMenuTr) {
	var toMenuTable = toMenuTr.cells[0].childNodes[0];
	
	//toMenuTable.rows[0].cells[0].childNodes[1].value = "";
	toMenuTable.rows[0].cells[2].childNodes[0].value = "0";
	toMenuTable.rows[0].cells[3].childNodes[1].value = "";
	
	var toSubMenuTable = toMenuTr.cells[1].childNodes[0];
	
	toSubMenuTable.rows[0].cells[1].childNodes[0].selectedIndex = -1;
	
	for(var i=1; i <= maxSubMenuLimit; i++) {
		resetSubMenuFields(toSubMenuTable.rows[i]);
		if(i != 1) {
			toSubMenuTable.rows[i].hide();
		}
	}
	
	changeMenuOption(toMenuTable.rows[0].cells[2].childNodes[0]);
}

function addSubMenu(myNode) {
	var subMenuTable = myNode.parentNode.childNodes[0];

	var subMenuCounter = 1;
	while(subMenuCounter < maxSubMenuLimit) {
		if(subMenuTable.rows[1+subMenuCounter].style.display == "none") {
			break;
		}
		subMenuCounter++;
	}
	
	if(subMenuCounter >= maxSubMenuLimit) {
		alert("Maximum of "+ maxSubMenuLimit + " sub menu allowed within one menu");
	}
	else {
		subMenuTable.rows[1+subMenuCounter].appear();
		changeSubMenuOption(subMenuTable.rows[1+subMenuCounter].cells[2].childNodes[0]);
	}
}

function deleteSubMenu(myNode) {
	var subMenuTr = myNode.parentNode.parentNode;
	var subMenuTable = subMenuTr.parentNode;
	
	var subMenuCounter = 1;
	while(subMenuCounter < maxSubMenuLimit) {
		if(subMenuTable.rows[1+subMenuCounter].style.display == "none") {
			break;
		}
		subMenuCounter++;
	}
	
	if(subMenuCounter == 1) {
		alert("Minimum of 1 main menu allowed within a switchboard");
	}
	else {
		if(confirm("Deleting one submenu will reorder the rest of the submenus. Continue?")) {
			var trIndex = 1;
			for(var singleTr; singleTr = subMenuTable.rows[trIndex]; trIndex++) {
				if(singleTr == subMenuTr) {
					break;
				}
			}
			var originalSubmenukey = subMenuTable.rows[trIndex].cells[0].childNodes[1].value;
			
			var trIndexRearrange = trIndex + 1;
			for(var singleTr; (singleTr = subMenuTable.rows[trIndexRearrange]) && singleTr.style.display != "none"; trIndexRearrange++) {
				copySubMenuFields(singleTr, subMenuTable.rows[trIndexRearrange-1]);
			}
			resetSubMenuFields(subMenuTable.rows[trIndexRearrange-1]);
			subMenuTable.rows[trIndexRearrange-1].cells[0].childNodes[1].value = originalSubmenukey;
			
			subMenuTable.rows[subMenuCounter].hide();
		}
	}
	return false;
}

function copySubMenuFields(fromSubMenuTr, toSubMenuTr) {
	toSubMenuTr.cells[0].childNodes[1].value = fromSubMenuTr.cells[0].childNodes[1].value;
	toSubMenuTr.cells[2].childNodes[0].value = fromSubMenuTr.cells[2].childNodes[0].value;
	toSubMenuTr.cells[3].childNodes[1].value = fromSubMenuTr.cells[3].childNodes[1].value;
	changeSubMenuOption(toSubMenuTr.cells[2].childNodes[0]);
}

function resetSubMenuFields(subMenuTr) {
	//subMenuTr.cells[0].childNodes[1].value = "";
	subMenuTr.cells[2].childNodes[0].value = "0";
	subMenuTr.cells[3].childNodes[1].value = "";
	changeSubMenuOption(subMenuTr.cells[2].childNodes[0]);
}

function changeMenuOption(myNode) {
	var innerMenu = myNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
	var destinationTd = innerMenu.cells[0].childNodes[0].rows[0].cells[3];
	var subMenuTd = innerMenu.cells[1];
	
	var hideSubMenus = true;
	if(myNode.value == "0") {
		destinationTd.style.visibility = "hidden";
		subMenuTd.style.visibility = "hidden";
	}
	else if(myNode.value == "1") {
		destinationTd.style.visibility = "hidden";
		subMenuTd.style.visibility = "visible";
		hideSubMenus = false;
	}
	else if(myNode.value == "2") {
		destinationTd.style.visibility = "visible";
		subMenuTd.style.visibility = "hidden";
	}
	
	var subMenuRows;
	for(var i = 1; subMenuRows = subMenuTd.childNodes[0].rows[i]; i++) {
		if(hideSubMenus) {
			subMenuRows.cells[3].style.visibility = "hidden";;
		}
		else {
			changeSubMenuOption(subMenuRows.cells[2].childNodes[0]);
		}
	}
}

function changeSubMenuOption(myNode) {
	var destinationTd = myNode.parentNode.parentNode.cells[3];
	if(myNode.value == "0") {
		destinationTd.childNodes[1].value = "";
		destinationTd.style.visibility = "hidden";
	}
	else if(myNode.value == "2") {
		destinationTd.style.visibility = "visible";
	}
}

function chooseDestination(destMenuOrSubmenu, menuIndex, submenuIndex) {
  document.companyInitAutoSwitchbrdListSave.action="companySwitchboardListInitSearchDest.action";
  document.companyInitAutoSwitchbrdListSave.destMenuOrSubmenu.value=destMenuOrSubmenu;
  document.companyInitAutoSwitchbrdListSave.menuIndex.value=menuIndex;
  document.companyInitAutoSwitchbrdListSave.submenuIndex.value=submenuIndex;
  document.companyInitAutoSwitchbrdListSave.submit();
}
</script>
<s:form action="companyInitAutoSwitchbrdListSave.action" method="post" namespace="/">
<table class="tableContainer">
<tr>
	<td>&nbsp;</td>
	<td>
	<s:actionerror />
  	<s:fielderror />
  	<s:actionmessage/>
	</td>
	<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td  valign="top">
<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">Switchboard</th>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.switchboardName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="switchboard.switchboardname" key="mics.form.switchboardName" size="30" class="fontContent" /></td>
		<s:hidden name="switchboard.switchboardkey"/>
		<s:hidden name="switchboard.companykey"/>
		<s:hidden name="destMenuOrSubmenu"/>
		<s:hidden name="menuIndex"/>
		<s:hidden name="submenuIndex"/>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.publicNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="switchboard.publicnumber" key="mics.form.publicNumber" size="30" class="fontContent" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.privateNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="switchboard.privatenumber" key="mics.form.privateNumber" size="30" class="fontContent" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.announcement"/></td>
		<td class="textField">:</td>
		<td><s:select name="switchboard.annoid" list="annoList" listKey="annoid" listValue="announcementname" label="Select Anno"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="4">
		
<table id="outerMenu" class="formOutlineOrange" style="vertical-align:top">
	<tr><th>Main Menu</th><th>Sub Menu</th></tr>
	
	<s:iterator value="switchboardmenus" var="switchboardmenu" status="status">
		<tr style="vertical-align:top">
			<td><table style="vertical-align:top">
				<tr>
					<td class="textField" nowrap="nowrap"><input type="image" onclick="return deleteMenu(this);" src="images/trash.gif" alt="delete" /><s:hidden name="switchboardmenus[%{#status.index}].switchboardmenukey" value="%{#switchboardmenu.switchboardmenukey}" /><s:hidden name="switchboardmenus[%{#status.index}].dialdigit" value="%{#switchboardmenu.dialdigit}" /></td>
					<td>Menu <s:property value="dialdigit" />:</td>
					<td><s:select name="switchboardmenus[%{#status.index}].menutype" value="%{#switchboardmenu.menutype}" list="switchList" listKey="id" listValue="name" onchange="changeMenuOption(this);"/></td>
					<td style="visibility:<s:if test="%{#switchboardmenu.menutype==2}">visible</s:if><s:else>hidden</s:else>;" nowrap="nowrap"><input value="Edit" onclick="chooseDestination(1,<s:property value="#status.index" />,-1)" style="width: 75px;" type="button" /><s:textfield readonly="true" name="switchboardmenus[%{#status.index}].destinationnumber" value="%{#switchboardmenu.destinationnumber}" size="15" class="fontContent" /></td>
				</tr>
			</table></td>
			<td style="visibility:<s:if test="%{#switchboardmenu.menutype==1}">visible</s:if><s:else>hidden</s:else>;vertical-align:top"><table style="vertical-align:top">
					<tr>
						<td colspan="2" nowrap="nowrap">Announcement:</td>
						<td colspan="2" nowrap="nowrap"><s:select name="switchboardmenus[%{#status.index}].annoid" value="%{#switchboardmenu.annoid}" list="annoList" listKey="annoid" listValue="announcementname" /></td>
					</tr>
					<s:iterator value="switchboardsubmenus[#status.index]" var="switchboardsubmenu" status="substatus">
						<tr style="<s:if test="%{(#switchboardsubmenu.destinationnumber==null || #switchboardsubmenu.destinationnumber==\"\") && #switchboardsubmenu.dialdigit != 1 }">display:none;</s:if>">
							<td class="textField" nowrap="nowrap"><input type="image" onclick="return deleteSubMenu(this);" src="images/trash.gif" alt="delete" /><s:hidden name="newsubmenus[%{#newsubmenucounter}].switchboardsubmenukey" value="%{#switchboardsubmenu.switchboardsubmenukey}" /><s:hidden name="newsubmenus[%{#newsubmenucounter}].dialdigit" value="%{#switchboardsubmenu.dialdigit}" /></td>
							<td nowrap="nowrap">Sub Menu <s:property value="dialdigit" />:</td>
							<td><s:if test="%{#switchboardsubmenu.destinationnumber!=null && #switchboardsubmenu.destinationnumber!=\"\" && #switchboardsubmenu.destinationnumber!=\" \"}"><s:select value="2" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSubMenuOption(this);"/></s:if><s:else><s:select value="0" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSubMenuOption(this);"/></s:else></td>
							<td style="<s:if test="%{#switchboardsubmenu.destinationnumber!=null && #switchboardsubmenu.destinationnumber!=\"\" && #switchboardsubmenu.destinationnumber!=\" \" && #switchboardmenu.menutype==1}">visibility:visible</s:if><s:elseif test="%{#switchboardsubmenu.destinationnumber==null || #switchboardsubmenu.destinationnumber==\"\" || #switchboardsubmenu.destinationnumber==\" \"}">visibility:hidden</s:elseif>;" nowrap="nowrap"><input name="" value="Edit" onclick="chooseDestination(2,<s:property value="#status.index" />,<s:property value="#substatus.index" />)" style="width: 75px;" type="button" /><s:textfield readonly="true" name="newsubmenus[%{#newsubmenucounter}].destinationnumber" value="%{#switchboardsubmenu.destinationnumber}" size="15" class="fontContent" /></td>
						</tr>
						<s:set var="newsubmenucounter" value="#newsubmenucounter+1" />
					</s:iterator>
					<s:iterator var="counter2" begin="%{switchboardsubmenus[#status.index].size()}" end="%{#maxSubMenuLimit-1}">
						<tr style="<s:if test="%{#counter2!=0}">display:none;</s:if>">
							<td class="textField" nowrap="nowrap"><input type="image" onclick="return deleteSubMenu(this);" src="images/trash.gif" alt="delete" /><s:hidden name="newsubmenus[%{#newsubmenucounter}].switchboardsubmenukey" /><s:hidden name="newsubmenus[%{#newsubmenucounter}].dialdigit" value="%{#counter2+1}" /></td>
							<td nowrap="nowrap">Sub Menu <s:property value="#counter2+1" />:</td>
							<td><s:select value="0" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSubMenuOption(this);" /></td>
							<td nowrap="nowrap" style="visibility:hidden"><input name="" value="Edit"  onclick="chooseDestination(2,<s:property value="#status.index" />,<s:property value="#counter2" />)" style="width: 75px;" type="button" /><s:textfield readonly="true" name="newsubmenus[%{#newsubmenucounter}].destinationnumber" size="15" class="fontContent" /></td>
						</tr>
						<s:set var="newsubmenucounter" value="#newsubmenucounter+1" />
					</s:iterator>
				</table>
				<input type="button" onclick="addSubMenu(this);" value="Add More" />
			</td>
		</tr>
	</s:iterator>
	<s:iterator var="counter" begin="%{switchboardmenus.size()}" end="%{#maxMenuLimit-1}">
		<tr style="<s:if test="%{#counter!=0}">display:none;</s:if>vertical-align:top">
			<td><table style="vertical-align:top">
				<tr>
					<td class="textField" nowrap="nowrap"><input type="image" onclick="return deleteMenu(this);" src="images/trash.gif" alt="delete" /><s:hidden name="switchboardmenus[%{#counter}].switchboardmenukey" /><s:hidden name="switchboardmenus[%{#counter}].dialdigit" value="%{#counter+1}" /></td>
					<td>Menu <s:property value="#counter+1" />:</td>
					<td><s:select name="switchboardmenus[%{#counter}].menutype" list="switchList" listKey="id" listValue="name" onchange="changeMenuOption(this);"/></td>
					<td style="visibility:hidden;" nowrap="nowrap"><input value="Edit" onclick="chooseDestination(1,<s:property value="#counter" />,-1)" style="width: 75px;" type="button" /><s:textfield readonly="true" name="switchboardmenus[%{#counter}].destinationnumber" size="15" class="fontContent" /></td>
				</tr>
			</table></td>
			<td style="visibility:hidden;vertical-align:top"><table style="vertical-align:top">
					<tr>
						<td colspan="2" nowrap="nowrap">Announcement:</td>
						<td colspan="2" nowrap="nowrap"><s:select name="switchboardmenus[%{#counter}].annoid" list="annoList" listKey="annoid" listValue="announcementname" /></td>
					</tr>
					
					<tr>
						<td class="textField" nowrap="nowrap"><input type="image" onclick="return deleteSubMenu(this);" src="images/trash.gif" alt="delete" /><s:hidden name="newsubmenus[%{#newsubmenucounter}].switchboardsubmenukey" /><s:hidden name="newsubmenus[%{#newsubmenucounter}].dialdigit" value="1" /></td>
						<td nowrap="nowrap">Sub Menu <s:property value="1" />:</td>
						<td><s:select value="0" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSubMenuOption(this);" /></td>
						<td nowrap="nowrap"><input name="" value="Edit" onclick="chooseDestination(2,<s:property value="#counter" />,0)" style="width: 75px;" type="button" /><s:textfield readonly="true" name="newsubmenus[%{#newsubmenucounter}].destinationnumber" size="15" class="fontContent" /></td>
					</tr>
					<s:set var="newsubmenucounter" value="#newsubmenucounter+1" />
					<s:iterator var="counter2" begin="1" end="%{#maxSubMenuLimit-1}">
						<tr style="display:none;">
							<td class="textField" nowrap="nowrap"><input type="image" onclick="return deleteSubMenu(this);" src="images/trash.gif" alt="delete" /><s:hidden name="newsubmenus[%{#newsubmenucounter}].switchboardsubmenukey" /><s:hidden name="newsubmenus[%{#newsubmenucounter}].dialdigit" value="%{#counter2+1}" /></td>
							<td nowrap="nowrap">Sub Menu <s:property value="#counter2+1" />:</td>
							<td><s:select value="0" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSubMenuOption(this);" /></td>
							<td nowrap="nowrap"><input name="" value="Edit" onclick="chooseDestination(2,<s:property value="#counter" />,<s:property value="#counter2" />)" style="width: 75px;" type="button" /><s:textfield readonly="true" name="newsubmenus[%{#newsubmenucounter}].destinationnumber" size="15" class="fontContent" /></td>
						</tr>
						<s:set var="newsubmenucounter" value="#newsubmenucounter+1" />
					</s:iterator>
				</table>
				<input type="button" onclick="addSubMenu(this);" value="Add More" />
			</td>
		</tr>
	</s:iterator>

	<tr><td colspan="2">
		<input type="button" onclick="addMenu();" value="Add More Menu" />
	</td></tr>
</table>

		</td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.submit" align="center" value="Save" /><s:submit key="mics.form.list" align="center" value="Back to List" action="companyInitAutoSwitchbrdList" /></td>
		<td>&nbsp;</td>
	</tr>
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>
