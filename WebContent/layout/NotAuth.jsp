<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="bodyContent">
	<span id="titlePage" class="spanInBodyContent">
		Oops! Sorry you are not authorized.
	</span>
	<span id="errorPage" class="errorSpanInBodyContent">
		<s:actionerror />
		<s:property value="oneClickError"/>
	</span>
</div>
