<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<s:form action="communitySearchMemberEdit.action" method="post" namespace="/">
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
			<th colspan="5" align="center">MICS Community Search Member</th>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.firstName"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="searchFirstName" key="mics.form.firstName" size="25" class="fontContent"/></td>
		<s:hidden name="communitykey" />
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.lastName"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="searchLastName" key="mics.form.lastName" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.publicNumber"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="searchPubNumber" key="mics.form.publicNumber" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="textField"><s:text name="mics.form.privateNumber"/></td>
			<td class="textField">:</td>
			<td><s:textfield name="searchPrivNumber" key="mics.form.privateNumber" size="25" class="fontContent"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="3" class="textField"><s:submit method="communitySearchMemberEdit" key="mics.form.search" align="center" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="5">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="5">
			<!--  Iterate User Number -->
			<s:if test="searchMemberRes != null && !searchMemberRes.isEmpty">
			<table>
				<thead>
					<tr>
						<th class="sortable">No</th>
						<th class="sortable">First Name</th>
						<th class="sortable">Last Name</th>
						<th class="sortable">Company</th>
						<th class="sortable">Public Number</th>
						<th class="sortable">Private Number</th>
						<th class="sortable">Select</th>
					</tr>
				</thead>
			<tbody>
			<s:iterator value="searchMemberRes" status="stat" var="iterMemberRes" >
				<tr>
					<s:hidden name="searchMemberRes[%{#stat.index}].micsUserKey" value="%{#iterMemberRes.micsUserKey}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsFirstName" value="%{#iterMemberRes.micsFirstName}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsLastName" value="%{#iterMemberRes.micsLastName}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsPubNumber" value="%{#iterMemberRes.micsPubNumber}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsPrivNumber" value="%{#iterMemberRes.micsPrivNumber}" />
					<s:hidden name="searchMemberRes[%{#stat.index}].micsCompanyKey" value="%{#iterMemberRes.micsCompanyKey}" />
					<td>
						<s:property value="%{#stat.count}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberRes.micsFirstName}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberRes.micsLastName}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberRes.micsCompanyKey}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberRes.micsPubNumber}"/>
					</td>
					<td>
						<s:property value="%{#iterMemberRes.micsPrivNumber}"/>
					</td>
					<td>
						<s:checkbox name="searchMemberRes[%{#stat.index}].selected" fieldValue="true" label="Selected"/>
					</td>
				</tr>
    		</s:iterator>
    			<tr>
    				<td colspan="6"><s:submit action="communityAddMemberEdit" key="mics.form.add" align="center" /></td>
    			</tr>
    		</tbody>
    		</table>
    	</td>
		<td>&nbsp;</td>
	</tr>
	</table>
	</s:if>
	<!--  End Iterator -->
			
		</td>
	</tr>
</table>
<s:hidden name="communityname" />
<s:hidden name="contactperson"/>
<s:hidden name="address" />
<s:hidden name="email" />
</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


