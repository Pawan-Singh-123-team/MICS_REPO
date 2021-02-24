<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<script type="text/javascript">
	function toggle(chkBox,keepValue,name) {	
		currValue = chkBox.checked;
		for(i = 0; i < document.forms[0].elements.length; i++) {
			elm = document.forms[0].elements[i];
			if (elm.type == 'checkbox') {
				if (elm.name.endsWith(name)) {
					elm.checked = false;
				}
			}
		}
		if(keepValue == "Y"){
			//Keep the current value
			chkBox.checked = currValue;
		} else {
			chkBox.checked = true;
		}
	}	
	

</script>
<s:form action="companySwitchboardListSearchDest.action" method="post" namespace="/">
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
			<th colspan="5" align="center">MICS Company Switchboard Search Destination</th>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.firstName"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="searchFirstName" key="mics.form.firstName" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
			<s:hidden name="companykey" />
			<s:hidden name="switchboardName"  />  
        	<s:hidden name="switchboardKey"  /> 
			<s:hidden name="switchboardPublicNumber" />
			<s:hidden name="switchboardPrivateNumber" />
			<s:hidden name="annoId" />
			<s:hidden name="destMenuOrSubmenu"/>
			<s:hidden name="menuIndex"/>
			<s:hidden name="submenuIndex"/>
			<s:hidden name="firstSearchDest" value="false"/>
			<s:hidden name="currDestination"/>
			
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.lastName"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="searchLastName" key="mics.form.lastName" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.publicNumber"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="searchPubNumber" key="mics.form.publicNumber" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.privateNumber"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="searchPrivNumber" key="mics.form.privateNumber" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="3" class="textField"><s:submit method="huntingGroupScheduleSearchMember" key="mics.form.search" align="center" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="5">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="5">
			<!--  Iterate User Number -->
			<s:if test="searchMemberRes != null && !searchMemberRes.isEmpty">
			<table>
				<thead>
					<tr>
						<th class="sortable">No</th>
						<th class="sortable">First Name</th>
						<th class="sortable">Last Name</th>
						<th class="sortable">Public Number</th>
						<th class="sortable">Private Number</th>
						<th class="sortable">Select</th>
					</tr>
				</thead>
			<tbody>
			<s:iterator value="searchMemberRes" status="stat" var="iterMemberRes" >
				<tr>
					<s:hidden name="searchMemberRes[%{#stat.index}].micsUserKey" value="%{#iterMemberRes.micsUserKey}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsFirstName" value="%{#iterMemberRes.micsFirstName}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsLastName" value="%{#iterMemberRes.micsLastName}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsPubNumber" value="%{#iterMemberRes.micsPubNumber}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsPrivNumber" value="%{#iterMemberRes.micsPrivNumber}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsCompanyKey" value="%{#iterMemberRes.micsCompanyKey}" />
					<td>
						<s:property value="%{#stat.count}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberRes.micsFirstName}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberRes.micsLastName}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberRes.micsPubNumber}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberRes.micsPrivNumber}"/>
					</td>
					<td>
						<s:checkbox name="searchMemberRes[%{#stat.index}].selected" fieldValue="true" onClick="toggle(this,'N','selected');" label="Selected"/>
					</td>
				</tr>
    		</s:iterator>
    			<tr>
    				<td colspan="6"><s:submit action="companySwitchboardListAddDest" key="mics.form.add" align="center" /></td>
    			</tr>
    		</tbody>
    		</table>
    	</td>
		<td>&nbsp;</td>
	</tr>
	</table>
	</s:if>
	<!--  End Iterator -->
			
		</td>
	</tr>
</table>



</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


