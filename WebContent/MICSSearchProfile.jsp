<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
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
<s:form action="profileSearch.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS User General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.username"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="profileKey" key="mics.form.callScreeningKey" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companyKey" key="mics.form.companyKey" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.submit" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">
			<!--  Display Result Here -->
			<display:table id="profiles" name="profiles" pagesize="5" export="true" requestURI="/profileSearch.action">
				<display:column property="callscreeningkey" title="Profile Key" paramId="callscreeningkey" sortable="true" href="profileEdit.action" />
				<display:column property="companykey" title="Company" sortable="true" />
				<display:column property="screeningtype" title="Type" sortable="true"/>
				<display:column property="screeninglevel" title="Level" sortable="true" />
				<display:column property="updateuser" title="Update" sortable="true" />
				<display:column property="updatetimestamp" title="Last Update" sortable="true" />
				<display:setProperty name="paging.banner.placement" value="bottom" />
			</display:table>
		</td>
	</tr>
</table>
</s:form>
</td>
<td>&nbsp;</td>
</tr>
</table>


