<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

function onCompanyChange() {

document.forms[0].action = 'companyAnnoCPYChange.action';

document.forms[0].submit();

}

</script>
<s:form action="companyAnnoCreate.action" method="post" namespace="/">
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
		<th colspan="5" align="center">Announcements</th>
	</tr>
	
		<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="30" class="fontContent" readonly="true" /></td>
		<td>&nbsp;</td>
	</tr>
	
	<!--  Iterate Announcement -->
	<s:if test="annoList != null && !annoList.isEmpty">
	<tr>
		<td>&nbsp;</td>
		<td colspan="3">
			<table>
				<thead>
					<tr>
						<th class="sortable">Anno ID</th>
						<th class="sortable">Name</th>
						<th class="sortable">Desc</th>
					</tr>
				</thead>
			<tbody>

			<s:iterator value="annoList" status="stat" var="annoReq" >
				<tr>
			
					<s:hidden name="annoList[%{#stat.index}].announcementkey" value="%{#annoReq.announcementkey}" />
					<s:hidden name="annoList[%{#stat.index}].companykey" value="%{#annoReq.companykey}" />
					<s:hidden name="annoList[%{#stat.index}].updateuser" value="%{#annoReq.updateuser}" />
					<s:hidden name="annoList[%{#stat.index}].srfelementid" value="%{#annoReq.srfelementid}" />
					<td valign="top">
						<s:textfield name="annoList[%{#stat.index}].annoid"  key="mics.form.announcementID" size="5" class="fontContent" readonly="true"/>
					</td >
					<td valign="top">
						<s:textfield name="annoList[%{#stat.index}].announcementname"  key="mics.form.announcementName" size="25" class="fontContent"/>
					</td>
					<td valign="top">
						<s:textarea name="annoList[%{#stat.index}].announcementdesc"  key="mics.form.announcementDesc" rows="2" cols="40" class="fontContent"/>
					</td>
					
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
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.submit" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


