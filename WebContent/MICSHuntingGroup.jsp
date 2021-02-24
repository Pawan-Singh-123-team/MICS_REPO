 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

function onCompanyChange() {

document.forms[0].action = 'huntingGroupCPYChange.action';

document.forms[0].submit();

}
function clickAddButton() {
    document.huntingGroupCreate.action="huntingGroupAddTimeband.action";
    document.huntingGroupCreate.submit();
  }

function clickEditButton(idx) {
    document.huntingGroupCreate.action="huntingGroupViewSchedule.action";
    document.huntingGroupCreate.targetTimebandIdx.value=idx;
    document.huntingGroupCreate.submit();
  }
  
function moveUp(idx) {
    document.huntingGroupCreate.action="timebandMoveUp.action";
    document.huntingGroupCreate.targetTimebandIdx.value=idx;
    document.huntingGroupCreate.submit();
  }
  
function moveDown(idx) {
    document.huntingGroupCreate.action="timebandMoveDown.action";
    document.huntingGroupCreate.targetTimebandIdx.value=idx;
    document.huntingGroupCreate.submit();
  }

</script>
<!-- $Id: MICSHuntingGroup.jsp,v 1.1.4.3 2019/03/22 10:05:28 cvsuser Exp $ -->
<s:form action="huntingGroupCreate.action" method="post" namespace="/">
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
		<th colspan="5" align="center">Hunting Group</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="35" class="fontContent" readonly="true"/></td>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.huntingGroupName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="huntinggroupname" key="mics.form.huntingGroupName" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.publicNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="publicnumber" key="mics.form.publicNumber" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.privateNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="privatenumber" key="mics.form.privateNumber" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.welcomeAnno"/></td>
		<td class="textField">:</td>
		<td><s:select name="welcomeannoid" headerKey="0" headerValue="Select Announcement" list="annoList" listKey="annoid" listValue="%{annoid + ' - ' + announcementname}" label="Select Anno" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.memberNAAnno"/></td>
		<td class="textField">:</td>
		<td><s:select name="membernaannoid" headerKey="0" headerValue="Select Announcement" list="annoList" listKey="annoid" listValue="%{annoid + ' - ' + announcementname}" label="Select Anno" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.memberNRAnno"/></td>
		<td class="textField">:</td>
		<td><s:select name="membernrannoid" headerKey="0" headerValue="Select Announcement" list="annoList" listKey="annoid" listValue="%{annoid + ' - ' + announcementname}" label="Select Anno" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.huntingCliPrefix"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="huntingcliprefix" key="mics.form.huntingCliPrefix" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.huntingOption"/></td>
		<td class="textField">:</td>
		<td><s:select name="huntingoption" list="huntingoptionList" listKey="id" listValue="name" label="Select Hunting Option" width="264" style="width: 264px"/></td>
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
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.timeband"/></td>
		<td class="textField">:</td>
		<td><s:select name="timebandkey" list="timewindowList" listKey="id.timebandkey" listValue="id.timebandname" label="Select Timeband" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3"><s:submit action="huntingGroupAddTimeband" key="mics.form.add" align="left" onclick="clickAddButton()"/></td>
		<td>&nbsp;</td>
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
						<th class="sortable">Del</th>
						<th class="sortable">Timeband Name</th>
						<th class="sortable">Timeband Desc</th>
						<th class="sortable">Up</th>
						<th class="sortable">Down</th>
						<th class="sortable">Member</th>
					</tr>
				</thead>
			<tbody>
			<s:iterator value="huntingscheduleReg" status="stat" var="hsReq" >
				<tr>
			
					<s:hidden name="huntingscheduleReg[%{#stat.index}].timebandkey" value="%{#hsReq.timebandkey}" />
					<s:hidden name="huntingscheduleReg[%{#stat.index}].huntinggroupkey" value="%{#hsReq.huntinggroupkey}" />
					
					<td>
						<s:textfield name="huntingscheduleReg[%{#stat.index}].orderindex" key="mics.form.orderIndex" size="2" class="fontContent" readonly="true"/>
					</td>
					<td>
						<s:checkbox name="huntingscheduleReg[%{#stat.index}].remove" fieldValue="true" label="Remove"/>
					</td>
					<td>
						<s:textfield name="huntingscheduleReg[%{#stat.index}].timebandname" key="mics.form.timebandName" size="15" class="fontContent" readonly="true"/>
					</td>
					<td>
						<s:textfield name="huntingscheduleReg[%{#stat.index}].timebanddesc" key="mics.form.timebandDesc" size="25" class="fontContent" readonly="true"/>
					</td>
					<s:if test="#stat.first">
						<td>
							<!-- First can not move up -->
						</td>
					</s:if>
					<s:else>
						<td>
							<s:submit type="image" value="Up" src="images/up.png" onclick="moveUp('%{#stat.index}')"/>
						</td>
					</s:else>
					<s:if test="#stat.last">
						<td>
							<!-- Last can not move down -->
						</td>
					</s:if>
					<s:else>
						<td>
							<s:submit type="image" value="Down" src="images/down.png" onclick="moveDown('%{#stat.index}')"/>
						</td>
					</s:else>
					<td>
						<s:submit action="huntingGroupViewSchedule" key="mics.form.editScheduleMember" onclick="clickEditButton('%{#stat.index}')" align="center" />          
					</td>
				</tr>
    		</s:iterator>
    			<tr>
    				<td colspan="5"><s:submit action="huntingGroupDelTimeband" key="mics.form.delete" align="center" /></td>
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
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.submit" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


