<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

function onCompanyChange() {

document.forms[0].action = 'huntingGroupCPYChange.action';

document.forms[0].submit();

}

</script>
<!-- $Id: MICSHuntingGroupDelete.jsp,v 1.1.4.3 2019/03/22 10:05:29 cvsuser Exp $ -->
<s:form action="huntingGroupDelete.action" method="post" namespace="/">
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
		<th colspan="5" align="center">Hunting Group Delete</th>
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
		<td><s:select name="welcomeannoid" list="annoList" listKey="annoid" listValue="%{annoid + ' - ' + announcementname}" label="Select Anno" disabled="true" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.memberNAAnno"/></td>
		<td class="textField">:</td>
		<td><s:select name="membernaannoid" list="annoList" listKey="annoid" listValue="%{annoid + ' - ' + announcementname}" label="Select Anno" disabled="true" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.memberNRAnno"/></td>
		<td class="textField">:</td>
		<td><s:select name="membernrannoid" list="annoList" listKey="annoid" listValue="%{annoid + ' - ' + announcementname}" label="Select Anno" disabled="true" width="264" style="width: 264px"/></td>
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
		<td><s:select name="huntingoption" list="huntingoptionList" listKey="id" listValue="name" label="Select Hunting Option" disabled="true" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.reverseCharging"/></td>
		<td class="textField">:</td>
		<td><s:checkbox name="boolReverseCharging" fieldValue="true" label="mics.form.reverseCharging"/></td>
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
						<th class="sortable">Member</th>
					</tr>
				</thead>
			<tbody>
			<s:iterator value="huntingscheduleReg" status="stat" var="hsReq" >
				<tr>
			
					<s:hidden name="huntingscheduleReg[%{#stat.index}].timebandkey" value="%{#hsReq.timebandkey}" />
					<s:hidden name="huntingscheduleReg[%{#stat.index}].timebandname" value="%{#hsReq.timebandname}" />
					<s:hidden name="huntingscheduleReg[%{#stat.index}].timebanddesc" value="%{#hsReq.timebanddesc}" />
					<s:hidden name="huntingscheduleReg[%{#stat.index}].huntinggroupkey" value="%{#hsReq.huntinggroupkey}" />
					<s:hidden name="huntingscheduleReg[%{#stat.index}].orderindex" value="%{#hsReq.orderindex}" />
					
					<td>
						<s:property value="%{#stat.count}"/>
					</td>
					<td>
						<s:checkbox name="huntingscheduleReg[%{#stat.index}].remove" fieldValue="true" label="Remove"/>
					</td>
					<td>
						<s:property value="%{#hsReq.timebandname}"/>
					</td>
					<td>
						<s:property value="%{#hsReq.timebanddesc}"/>
					</td>
					<td>
						 <s:url id="aLink" action="huntingGroupViewScheduleDelete">  
          					<s:param name="huntingScheduleIdx" value="%{#stat.index}" />  
          					<s:param name="huntingScheduleName" value="%{#hsReq.timebandname}" /> 
          					<s:param name="companykey" value="%{companykey}" /> 
          					<s:param name="huntinggroupkey" value="%{huntinggroupkey}" />
          					<s:param name="huntinggroupname" value="%{huntinggroupname}" />
          					<s:param name="publicnumber" value="%{publicnumber}" />
          					<s:param name="privatenumber" value="%{privatenumber}" />
          					<s:param name="welcomeannoid" value="%{welcomeannoid}" />
          					<s:param name="membernaannoid" value="%{membernaannoid}" />
          					<s:param name="membernrannoid" value="%{membernrannoid}" />
          					<s:param name="huntingcliprefix" value="%{huntingcliprefix}" />
          					<s:param name="huntingoption" value="%{huntingoption}" />
          					<s:param name="boolReverseCharging" value="%{boolReverseCharging}" />
       					</s:url>  
						<!--<s:submit action="%{aLink}"  key="mics.form.editScheduleMember"/>-->
						<s:a href="%{aLink}"><s:text name="mics.form.viewScheduleMember"/></s:a>              
						
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
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.delete" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


