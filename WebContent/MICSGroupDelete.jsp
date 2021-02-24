<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<s:form action="groupDelete.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Group General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.company"/></td>
		<td class="textField">:</td>
		<td><s:property value="companykey"/>&nbsp;<s:property value="companyname" /></td>
		<td>&nbsp;</td>
		<s:hidden name="companykey" />
		<s:hidden name="companyname" />
		<s:hidden name="micsgroupkey" />
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="groupname" key="mics.form.groupName" size="35" class="fontContent" readonly="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupId"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="groupid" key="mics.form.groupId" size="35" class="fontContent" readonly="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupCli"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="groupcli" key="mics.form.groupCli" size="35" class="fontContent" readonly="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th colspan="5" align="center">MICS Group Outgoing Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="outcs" list='outgoingProfiles' headerKey="-1" headerValue="Select Outgoing Profile" listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" readonly="true" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<th colspan="5" align="center">MICS Group Incoming Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="incs" list='incomingProfiles' headerKey="" headerValue="Select Incoming Profile" listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" readonly="true" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textWarn">Are you really want to delete this group ? this process can not be undo</td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.delete" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>
</s:form>
</td>
<td>&nbsp;</td>
</tr>
</table>


