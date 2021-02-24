<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<s:form action="employeeSearch.action" method="post" namespace="/">
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
			<th colspan="5" align="center">MICS Employee Search</th>
		</tr>
		<s:if test="tselAdmin">
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.companyKey"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="companykey" key="mics.form.companyKey" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<s:hidden name="searchCnt"/>
		</s:if>
		<s:else>
		<s:hidden name="companykey"/>
		<s:hidden name="searchCnt"/>
		</s:else>
		
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.firstName"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="micsfirstname" key="mics.form.firstName" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.lastName"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="micslastname" key="mics.form.lastName" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.publicNumber"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="mainpublicnumber" key="mics.form.publicNumber" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.privateNumber"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="mainprivatenumber" key="mics.form.privateNumber" size="25" class="fontContent"/></td>
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
			<display:table id="employeeList" name="employeeList" pagesize="10" export="false" requestURI="/employeeSearch.action">
				<s:if test="editAllowed">
					<display:column property="micsUserKey" title="Employee Key" paramId="micsuserkey" sortable="true" href="employeeInitEdit.action" />
				</s:if>
				<s:else>
					<display:column property="micsUserKey" title="Employee Key" paramId="micsuserkey" sortable="true" href="employeeInitView.action" />
				</s:else>
				<display:column property="micsCompanyKey" title="Company Key" sortable="true" />
				<display:column property="micsFirstName" title="First Name" sortable="true"/>
				<display:column property="micsLastName" title="Last Name" sortable="true" />
				<display:column property="micsPubNumber" title="Public Number" sortable="true" />
				<display:column property="micsPrivNumber" title="Private Number" sortable="true" />
				<s:if test="deleteAllowed">
				<display:column media="html" title="Delete" style="text-align:left">
               		<s:url id="delUrl" action="employeeInitDelete.action">
                    	<s:param name="micsuserkey" value="#attr.employeeList.micsUserKey" />
                	</s:url>
                	<s:a href="%{delUrl}">Del</s:a>
         		</display:column>
         		</s:if>
         		<display:column media="html" title="SIP" style="text-align:left">
               		<s:url id="changeSIPPassword" action="employeeInitSipPassword.action">
                    	<s:param name="micsuserkey" value="#attr.employeeList.micsUserKey" />
                	</s:url>
                	<s:a href="%{changeSIPPassword}">Change Password</s:a>
         		</display:column>
				<display:setProperty name="paging.banner.placement" value="bottom" />
			</display:table>
		</td>
	</tr>
</table>



</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


