<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
		<th colspan="5" align="center">MICS Community General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.communityName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="communityname" key="mics.form.communityName" size="30" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.contactPerson"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="contactperson" key="mics.form.contactPerson" size="30" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.address"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="address" key="mics.form.address" size="50" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.email"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="email" key="mics.form.email" size="30" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<!--  Iterate User Number -->
	<s:if test="memberReq != null && !memberReq.isEmpty">
	<tr>
		<td>&nbsp;</td>
		<td colspan="3">
			<table>
				<thead>
					<tr>
						<th class="sortable">No</th>
						<th class="sortable">Company</th>
						<th class="sortable">First Name</th>
						<th class="sortable">Last Name</th>
					</tr>
				</thead>
			<tbody>
			<s:iterator value="memberReq" status="stat" var="iterMemberReq" >
				<tr>
			
					<s:hidden name="memberReq[%{#stat.index}].micsCompanyKey" value="%{#iterMemberReq.micsCompanyKey}" />
					<s:hidden name="memberReq[%{#stat.index}].micsFirstName" value="%{#iterMemberReq.micsFirstName}" />
					<s:hidden name="memberReq[%{#stat.index}].micsLastName" value="%{#iterMemberReq.micsLastName}" />
					<s:hidden name="memberReq[%{#stat.index}].micsUserKey" value="%{#iterMemberReq.micsUserKey}" />
					<s:hidden name="memberReq[%{#stat.index}].micsPubNumber" value="%{#iterMemberReq.micsPubNumber}" />
					<s:hidden name="memberReq[%{#stat.index}].micsPrivNumber" value="%{#iterMemberReq.micsPrivNumber}" />
					<td>
						<s:property value="%{#stat.count}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberReq.micsCompanyKey}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberReq.micsFirstName}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberReq.micsLastName}"/>
					</td>
				</tr>
    		</s:iterator>
    		</tbody>
    		</table>
    		
    	</td>
		<td>&nbsp;</td>
	</tr>
	</s:if>
	<!--  End Iterator -->
	
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>



