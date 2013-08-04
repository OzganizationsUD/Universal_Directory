package com.ud.gui.services;

import javax.swing.JList;

public interface GUIServices{
	
	public void showFrame();
	
	public void setLabelText(String text);
	
	@SuppressWarnings("rawtypes")
	public JList getJlist();
}