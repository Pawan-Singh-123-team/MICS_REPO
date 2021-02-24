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
	<table class="formOutlineOrange">
		<tr>
			<th colspan="5" align="center">Company Switchboards</th>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<display:table id="switchboardsTable" name="switchboards" pagesize="20" export="false" requestURI="/companyInitAutoSwitchbrdList.action">
					<!-- TODO: permissions for add, edit, delete -->
					<display:column property="switchboardkey" title="Switchboard Key" paramId="switchboardkey" sortable="true" href="companyInitAutoSwitchbrdListEdit.action" />
					<display:column property="switchboardname" title="Switchboard Name" sortable="true" />
					<display:column property="publicnumber" title="Public Number" sortable="true"/>
					<display:column property="privatenumber" title="Private Number" sortable="true" />
					<display:column media="html" title="Delete" style="text-align:left">
	               		<s:url id="delUrl" action="companyInitAutoSwitchbrdListDelete.action">
	                    	<s:param name="switchboardkey" value="#attr.switchboardsTable.switchboardkey" />
	                	</s:url>
	                	<s:a href="%{delUrl}">Del</s:a>
	         		</display:column>
					<display:setProperty name="paging.banner.placement" value="bottom" />
				</display:table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr><td>
			<s:form action="companyInitAutoSwitchbrdListEdit.action" method="get" namespace="/">
				<s:submit value="Add New" />
			</s:form>
		</td></tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</td>
</tr>
</table>