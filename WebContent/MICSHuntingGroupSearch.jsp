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
<s:form action="huntingGroupSearch.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Hunting Group Search</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.huntingGroupName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="huntinggroupname" key="mics.form.huntingGroupName" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
		<s:hidden name="searchCnt"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.publicNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="publicnumber" key="mics.form.publicNumber" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.privateNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="privatenumber" key="mics.form.privateNumber" size="35" class="fontContent"/></td>
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
			<display:table id="huntinggroups" name="huntinggroups" pagesize="10" export="false" requestURI="/huntingGroupSearch.action">
				<s:if test="editAllowed">
					<display:column property="huntinggroupkey" title="Hunting Group Key" paramId="huntinggroupkey" sortable="true" href="huntingGroupInitEdit.action" />
				</s:if>
				<s:else>
					<display:column property="huntinggroupkey" title="Hunting Group Key" paramId="huntinggroupkey" sortable="true" href="huntingGroupInitView.action" />
				</s:else>
				<display:column property="huntinggroupname" title="Hunting Group Name" sortable="true" />
				<display:column property="publicnumber" title="Public Number" sortable="true"/>
				<display:column property="privatenumber" title="Private Number" sortable="true" />
				<s:if test="deleteAllowed">
				<display:column media="html" title="Delete" style="text-align:left">
               		<s:url id="delUrl" action="huntingGroupInitDelete.action">
                    	<s:param name="huntinggroupkey" value="#attr.huntinggroups.huntinggroupkey" />
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


