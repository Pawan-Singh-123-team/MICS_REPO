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
<s:form action="profileRuleAdd.action" method="post" namespace="/" validate="true">

<table class="formOutlineOrange">
	<s:if test="screeningtype == 1">
		<tr>
			<th colspan="5" align="center">MICS Profile Outgoing Rule Data</th>
		</tr>
	</s:if>
	<s:if test="screeningtype == 2">
		<tr>
			<th colspan="5" align="center">MICS Profile Incoming Rule Data</th>
		</tr>
	</s:if>
	<s:hidden name="callscreeningname" />
	<s:hidden name="callscreeningdesc" />
	<s:hidden name="screeningtype" />
	<s:hidden name="screeninglevel" />
	<s:hidden name="orderindex"/>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.ruleName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="rulename" key="mics.form.ruleName" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<s:hidden name="rulekey"/>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.timeband"/></td>
		<td class="textField">:</td>
		<td><s:select name="timebandkey" list='timebands' listKey="id.timebandkey" listValue="id.timebandname" label="Select Level" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.vlrPrefix"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="vlrprefix" key="mics.form.vlrPrefix" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.allow"/></td>
		<td class="textField">:</td>
		<td>
			<s:checkbox name="allow" fieldValue="true"/>
		</td>
		<td>&nbsp;</td>
	</tr>
	<!-- Outgoing Properties -->
	<s:if test="screeningtype == 1">
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.globalCIPrefix"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="globalciprefix" key="mics.form.globalCIPrefix" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.calledPartyType"/></td>
		<td class="textField">:</td>
		<td>
			<!--<s:textfield name="calledpartytype" key="mics.form.calledPartyType" size="25" class="fontContent"/>-->
			<s:checkbox name="companyOnNet" fieldValue="true"/>Company OnNet
			<s:checkbox name="virtualOnNet" fieldValue="true"/>Virtual OnNet
			<s:checkbox name="partnerOnNet" fieldValue="true"/>Partner OnNet
			<s:checkbox name="communityOnNet" fieldValue="true"/>Community OnNet
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>
			<s:checkbox name="homeNetwork" fieldValue="true"/>Home Network
			<s:checkbox name="national" fieldValue="true"/>National
			<s:checkbox name="international" fieldValue="true"/>International
			<s:checkbox name="shortcode" fieldValue="true"/>Shortcode
			<s:checkbox name="other" fieldValue="true"/>Other
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.calledPartyNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="calledpartynumber" key="mics.form.calledPartyNumber" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	</s:if>
	<!-- End Outgoing -->
	<!-- Incoming Properties -->
	<s:if test="screeningtype == 2">
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callingPartyType"/></td>
		<td class="textField">:</td>
		<td>
			<s:checkbox name="companyOnNet" fieldValue="true"/>Company OnNet
			<s:checkbox name="partnerOnNet" fieldValue="true"/>Partner OnNet
			<s:checkbox name="communityOnNet" fieldValue="true"/>Community OnNet
			<s:checkbox name="offNet" fieldValue="true"/>OffNet
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callingPartyNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="callingpartynumber" key="mics.form.callingPartyNumber" size="25" class="fontContent"/></td>
		<td>&nbsp;</td>
	</tr>
	</s:if>
	<!-- End Incoming -->
	
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.bearerType"/></td>
		<td class="textField">:</td>
		<td>
			<s:checkbox name="voiceBearer" fieldValue="true"/>Voice
			<s:checkbox name="videoBearer" fieldValue="true"/>Video
			<s:checkbox name="faxBearer" fieldValue="true"/>Fax
			<s:checkbox name="dataBearer" fieldValue="true"/>Data
			<s:checkbox name="smsBearer" fieldValue="true"/>SMS
			<s:checkbox name="otherBearer" fieldValue="true"/>Other
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.rejectAnno"/></td>
		<td class="textField">:</td>
		<td>
			<s:textfield name="rejectionannoid" key="mics.form.rejectAnno" size="25" class="fontContent"/>
		</td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.submit" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>
</s:form>
</td>
<td>&nbsp;</td>
</tr>
</table>


