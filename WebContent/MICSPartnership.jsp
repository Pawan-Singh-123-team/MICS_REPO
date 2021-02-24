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
<s:form action="partnershipCreate.action" method="post" namespace="/" validate="true">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Partnership General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.partnershipName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="partnershipname" key="mics.form.partnershipName" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.company"/>1</td>
		<td class="textField">:</td>
		<td><s:select name="companykey1" list='companyList' listKey="companykey" listValue="%{companykey + ' - ' + companyname}" label="Select Company" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.company"/>2</td>
		<td class="textField">:</td>
		<td><s:select name="companykey2" list='companyList' listKey="companykey" listValue="%{companykey + ' - ' + companyname}" label="Select Company" width="264" style="width: 264px"/></td>
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


