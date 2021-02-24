<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<s:form action="companyInitAutoSwitchbrdListDeleting.action" method="post" namespace="/">
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
		<th colspan="5" align="center">Switchboard</th>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.switchboardName"/></td>
		<td class="textField">:</td>
		<td><s:text name="switchboard.switchboardname"/></td>
		<s:hidden name="switchboardkey"/>
		<s:hidden name="companykey"/>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.publicNumber"/></td>
		<td class="textField">:</td>
		<td><s:text name="switchboard.publicnumber"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.privateNumber"/></td>
		<td class="textField">:</td>
		<td><s:text name="switchboard.privatenumber"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:if test="deleteAllowed"><s:submit method="execute" key="mics.form.submit" align="center" value="Delete" /></s:if><s:submit key="mics.form.list" align="center" value="Back to List" action="companyInitAutoSwitchbrdList" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
</table>

		</td>
	</tr>
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>
