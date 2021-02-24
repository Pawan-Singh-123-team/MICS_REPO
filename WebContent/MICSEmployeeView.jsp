<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
	
	function checkDelete(chkBox,name) {	
		
		currValue = chkBox.checked;
		elms = document.getElementsByName(name);
		
		elm = elms[0];
		if(elm.checked){
			alert("Sorry, Can not delete main public number, deselect first before try to delete");
			chkBox.checked = false;
		} else {
			chkBox.checked = currValue;
		}
	}
	
	function moveUserUp(pos){
		var tmpImsi = "";
		var tmpPublicNumber = "";
		var tmpNumberType = "";
		var elImsiPos = "micsUserNumberReq["+ pos+"].imsi";
		var elPubNumberPos = "micsUserNumberReq["+pos+"].publicNumber";
		var elNumTypePos =  "micsUserNumberReq["+pos+"].numberType";
		var elMainPubPos = "micsUserNumberReq["+pos+"].mainPublic";
		var elPrivNumPos = "micsUserNumberReq["+pos+"].privateNumber";
		var elMainPrivate = "micsUserNumberReq["+pos+"].mainPrivate";
		var elSipUri = "micsUserNumberReq["+pos+"].sipuri";
		var elOrderIndex = "micsUserNumberReq["+pos"].orderIndex";
		
		
		var targetPos = +pos - 1;
		tmpImsi = document.
	        tmp = document.forms[0].elements["peakMember1"].value;
	        document.forms[0].elements["peakMember1"].value = document.forms[0].elements["peakMember2"].value;
	        document.forms[0].elements["peakMember2"].value = tmp;
	}
	
	function moveUserDown(){
		
	}
