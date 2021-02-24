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
<s:form action="partnershipSearch.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Partnership Search</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.partnershipName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="partnershipname" key="mics.form.partnershipName" size="25" class="fontContent"/></td>
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
			<display:table id="partnershipList" name="partnershipList" pagesize="10" export="false" requestURI="/partnershipSearch.action">
				<s:if test="editAllowed">
					<display:column property="partnershipkey" title="Partnership Key" paramId="partnershipkey" sortable="true" href="partnershipInitEdit.action" />
				</s:if>
				<s:else>
					<display:column property="partnershipkey" title="Partnership Key" paramId="partnershipkey" sortable="true" href="partnershipInitView.action" />
				</s:else>
				<display:column property="partnershipname" title="Partnership Name" sortable="true" />
				<display:column property="companykey1" title="Company 1" sortable="true"/>
				<display:column property="companykey2" title="Company 2" sortable="true"/>
				<display:column property="updatetimestamp" title="Last Update" sortable="true" />
				<s:if test="deleteAllowed">
				<display:column media="html" title="Delete" style="text-align:left">
               		<s:url id="delUrl" action="partnershipInitDelete.action">
                    	<s:param name="partnershipkey" value="#attr.partnershipList.partnershipkey" />
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


