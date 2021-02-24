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
<s:form action="timebandSearch.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Timeband Search</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.timebandName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="timebandName" key="mics.form.timebandName" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
		<s:hidden name="searchCnt"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.timebandDesc"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="timebandDescription" key="mics.form.timebandDesc" size="25" class="fontContent"/></td>
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
			<display:table id="timebands" name="timebands" pagesize="10" export="false" requestURI="/timebandSearch.action">
				<display:column property="id.timebandkey" title="Timeband Key" paramId="timebandKey" sortable="true" href="timebandInitEdit.action" />
				<display:column property="id.timebandname" title="Timeband Name" sortable="true" />
				<display:column property="timebanddescription" title="Timeband Desc" sortable="true"/>
				<display:column property="updatetimestamp" title="Last Update" sortable="true" />
				<s:if test="deleteAllowed">
				<display:column media="html" title="Delete" style="text-align:left">
               		<s:url id="delUrl" action="timebandInitDelete.action">
                    	<s:param name="timebandKey" value="#attr.timebands.id.timebandkey" />
                	</s:url>
                	<s:a href="%{delUrl}">Del</s:a>
         		</display:column>
         		</s:if>
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


