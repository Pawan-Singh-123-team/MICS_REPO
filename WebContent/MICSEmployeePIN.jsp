<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form action="employeeSipPassword.action" method="post" namespace="/" validate="true" >
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
		<th colspan="5" align="center">MICS Employee SIP Password Change</th>
	</tr>
	<s:hidden name="micsuserkey" />
	<s:hidden name="companykey" />
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.firstName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="micsfirstname" key="mics.form.firstName" size="35" class="fontContent" readonly="true" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.lastName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="micslastname" key="mics.form.lastName" size="35" class="fontContent" readonly="true"/></td>
		<td>&nbsp;</td>
	</tr> 
	<tr>
		<th colspan="5" align="center">MICS Employee SIP Password</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.sipPassword"/>*</td>
		<td class="textField">:</td>
		<td><s:password name="sipPassword" key="mics.form.sipPassword" size="35" class="fontContent"/><s:text name="mics.form.sipPassword8"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.sipPasswordConf"/>*</td>
		<td class="textField">:</td>
		<td><s:password name="sipPasswordConf" key="mics.form.sipPasswordConf" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.edit" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


