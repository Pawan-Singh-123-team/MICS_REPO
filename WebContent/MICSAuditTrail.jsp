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
<s:form action="auditTrailView.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Audit Trail</th>
	</tr>
	
	
	
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.refresh" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">
			<!--  Display Result Here -->
			<display:table id="logList" name="logList" pagesize="10" export="false" requestURI="/auditTrailView.action">
				<display:column property="id" title="ID" sortable="true"/>
				<display:column property="module" title="Module" sortable="true"/>
				<display:column property="activity" title="Activity" sortable="true"/>
				<display:column property="mics_desc" title="Desc" sortable="true"/>
				<display:column property="status" title="Status" sortable="true"/>
				<display:column property="mics_user" title="User" sortable="true"/>
				<display:column property="timestamp" title="Timestamp" sortable="true"/>
				<display:setProperty name="paging.banner.placement" value="bottom"/>
			</display:table>
		</td>
	</tr>
</table>
</s:form>
</td>
<td>&nbsp;</td>
</tr>
</table>


