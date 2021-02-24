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
		<th colspan="5" align="center">MICS Profile Search</th>
	</tr>

	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="callscreeningname" key="mics.form.callScreeningName" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
		<s:hidden name="searchCnt"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.type"/></td>
		<td class="textField">:</td>
		<td><s:select name="searchScreeningtype" headerKey="0" headerValue="All Types" list="screeningtypes" listKey="id" listValue="name" label="Select Type" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.level"/></td>
		<td class="textField">:</td>
		<td><s:select name="screeninglevel" headerKey="0" headerValue="All Levels" list='screeninglevels' listKey="id" listValue="name" label="Select Level" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.search" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">
			<!--  Display Result Here -->
			<display:table id="callscreenings" name="callscreenings" pagesize="10" export="false" requestURI="/profileSearch.action">
				<display:column property="callscreeningkey" title="Profile Key" paramId="callscreeningkey" sortable="true" href="profileInitEdit.action" />
				<display:column property="callscreeningname" title="Profile Name" sortable="true" />
				<display:column media="html" title="Type" sortable="true">
					<s:text name="mics.label.screeningType.%{#attr.callscreenings.screeningtype}"/>
				</display:column>
				<display:column media="html" title="Level" sortable="true">
					<s:text name="mics.label.screeningLevel.%{#attr.callscreenings.screeninglevel}"/>
				</display:column>
				<s:if test="deleteAllowed">
				<display:column media="html" title="Delete" style="text-align:left">
               		<s:url id="delUrl" action="profileInitDelete.action">
                    	<s:param name="callscreeningkey" value="#attr.callscreenings.callscreeningkey" />
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


