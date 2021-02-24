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
<s:form action="userSearch.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS User General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.username"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="userName" key="mics.form.username" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
		<s:hidden name="searchCnt"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companyName" key="mics.form.companyKey" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.nik"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="userNik" key="mics.form.nik" size="35" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.role"/></td>
		<td class="textField">:</td>
		<td><s:select name="userRole" list="role" headerKey="" headerValue="All Roles" listKey="rolekey" listValue="rolename" label="Select Role" width="264" style="width: 264px"/></td>
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
			<display:table id="webuser" name="webuser" pagesize="10" export="false" requestURI="/userSearch.action">
				<display:column property="webuserkey" title="User Key" paramId="webUserKey" sortable="true" href="userEdit.action" />
				<display:column property="webusername" title="User Name" sortable="true" />
				<display:column property="companykey" title="Company" sortable="true"/>
				<display:column property="ssotoken" title="NIK" sortable="true" />
				<display:column media="html" title="Role" sortable="true">
					<s:text name="mics.label.role.%{#attr.webuser.rolekey}"/>
				</display:column>
				<display:column property="email" title="Email" sortable="true"/>
				<display:column property="msisdn" title="MSISDN" sortable="true"/>
				<%/*
				<display:column property="updatetimestamp" title="Last Update" sortable="true" />
				*/%>
				<display:column media="html" title="Set Password" style="text-align:left">
               		<s:url id="setPwdUrl" action="userSetPwd.action">
                    	<s:param name="webUserKey" value="#attr.webuser.webuserkey" />
                	</s:url>
                	<s:a href="%{setPwdUrl}">Set Password</s:a>
         		</display:column>
				<display:column media="html" title="Delete" style="text-align:left">
               		<s:url id="delUrl" action="userDel.action">
                    	<s:param name="webUserKey" value="#attr.webuser.webuserkey" />
                	</s:url>
                	<s:a href="%{delUrl}">Del</s:a>
         		</display:column>
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


