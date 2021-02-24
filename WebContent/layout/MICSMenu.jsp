<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<ul id="MenuBar1" class="MenuBarVertical">
	<s:if test="#session.menus != null"> 
		<s:iterator value="#session.menus" status="menuIdx" var="menuVar">
			<s:if test="%{#menuVar.parentmenu == null || #menuVar.parentmenu == ''}">
				<s:if test='%{#menuVar.link.trim().equals("#")}'>
					<li><a class="MenuBarItemSubmenu" href="<s:property value="#menuVar.link"/>"><s:property value="#menuVar.micsmenuname"/></a>
						<ul>
						<s:iterator value="#session.menus" status="submenuIdx" var="submenuVar">
							<s:if test="#submenuVar.parentmenu == #menuVar.micsmenukey">
								<li><a href="<s:property value="#submenuVar.link"/>"><s:property value="#submenuVar.micsmenuname"/></a></li>
							</s:if>
						</s:iterator>
						</ul>
					</li>
				</s:if>
				<s:else>
					<li><a href="<s:property value="#menuVar.link"/>"><s:property value="#menuVar.micsmenuname"/></a></li>
				</s:else>
			</s:if>
		</s:iterator>
	</s:if>
	<li><a href="logout.action">Logout</a></li>
</ul>