</script>
<s:form action="" method="post" namespace="/" validate="true" >
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
		<th colspan="5" align="center">MICS Employee General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
		<s:hidden name="micsuserkey" />
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.firstName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="micsfirstname" key="mics.form.firstName" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.lastName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="micslastname" key="mics.form.lastName" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.memberType"/></td>
		<td class="textField">:</td>
		<td><s:select name="usertype" list="userTypes" listKey="id" listValue="name" label="Select Type" disabled="true" width="264" style="width: 264px"/>
			<s:hidden name="usertype"/>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.huntingOption"/></td>
		<td class="textField">:</td>
		<td><s:select name="huntingoption" list="huntingoptionList" listKey="id" listValue="name" label="Select Hunting Option" width="264" style="width: 264px" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.keepHuntingOnBusy"/></td>
		<td class="textField">:</td>
		<td><s:checkbox name="boolKeepHuntingOnBusy" fieldValue="true" label="mics.form.keepHuntingOnBusy" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.reverseCharging"/></td>
		<td class="textField">:</td>
		<td><s:checkbox name="boolReverseCharging" fieldValue="true" label="mics.form.reverseCharging" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.mainNumberAsCLI"/></td>
		<td class="textField">:</td>
		<td><s:checkbox name="boolMainNumberAcCli" fieldValue="true" label="mics.form.mainNumberAsCLI" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.mainNumberAsChargedParty"/></td>
		<td class="textField">:</td>
		<td><s:checkbox name="boolMainNumberAcChargedParty" fieldValue="true" label="mics.form.mainNumberAsChargedParty" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th colspan="5" align="center">MICS Employee Phone Number</th>
	</tr>
	
	<!--  Iterate User Number -->
	<s:if test="micsUserNumberReq != null && !micsUserNumberReq.isEmpty">
	<tr>
		<td>&nbsp;</td>
		<td colspan="3">
			<table>
				<thead>
					<tr>
						<th class="sortable">No</th>
						<th class="sortable">Public Number</th>
						<th class="sortable">Private Number</th>
						<th class="sortable">IMSI</th>
						<th class="sortable">SIP URI</th>
						<th class="sortable">Idx</th>
						<th class="sortable">Type</th>
						<th class="sortable">Req Stat</th>
					</tr>
				</thead>
			<tbody>
			<s:set name="currMainPublic" value="%{mainpublicnumber}" />
			<s:set name="currMainPrivate" value="%{mainprivatenumber}" />
			<s:iterator value="micsUserNumberReq" status="stat" var="numberReq" >
				<tr>
					<s:hidden name="micsUserNumberReq[%{#stat.index}].imsi" value="%{#numberReq.imsi}" />
					<s:hidden name="micsUserNumberReq[%{#stat.index}].publicNumber" value="%{#numberReq.publicNumber}" />
					<s:hidden name="micsUserNumberReq[%{#stat.index}].numberType" value="%{#numberReq.numberType}" />
					<td>
						<s:property value="%{#stat.count}"/>
					</td>
					
					<td>
						<s:property value="%{#numberReq.publicNumber}"/>
						 
						<s:if test='%{#numberReq.publicNumber == #currMainPublic}'>
							<s:checkbox name="micsUserNumberReq[%{#stat.index}].mainPublic" fieldValue="true" onClick="toggle(this,'N','mainPublic');" value="true" label="Set as Main" disabled="true"/>
							<s:text name="mics.form.setAsMain"/>
						</s:if>
						<s:else>
							<s:checkbox name="micsUserNumberReq[%{#stat.index}].mainPublic" fieldValue="true" onClick="toggle(this,'N','mainPublic');" label="Set as Main" disabled="true"/>
							<s:text name="mics.form.setAsMain"/>
						</s:else>
						
					</td>
					<td>
						<s:property value="%{#numberReq.privateNumber}"/>
						<s:if test='%{#numberReq.privateNumber == #currMainPrivate}'>
							<s:checkbox name="micsUserNumberReq[%{#stat.index}].mainPrivate" fieldValue="true" onClick="toggle(this,'Y','mainPrivate');" value="true" label="Set as Main" disabled="true"/>
							<s:text name="mics.form.setAsMain"/>
						</s:if>
						<s:else>
							<s:checkbox name="micsUserNumberReq[%{#stat.index}].mainPrivate" fieldValue="true" onClick="toggle(this,'Y','mainPrivate');" label="Set as Main" disabled="true"/>
							<s:text name="mics.form.setAsMain"/>
						</s:else>
						
					</td>
					<td>
						<s:property value="%{#numberReq.imsi}"/>
					</td>
					<td>
						<s:property value="%{#numberReq.sipuri}"/>
						
					</td>
					<td>
						<s:property value="%{#numberReq.orderIndex}"/>
					</td>
					<td>
						<s:text name="mics.label.numberType.%{#numberReq.numberType}"/>
					</td>
					<td>
						<!--<s:property value="%{#numberReq.prov_req_id}"/>-->
						<s:text name="mics.prov.ne.status.%{#numberReq.prov_req_status}"/>
					</td>
				</tr>
    		</s:iterator>
    			<tr>
    				<td colspan="5"></td>
    			</tr>
    		</tbody>
    		</table>
    		
    	</td>
		<td>&nbsp;</td>
	</tr>
	</s:if>
	<!--  End Iterator -->
	<tr>
	<th colspan="5" align="center">MICS Employee Permission</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3">
			<s:checkbox name="boolEnableAnnorRecording" fieldValue="true" label="mics.form.annoRecord" disabled="true"/><s:text name="mics.form.annoRecord"/>
			<s:checkbox name="boolLocked" fieldValue="true" label="mics.form.subcriberLocked" disabled="true"/><s:text name="mics.form.subcriberLocked"/>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th colspan="5" align="center">MICS Employee Properties</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupId"/></td>
		<td class="textField">:</td>
		<td>
			<!--<s:textfield name="usergroupid" key="mics.form.groupId" size="10" class="fontContent"/>-->
			<s:select name="groupkey" list="groups" listKey="groupid" listValue="%{groupid + ' - ' + groupname}" label="Select Type" width="264" style="width: 264px" disabled="true"/>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.subGroupId"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="subgroupid" key="mics.form.subGroupId" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.usergroupid"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="usergroupid" key="mics.form.usergroupid" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.annoLanguage"/></td>
		<td class="textField">:</td>
		<td><s:select name="language" list="languages" listKey="id" listValue="name" label="Select Language" width="264" style="width: 264px" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<th colspan="5" align="center">MICS Employee Outgoing Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="outcs" headerKey="" headerValue="Select Outgoing Profile" list='outgoingProfiles' listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" width="264" style="width: 264px" disabled="true"/>&nbsp;<!--<s:checkbox name="boolBypassOutCS" fieldValue="true" label="mics.form.bypass"/><s:text name="mics.form.bypass"/>--></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<th colspan="5" align="center">MICS Employee Incoming Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="incs" headerKey="" headerValue="Select Incoming Profile" list='incomingProfiles' listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" width="264" style="width: 264px" disabled="true"/>&nbsp;<!--<s:checkbox name="boolBypassInCS" fieldValue="true" label="mics.form.bypass"/><s:text name="mics.form.bypass"/>--></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"></td>
		<td>&nbsp;</td>
	</tr>
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


