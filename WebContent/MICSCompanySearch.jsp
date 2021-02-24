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
<s:form action="companySearch.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Company Search</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
		<s:hidden name="searchCnt"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companyname" key="mics.form.companyName" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.chargeString"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="chargestring" key="mics.form.chargeString" size="25" class="fontContent"/></td>
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
			<display:table id="companies" name="companies" pagesize="10" export="false" requestURI="/companySearch.action">
				<s:if test="editAllowed">
					<display:column property="companykey" title="Company Key" paramId="companykey" sortable="true" href="companyInitEdit.action" />
				</s:if>
				<s:else>
					<display:column property="companykey" title="Company Key" paramId="companykey" sortable="true" href="companyInitView.action" />
				</s:else>
				<display:column property="companyname" title="Company Name" sortable="true" />
				<display:column property="chargestring" title="Charge String" sortable="true"/>
				<display:column property="updatetimestamp" title="Last Update" sortable="true" />
				<s:if test="deleteAllowed">
				<display:column media="html" title="Delete" style="text-align:left">
               		<s:url id="delUrl" action="companyInitDelete.action">
                    	<s:param name="companykey" value="#attr.companies.companykey" />
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


