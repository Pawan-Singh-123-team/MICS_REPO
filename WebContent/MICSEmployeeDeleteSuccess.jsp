<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form action="employeeDelete.action" method="post" namespace="/">
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
		<th colspan="5" align="center">MICS Employee General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="30" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
		<s:hidden name="micsuserkey" />
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.firstName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="micsfirstname" key="mics.form.firstName" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.lastName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="micslastname" key="mics.form.lastName" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.memberType"/></td>
		<td class="textField">:</td>
		<td><s:select name="usertype" list="userTypes" listKey="id" listValue="name" label="Select Type" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.huntingOption"/></td>
		<td class="textField">:</td>
		<td><s:select name="huntingoption" list="huntingoptionList" listKey="id" listValue="name" label="Select Hunting Option" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


