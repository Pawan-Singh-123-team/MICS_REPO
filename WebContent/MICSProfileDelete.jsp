<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

function onTypeChange() {

document.forms[0].action = 'profileScreeningTypeChange.action';

document.forms[0].submit();

}

</script>
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
<s:form action="profileDelete.action" method="post" namespace="/">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Profile General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="callscreeningname" key="mics.form.callScreeningName" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
		<s:hidden name="callscreeningkey"/>
		<s:hidden name="companykey"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningDesc"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="callscreeningdesc" key="mics.form.callScreeningDesc" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<!--
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="25" class="fontContent" readonly="true"/></td>
		<td>&nbsp;</td>
	</tr>
	-->
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.type"/></td>
		<td class="textField">:</td>
		<td><s:select name="screeningtype" list='screeningtypes' listKey="id" listValue="name" label="Select Type" disabled="true" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.level"/></td>
		<td class="textField">:</td>
		<td><s:select name="screeninglevel" list='screeninglevels' listKey="id" listValue="name" label="Select Level" disabled="true" width="264" style="width: 264px"/></td>
		<td>&nbsp;</td>
	</tr>
	<!--  Iter Profile Rules -->
	<s:if test="rules != null && !rules.isEmpty">
	<tr>
		<td>&nbsp;</td>
		<td colspan="3">
			<table>
				<thead>
					<tr>
						<th class="sortable">No</th>
						<th class="sortable">Del</th>
						<th class="sortable">Rule Name</th>
						<th class="sortable">VLR Prefix</th>
						<s:if test="screeningtype == 1">
							<th class="sortable">Global Cell ID Prefix</th>
						</s:if>
					</tr>
				</thead>
			<tbody>
			<s:iterator value="rules" status="stat" var="screeningRule" >
				<tr>
					<s:hidden name="rules[%{#stat.index}].rulekey" value="%{#screeningRule.rulekey}" />
					<s:hidden name="rules[%{#stat.index}].timebandkey" value="%{#screeningRule.timebandkey}" />
					<s:hidden name="rules[%{#stat.index}].callpartynumber" value="%{#screeningRule.callpartynumber}" />
					<s:hidden name="rules[%{#stat.index}].callpartytype" value="%{#screeningRule.callpartytype}" />
					<s:hidden name="rules[%{#stat.index}].allow" value="%{#screeningRule.allow}" />
					<s:hidden name="rules[%{#stat.index}].bearerType" value="%{#screeningRule.bearerType}" />
					<s:hidden name="rules[%{#stat.index}].rejectionannoid" value="%{#screeningRule.rejectionannoid}" />
					<s:hidden name="rules[%{#stat.index}].profileName" value="%{#screeningRule.profileName}" />
					<s:hidden name="rules[%{#stat.index}].profileDesc" value="%{#screeningRule.profileDesc}" />
					<s:hidden name="rules[%{#stat.index}].screeningType" value="%{#screeningRule.screeningType}" />
					<s:hidden name="rules[%{#stat.index}].screeningLevel" value="%{#screeningRule.screeningLevel}" />
					<td>
						<s:textfield name="rules[%{#stat.index}].orderindex" key="mics.form.orderIndex" size="2" class="fontContent" readonly="true"/>
					</td>
					<td>
						<s:checkbox name="rules[%{#stat.index}].remove" fieldValue="true" label="Remove"/>
					</td>
					<td>
						<s:textfield name="rules[%{#stat.index}].rulename" key="mics.form.ruleName" size="25" class="fontContent" readonly="true"/>
					</td>
					<td>
						<s:textfield name="rules[%{#stat.index}].vlrprefix" key="mics.form.vlrPrefix" size="10" class="fontContent" readonly="true"/>
					</td>
					<s:if test="screeningtype == 1">
					<td>
						<s:textfield name="rules[%{#stat.index}].globalciprefix" key="mics.form.globalCIPrefix" size="10" class="fontContent" readonly="true"/>
					</td>
					</s:if>
					
				</tr>
    		</s:iterator>
    			
    		</tbody>
    		</table>
    		
    	</td>
		<td>&nbsp;</td>
	</tr>
	</s:if>
	<!--  End Iter Profile Rules -->
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textWarn"></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textWarn">Are you really want to delete this Profile ? this process can not be undone</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.delete" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>
</s:form>
</td>
<td>&nbsp;</td>
</tr>
</table>


