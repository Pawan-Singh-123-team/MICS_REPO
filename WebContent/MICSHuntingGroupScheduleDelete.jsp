<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- $Id: MICSHuntingGroupScheduleDelete.jsp,v 1.1.4.3 2019/03/22 10:05:32 cvsuser Exp $ -->
<s:form action="huntingGroupScheduleDelete.action" method="post" namespace="/">
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
		<th colspan="5" align="center">MICS Hunting Group Delete Schedule General Data</th>
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
		<td><s:textfield name="huntingScheduleName" key="mics.form.mics.form.timeband" size="30" class="fontContent" readonly="true"/></td>
		<td>&nbsp;</td>
	</tr>
		
		<s:hidden name="huntinggroupkey" />
		<s:hidden name="huntingScheduleIdx" />  
		<s:hidden name="publicnumber" />
		<s:hidden name="privatenumber" />
		<s:hidden name="welcomeannoid" />
		<s:hidden name="membernaannoid" />
		<s:hidden name="membernrannoid" />
		<s:hidden name="huntingcliprefix" />
		<s:hidden name="huntingoption" />
		<s:hidden name="boolReverseCharging" />
	
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
					</tr>
				</thead>
			<tbody>
			<s:iterator value="memberReq" status="stat" var="iterMemberReq" >
				<tr>
			
					<s:hidden name="memberReq[%{#stat.index}].micsCompanyKey" value="%{#iterMemberReq.micsCompanyKey}" />
					<s:hidden name="memberReq[%{#stat.index}].micsFirstName" value="%{#iterMemberReq.micsFirstName}" />
					<s:hidden name="memberReq[%{#stat.index}].micsLastName" value="%{#iterMemberReq.micsLastName}" />
					<s:hidden name="memberReq[%{#stat.index}].micsUserKey" value="%{#iterMemberReq.micsUserKey}" />
					<s:hidden name="memberReq[%{#stat.index}].micsPubNumber" value="%{#iterMemberReq.micsPubNumber}" />
					<s:hidden name="memberReq[%{#stat.index}].micsPrivNumber" value="%{#iterMemberReq.micsPrivNumber}" />
					<td>
						<s:textfield name="memberReq[%{#stat.index}].orderindex" key="mics.form.orderIndex" size="2" class="fontContent" readonly="true"/>
					</td>
					<td>
						<s:checkbox name="memberReq[%{#stat.index}].remove" fieldValue="true" label="Remove"/>
					</td>
					<td>
						<s:property value="%{#iterMemberReq.micsPubNumber}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberReq.micsFirstName}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberReq.micsLastName}"/>
					</td>
				</tr>
    		</s:iterator>
    			
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


