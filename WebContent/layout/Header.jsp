<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	
<table class="headerTable" >
	<tr>
        	<td ><h1><a href="#"><img src="images/newnsn.png" alt="NSN" name="NSN" width="127" height="75" id="Insert_logo" style="background: #FFF; display:block;" /></a></h1></td>
            <td align="center"><h1 class="headertitle">MICS Portal</h1></td>
            <td>
            </td>
            <td align="right"><h1><a href="#"><img src="images/logo_tsel.png" alt="Telkomsel" name="Telkomsel" width="139" height="56" id="Telkomsel_Logo" style="background: ##FFF; display:block;" /></a></h1></td>
        </tr>
       <tr bgcolor="#FF6E00">
       		<td colspan="2" class="noPadding">&nbsp;
       		<label id="show_user" class="userInfo">
       		<s:if test="%{#session.user != null}">
 						Welcome, <b><s:property value="%{#session.user.webusername}" /></b>
			</s:if>
			<s:if test="%{#session.currCompany != null}">
						<s:url id="viewUrl" action="companyInitView.action">
                    		<s:param name="companykey" value="%{#session.currCompany.companykey}" />
                		</s:url>
 						Company: <s:a href="%{viewUrl}"><b><s:property value="%{#session.currCompany.companykey}" /> <s:property value="%{#session.currCompany.companyname}" /></b></s:a>
			</s:if>
       			
       		</label>&nbsp;</td>
        	<td colspan="2" class="noPadding"><label id="show_clock" class="clock"></label>&nbsp;</td>
        </tr>
    </table>
