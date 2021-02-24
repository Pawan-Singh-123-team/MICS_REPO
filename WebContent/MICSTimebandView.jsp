<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
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
		<th colspan="5" align="center">MICS Timeband General Data</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.timebandName"/>*</td>
		<td class="textField">:</td>
		<td><s:textfield name="timebandName" key="mics.form.timebandName" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
		<s:hidden name="timebandKey"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.timebandDesc"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="timebandDescription" key="mics.form.timebandDesc" size="35" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.daySelection"/></td>
		<td class="textField">:</td>
		<td>
			<s:checkbox name="sunday" fieldValue="true" disabled="true"/>Sunday
			<s:checkbox name="monday" fieldValue="true" disabled="true"/>Monday
			<s:checkbox name="tuesday" fieldValue="true" disabled="true"/>Tuesday
			<s:checkbox name="wednesday" fieldValue="true" disabled="true"/>Wednesday
			<s:checkbox name="thursday" fieldValue="true" disabled="true"/>Thursday
			<s:checkbox name="friday" fieldValue="true" disabled="true"/>Friday
			<s:checkbox name="saturday" fieldValue="true" disabled="true"/>Saturday
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.allDay"/></td>
		<td class="textField">:</td>
		<td><s:checkbox name="allDay" fieldValue="true" label="mics.form.allDay" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.begin"/></td>
		<td class="textField">:</td>
		<td><s:text name="mics.form.hour"/><s:textfield name="beginHour" key="mics.form.hour" size="5" class="fontContent" disabled="true"/>&nbsp;<s:text name="mics.form.minute"/><s:textfield name="beginMinute" key="mics.form.minute" size="5" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.end"/></td>
		<td class="textField">:</td>
		<td><s:text name="mics.form.hour"/><s:textfield name="endHour" key="mics.form.hour" size="5" class="fontContent" disabled="true"/>&nbsp;<s:text name="mics.form.minute"/><s:textfield name="endMinute" key="mics.form.minute" size="5" class="fontContent" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.invertTimeSelection"/></td>
		<td class="textField">:</td>
		<td><s:checkbox name="invertTimeSelection" fieldValue="true" label="mics.form.invertTimeSelection" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.forever"/></td>
		<td class="textField">:</td>
		<td><s:checkbox name="forever" fieldValue="true" label="mics.form.forever" disabled="true"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.effStartTime"/></td>
		<td class="textField">:</td>
		<td> <sx:datetimepicker name="effectiveStartTime"   displayFormat="yyyy/MM/dd HH:mm" disabled="true"/></td>
		<!--<s:textfield name="effectiveStartTime" key="mics.form.effStartTime" size="25" class="fontContent"/> yyyy/MM/dd HH:mm</td>-->
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.effStopTime"/></td>
		<td class="textField">:</td>
		<td><sx:datetimepicker name="effectiveStopTime"  displayFormat="yyyy/MM/dd HH:mm" disabled="true"/></td>
		<!--<s:textfield name="effectiveStopTime" key="mics.form.effStopTime" size="25" class="fontContent"/> yyyy/MM/dd HH:mm</td>-->
		<td>&nbsp;</td>
	</tr>
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


