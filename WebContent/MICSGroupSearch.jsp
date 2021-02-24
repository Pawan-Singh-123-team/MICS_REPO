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
<s:form action="groupSearch.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Group Search</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="groupname" key="mics.form.groupName" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
		<s:hidden name="searchCnt"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupId"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="groupid" key="mics.form.companyKey" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.groupCli"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="groupcli" key="mics.form.groupCli" size="25" class="fontContent"/></td>
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
			<display:table id="groups" name="groups" pagesize="10" export="false" requestURI="/groupSearch.action">
				<s:if test="editAllowed">
					<display:column property="micsgroupkey" title="Group Key" paramId="micsgroupkey" sortable="true" href="groupInitEdit.action" />
				</s:if>
				<s:else>
					<display:column property="micsgroupkey" title="Group Key" paramId="micsgroupkey" sortable="true" href="groupInitView.action" />
				</s:else>
				<display:column property="groupid" title="Group Id" sortable="true" />
				<display:column property="groupname" title="Group Name" sortable="true" />
				<display:column property="groupcli" title="Group CLI" sortable="true"/>
				<display:column property="updatetimestamp" title="Last Update" sortable="true" />
				<s:if test="deleteAllowed">
				<display:column media="html" title="Delete" style="text-align:left">
               		<s:url id="delUrl" action="groupInitDelete.action">
                    	<s:param name="micsgroupkey" value="#attr.groups.micsgroupkey" />
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


