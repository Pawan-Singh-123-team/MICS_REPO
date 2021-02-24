package com.nsn.telkomsel.mics.util;

import java.util.List;

import com.nsn.telkomsel.mics.orahbm.Switchboardmenu;

public class SwitchboardEntry {
	
	
	/**
	 * @return the menu
	 */
	public Switchboardmenu getMenu() {
		return menu;
	}
	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Switchboardmenu menu) {
		this.menu = menu;
	}
	/**
	 * @return the subMenu
	 */
	@SuppressWarnings("rawtypes")
	public List getSubMenu() {
		return subMenu;
	}
	/**
	 * @param subMenu the subMenu to set
	 */
	@SuppressWarnings("rawtypes")
	public void setSubMenu(List subMenu) {
		this.subMenu = subMenu;
	}
	private Switchboardmenu menu;
	@SuppressWarnings("rawtypes")
	private List subMenu;

}
