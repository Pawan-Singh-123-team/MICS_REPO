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
<s:form action="" method="post" namespace="/" validate="true">

<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">MICS Company General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyKey"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="companykey" key="mics.form.companyKey" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="companyname" key="mics.form.companyName" size="35" class="fontContent" disabled="true" /></td>
		<td>&nbsp;</td>
	</tr>
	<s:if test="tselAdmin">
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.chargeString"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="chargestring" key="mics.form.chargeString" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	</s:if>
	<s:else>
		<s:hidden name="chargestring" />
	</s:else>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.contactPerson"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="contactperson" key="mics.form.contactPerson" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.email"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="email" key="mics.form.email" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.address"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="address" key="mics.form.address" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<!-- add domain name -->
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.domainname"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="domainname" key="mics.form.domainname" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<s:if test="tselAdmin">
	<tr>
		<th colspan="5" align="center">MICS Company Permission</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<s:checkbox name="boolForcedOnnet" fieldValue="true" label="mics.form.forcedOnNet" disabled="true"/><s:text name="mics.form.forcedOnNet"/>
		</td>
		<td class="textField"></td>
		<td>
			<s:checkbox name="boolEnableGroupHunting" fieldValue="true" label="mics.form.groupHunting" disabled="true"/><s:text name="mics.form.groupHunting"/>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<s:checkbox name="boolEnablePrivCall" fieldValue="true" label="mics.form.privateCall" disabled="true"/><s:text name="mics.form.privateCall"/>
		</td>
		<td class="textField"></td>
		<td>
			<s:checkbox name="boolLocked" fieldValue="true" label="mics.form.subcriberLocked" disabled="true"/><s:text name="mics.form.subcriberLocked"/>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<s:checkbox name="boolEnablevpnchargingforsms" fieldValue="true" label="mics.form.enablevpnchargingforsms" disabled="true"/><s:text name="mics.form.enablevpnchargingforsms"/>
		</td>
		<td class="textField"></td>
		<td>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th colspan="5" align="center">MICS Company Properties</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.companyCLI"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="companycli" key="mics.form.companyCLI" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.useMainCLI"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="usemaincliprefix" key="mics.form.useMainCLI" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.cliOption"/></td>
		<td class="textField">:</td>
		<td><s:radio label="mics.form.cliOption" name="clioption" list="#{'0':'MSISDN','1':'PNP'}" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th colspan="5" align="center">MICS Company Outgoing Forced Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="forcedoutcs" headerKey="" headerValue="Select Outgoing Profile" list='outgoingProfiles' listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" width="264" style="width: 264px" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<th colspan="5" align="center">MICS Company Outgoing Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="defaultoutcs" headerKey="" headerValue="Select Outgoing Profile" list='outgoingProfiles' listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" width="264" style="width: 264px" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<th colspan="5" align="center">MICS Company Incoming Forced Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="forcedincs" headerKey="" headerValue="Select Incoming Profile" list='incomingProfiles' listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" width="264" style="width: 264px" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<th colspan="5" align="center">MICS Company Incoming Profiles</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.callScreeningName"/></td>
		<td class="textField">:</td>
		<td><s:select name="defaultincs" headerKey="" headerValue="Select Incoming Profile" list='incomingProfiles' listKey="callscreeningkey" listValue="%{callscreeningname + ' - ' + callscreeningdesc}" label="Select Profile" width="264" style="width: 264px" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	</s:if>
	<s:else>
		<s:hidden name="chargestring" />
		<s:hidden name="boolForcedOnnet" />
		<s:hidden name="boolEnableGroupHunting" />
		<s:hidden name="boolLocked" />
		<s:hidden name="boolEnablePrivCall" />
		<s:hidden name="companycli" />
		<s:hidden name="usemaincliprefix" />
		<s:hidden name="clioption" />
		
		<s:hidden name="forcedoutcs" />
		<s:hidden name="boolBypassForcedOutCS" />
		<s:hidden name="forcedoutcsdesc" />
		
		<s:hidden name="defaultoutcs" />
		<s:hidden name="boolBypassDefaultOutCS" />
		<s:hidden name="defaultoutcsdesc" />
		
		<s:hidden name="forcedincs" />
		<s:hidden name="boolBypassForcedInCS" />
		<s:hidden name="forcedincsdesc" />
		
		<s:hidden name="defaultincs" />
		<s:hidden name="boolBypassDefInCS" />
		<s:hidden name="defaultincsdesc" />
	</s:else>
	<s:if test="editAllowed">
		<s:url id="editUrl" action="companyInitEdit.action">
        	<s:param name="companykey" value="#companykey" />
      	</s:url>
        
		<tr>
			<td>&nbsp;</td>
			<td colspan="3" class="textField"><s:a href="%{editUrl}">Edit</s:a></td>
			<td>&nbsp;</td>
		</tr>	
	</s:if>
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"></td>
		<td>&nbsp;</td>
	</tr>
</table>
</s:form>
</td>
<td>&nbsp;</td>
</tr>
</table>


