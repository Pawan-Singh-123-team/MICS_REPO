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
<s:form action="companyDelete.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Company General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companyname" key="mics.form.companyName" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<s:if test="tselAdmin">
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.chargeString"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="chargestring" key="mics.form.chargeString" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	</s:if>
	<s:else>
		<s:hidden name="chargestring" />
	</s:else>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.contactPerson"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="contactperson" key="mics.form.contactPerson" size="35" class="fontContent" disabled="true" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.email"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="email" key="mics.form.email" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.address"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="address" key="mics.form.address" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.domainname"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="domainname" key="mics.form.domainname" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textWarn">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	
</table>
</s:form>
</td>
<td>&nbsp;</td>
</tr>
</table>


