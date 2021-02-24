<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- $Id: MICSHuntingGroupSchedule.jsp,v 1.1.4.3 2019/03/22 10:05:31 cvsuser Exp $ -->
<script type="text/javascript">

 
function moveUp(idx) {
    document.huntingGroupScheduleCreate.action="timebandMemberMoveUp.action";
    document.huntingGroupScheduleCreate.targetMemberIdx.value=idx;
    document.huntingGroupScheduleCreate.submit();
  }
  
function moveDown(idx) {
    document.huntingGroupScheduleCreate.action="timebandMemberMoveDown.action";
    document.huntingGroupScheduleCreate.targetMemberIdx.value=idx;
    document.huntingGroupScheduleCreate.submit();
  }

</script>
<s:form action="huntingGroupScheduleCreate.action" method="post" namespace="/">
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
		<th colspan="5" align="center">MICS Hunting Group Schedule General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="30" class="fontContent" readonly="true" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.huntingGroupName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="huntinggroupname" key="mics.form.huntingGroupName" size="30" class="fontContent" readonly="true" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.timeband"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="huntingScheduleName" key="mics.form.timeband" size="30" class="fontContent" readonly="true"/></td>
		<td>&nbsp;</td>
	</tr>
		<s:hidden name="targetMemberIdx"/>
		<s:hidden name="targetTimebandIdx"/>
		<s:hidden name="maxTimeband"/>
		<s:hidden name="huntingScheduleIdx" />  
		<s:hidden name="publicnumber"/>
		<s:hidden name="privatenumber"/>
		<s:hidden name="welcomeannoid"/>
		<s:hidden name="membernaannoid"/>
		<s:hidden name="membernrannoid"/>
		<s:hidden name="huntingcliprefix"/>
		<s:hidden name="huntingoption"/>
		<s:hidden name="boolReverseCharging"/>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit action="huntingGroupScheduleInitSearchMember" key="mics.form.add" align="left" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<!--  Iterate User Number -->
	<s:if test="memberReq != null && !memberReq.isEmpty">
	<tr>
		<td>&nbsp;</td>
		<td colspan="3">
			<table>
				<thead>
					<tr>
						<th class="sortable">No</th>
						<th class="sortable">Del</th>
						<th class="sortable">Public Number</th>
						<th class="sortable">First Name</th>
						<th class="sortable">Last Name</th>
						<th class="sortable">Up</th>
						<th class="sortable">Down</th>
						
					</tr>
				</thead>
			<tbody>
			<s:iterator value="memberReq" status="stat" var="iterMemberReq" >
				<tr>
			
					<s:hidden name="memberReq[%{#stat.index}].micsCompanyKey" value="%{#iterMemberReq.micsCompanyKey}" />
					<s:hidden name="memberReq[%{#stat.index}].micsFirstName" value="%{#iterMemberReq.micsFirstName}" />
					<s:hidden name="memberReq[%{#stat.index}].micsLastName" value="%{#iterMemberReq.micsLastName}" />
					<s:hidden name="memberReq[%{#stat.index}].micsUserKey" value="%{#iterMemberReq.micsUserKey}" />
					<s:hidden name="memberReq[%{#stat.index}].micsPrivNumber" value="%{#iterMemberReq.micsPrivNumber}" />
					<td>
						<s:textfield name="memberReq[%{#stat.index}].orderindex" key="mics.form.orderIndex" size="2" class="fontContent" readonly="true"/>
					</td>
					<td>
						<s:checkbox name="memberReq[%{#stat.index}].remove" fieldValue="true" label="Remove"/>
					</td>
					<td>
						<s:textfield name="memberReq[%{#stat.index}].micsPubNumber" key="mics.form.publicNumber" size="15" class="fontContent" readonly="true"/>
					</td>
					<td>
						<s:property value="%{#iterMemberReq.micsFirstName}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberReq.micsLastName}"/>
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
				</tr>
    		</s:iterator>
    			<tr>
    				<td colspan="5"><s:submit action="huntingGroupScheduleDelMember" key="mics.form.delete" align="center" /></td>
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
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.continue" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


