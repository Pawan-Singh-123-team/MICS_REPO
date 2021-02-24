<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<s:head />
<sx:head />
<title><tiles:insertAttribute name="title" /></title>
<link href="<%=request.getContextPath()%>/css/mics.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/SpryAssets/SpryMenuBar.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/mics.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/SpryAssets/SpryMenuBarVertical.css" rel="stylesheet" type="text/css" />
<style type="text/css" media="all">
      @import url("css/maven-base.css"); @import url("css/maven-theme.css"); @import url("css/site.css"); @import
      url("css/screen.css");
    </style>
<link rel="stylesheet" href="./css/print.css" type="text/css" media="print" />
<!--[if lte IE 7]>
<style>
.content { margin-right: -1px; } /* this 1px negative margin can be placed on any of the columns in this layout with the same corrective effect. */
ul.nav a { zoom: 1; }  /* the zoom property gives IE the hasLayout trigger it needs to correct extra whitespace between the links */
</style>
<![endif]-->
</head>

<body onload="showClock();">

<div class="container">
  <!--  Start Container -->
  <div class="header">
  	<!-- Start Header -->
  	<tiles:insertAttribute name="header" />
    <!-- End  Header -->
  </div>
  <div class="sidebar1">
  	<!-- Start Menu -->
  	<tiles:insertAttribute name="menu" />
  	<!-- End Menu -->
  </div>
  <div class="content">
  	<!-- Start Body Content -->
  	<tiles:insertAttribute name="body" />
    <!-- End Body Content -->
  </div>
  <div class="footer">
  	<!-- Start Footer -->
  	<tiles:insertAttribute name="footer" />
    <!-- End Footer -->
  </div>
 <!-- End Container -->
</div>
<script type="text/javascript">
	var MenuBar1 = new Spry.Widget.MenuBar("MenuBar1", {imgRight:"<%=request.getContextPath()%>/SpryAssets/SpryMenuBarRightHover.gif"});
</script>
</body>
</html>