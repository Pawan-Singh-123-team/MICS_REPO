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
<s:form action="downloadTimeband.action" method="post" namespace="/" validate="true">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Timeband List Download</th>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
		
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
		
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField" align="center"><s:submit method="execute" key="mics.form.download" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
		
	</tr>
</table>
</s:form>
</td>
<td>&nbsp;</td>
</tr>
</table>


