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
<s:form action="userEditSubmit.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS User General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.username"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="userName" key="mics.form.username" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
		<s:hidden name="webUserKey"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.nik"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="userNik" key="mics.form.nik" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.email"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="userEmail" key="mics.form.email" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.msisdn"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="userMsisdn" key="mics.form.msisdn" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.role"/></td>
		<td class="textField">:</td>
		<td><s:select name="roleList" list="role" listKey="rolekey" listValue="rolename" label="Select Role" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.company"/></td>
		<td class="textField">:</td>
		<td><s:select name="companyList" list="company" headerKey="1" headerValue="Select Company" listKey="companykey" listValue="companyname" label="Select Company" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.submit" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>
</s:form>
</td>
<td>&nbsp;</td>
</tr>
</table>


