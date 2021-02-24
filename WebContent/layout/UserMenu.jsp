<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="sideMenu">
	<span id="menu" class="menu">
		<s:if test="#session.user != null">
			<ul id="sideMenuNavigation">
  	  	<li id="menuline_1">
  	    	<a href="initProvision.action">Provisioning</a>
  	    		<div class="highlightedTriangle">
  	    	</div>
  	  	</li>
  	  	<li id="menuline_2">
  	    	<a href="initMonitoring.action">Monitoring</a>
  	    		<div class="highlightedTriangle">
  	    	</div>
  	  	</li>
  	  	<li id="menuline_3">
  	    	<a href="initLog.action">Monitoring</a>
  	    		<div class="highlightedTriangle">
  	    	</div>
  	  	</li>
  	  	<li id="menuline_4">
  	    	<a href="logout.action">Logout</a>
  	    		<div class="highlightedTriangle">
  	    	</div>
  	  	</li>
  		</ul>
		</s:if>
	</span>
</div>
