<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

function onCompanyChange() {

document.forms[0].action = 'huntingGroupCPYChange.action';

document.forms[0].submit();

}
function clickAddButton() {
    document.huntingGroupEdit.action="huntingGroupEditAddTimeband.action";
    document.huntingGroupEdit.submit();
  }

function clickEditButton(idx) {
    document.huntingGroupEdit.action="huntingGroupViewScheduleEdit.action";
    document.huntingGroupEdit.targetTimebandIdx.value=idx;
    document.huntingGroupEdit.submit();
  }
  
function moveUp(idx) {
    document.huntingGroupEdit.action="hgEditTimebandMoveUp.action";
    document.huntingGroupEdit.targetTimebandIdx.value=idx;
    document.huntingGroupEdit.submit();
  }
  
function moveDown(idx) {
    document.huntingGroupEdit.action="hgEditTimebandMoveDown.action";
    document.huntingGroupEdit.targetTimebandIdx.value=idx;
    document.huntingGroupEdit.submit();
  }

</script>
<!-- $Id: MICSHuntingGroupView.jsp,v 1.1.4.3 2019/03/22 10:05:37 cvsuser Exp $ -->
<s:form action="" method="post" namespace="/">
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
	<s:hidden name="targetTimebandIdx"/>
	<s:hidden name="maxTimeband"/>
	<tr>
		<th colspan="5" align="center">Hunting Group View</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
		<s:hidden name="huntinggroupkey"/>
	</tr>

	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.huntingGroupName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="huntinggroupname" key="mics.form.huntingGroupName" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.publicNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="publicnumber" key="mics.form.publicNumber" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.privateNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="privatenumber" key="mics.form.privateNumber" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.welcomeAnno"/></td>
		<td class="textField">:</td>
		<td><s:select name="welcomeannoid" headerKey="0" headerValue="Select Announcement" list="annoList" listKey="annoid" listValue="%{annoid + ' - ' + announcementname}" label="Select Anno" width="264" style="width: 264px" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.memberNAAnno"/></td>
		<td class="textField">:</td>
		<td><s:select name="membernaannoid" headerKey="0" headerValue="Select Announcement" list="annoList" listKey="annoid" listValue="%{annoid + ' - ' + announcementname}" label="Select Anno" width="264" style="width: 264px" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.memberNRAnno"/></td>
		<td class="textField">:</td>
		<td><s:select name="membernrannoid" headerKey="0" headerValue="Select Announcement" list="annoList" listKey="annoid" listValue="%{annoid + ' - ' + announcementname}" label="Select Anno" width="264" style="width: 264px" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.huntingCliPrefix"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="huntingcliprefix" key="mics.form.huntingCliPrefix" size="35" class="fontContent" disabled="true"/></td>
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
		<td class="textField"><s:text name="mics.form.reverseCharging"/></td>
		<td class="textField">:</td>
		<td><s:checkbox name="boolReverseCharging" fieldValue="true" label="mics.form.reverseCharging"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th colspan="5" align="center">Time Window</th>
	</tr>
	<!--  Iterate Timeband -->
	<s:if test="huntingscheduleReg != null && !huntingscheduleReg.isEmpty">
	<tr>
		<td>&nbsp;</td>
		<td colspan="3">
			<table>
				<thead>
					<tr>
						<th class="sortable">No</th>
						<th class="sortable">Timeband Name</th>
						<th class="sortable">Timeband Desc</th>
						<th class="sortable">Member</th>
					</tr>
				</thead>
			<tbody>
			<s:iterator value="huntingscheduleReg" status="stat" var="hsReq" >
				<tr>
					
					<s:hidden name="huntingscheduleReg[%{#stat.index}].huntingschedulekey" value="%{#hsReq.huntingschedulekey}" />
					<s:hidden name="huntingscheduleReg[%{#stat.index}].timebandkey" value="%{#hsReq.timebandkey}" />
					<s:hidden name="huntingscheduleReg[%{#stat.index}].huntinggroupkey" value="%{#hsReq.huntinggroupkey}" />
					
					<td>
						<s:textfield name="huntingscheduleReg[%{#stat.index}].orderindex" key="mics.form.orderIndex" size="2" class="fontContent" disabled="true"/>
					</td>
					<td>
						<s:textfield name="huntingscheduleReg[%{#stat.index}].timebandname" key="mics.form.timebandName" size="15" class="fontContent" disabled="true"/>
					</td>
					<td>
						<s:textfield name="huntingscheduleReg[%{#stat.index}].timebanddesc" key="mics.form.timebandDesc" size="25" class="fontContent" disabled="true"/>
					</td>
					<td>
						<!--<s:submit action="huntingGroupViewSchedule" key="mics.form.editScheduleMember" onclick="clickEditButton('%{#stat.index}')" align="center" />-->          
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


