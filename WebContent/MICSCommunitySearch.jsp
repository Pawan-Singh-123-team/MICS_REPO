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
<s:form action="communitySearch.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Community Search</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.communityName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="communityname" key="mics.form.communityName" size="30" class="fontContent"/></td>
		<td>&nbsp;</td>
		<s:hidden name="searchCnt"/>
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
			<display:table id="communities" name="communities" pagesize="10" export="false" requestURI="/communitySearch.action">
				<s:if test="editAllowed">
					<display:column property="communitykey" title="Community Key" paramId="communitykey" sortable="true" href="communityInitEdit.action" />
				</s:if>
				<s:else>
					<display:column property="communitykey" title="Community Key" paramId="communitykey" sortable="true" href="communityInitView.action" />
				</s:else>
				<display:column property="communityname" title="Community Name" sortable="true" />
				<display:column property="contactperson" title="Contact Person" sortable="true"/>
				<display:column property="email" title="email" sortable="true" />
				<s:if test="deleteAllowed">
				<display:column media="html" title="Delete" style="text-align:left">
               		<s:url id="delUrl" action="communityInitDelete.action">
                    	<s:param name="communitykey" value="#attr.communities.communitykey" />
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


