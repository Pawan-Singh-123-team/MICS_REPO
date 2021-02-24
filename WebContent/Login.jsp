<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form action="login.action" method="post" namespace="/">
<table class="tableContainer">
	<tr>
		<td colspan="2">
			<s:actionerror />
  			<s:fielderror />
  			<s:actionmessage/>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<!-- SSO -->
			<table class="outlineorange">
				<tr>
					<th colspan="3" align="center">Login Using Single Sign-On</th>
				</tr>
				<tr>
					<td align="center" class="textContent">
						Do you have Telkomsel Account?
					</td>
				</tr>
				<tr>
					<td align="center" class="textContent">
						Then you can use the Single Sign-On:
					</td>
				</tr>
				<tr>
						<td colspan="3" align="center" class="textField"><s:submit method="sso" key="mics.form.login" align="center" /></td>
				</tr>
				<tr>
					<td align="center" class="textContent">
						If you have any problem with SSO please contact admin
					</td>
				</tr>
			</table>
		</td>
		<td valign="top">
			<!-- Manual Login -->
			<table class="outlineorange">
				<tr>
					<th colspan="4" align="center">Alternative Login</th>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td class="textField"><s:text name="mics.form.username"/></td>
					<td class="textField">:</td>
					<td><s:textfield name="username" key="mics.form.username" size="25" class="fontContent"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td class="textField"><s:text name="mics.form.password"/></td>
					<td class="textField">:</td>
					<td><s:password name="password" key="mics.form.password" size="25" class="fontContent"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.login" align="center" /></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</s:form>


