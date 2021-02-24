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
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="groupname" key="mics.form.groupName" size="30" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupId"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="groupid" key="mics.form.groupId" size="30" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupCli"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="groupcli" key="mics.form.groupCli" size="30" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th colspan="5" align="center">MICS Group Outgoing Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="outcs" list='outgoingProfiles' headerKey="" headerValue="Select Outgoing Profile" listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" disabled="true"/>&nbsp;<!--<s:checkbox name="boolBypassOutCS" fieldValue="true" label="mics.form.forcedOnNet" disabled="true"/><s:text name="mics.form.bypass"/>--></td>
		<td>&nbsp;</td>
	</tr>
	<!--
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningDesc"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="outcsdesc" key="mics.form.callScreeningDesc" size="25" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	-->
	<tr>
		<th colspan="5" align="center">MICS Group Incoming Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="incs" list='incomingProfiles' headerKey="" headerValue="Select Incoming Profile" listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" disabled="true"/>&nbsp;<!--<s:checkbox name="boolBypassInCS" fieldValue="true" label="mics.form.forcedOnNet" disabled="true"/><s:text name="mics.form.bypass"/>--></td>
		<td>&nbsp;</td>
	</tr>
	<!--
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningDesc"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="incsdesc" key="mics.form.callScreeningDesc" size="25" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	-->
</table>
</td>
<td>&nbsp;</td>
</tr>
</table>


