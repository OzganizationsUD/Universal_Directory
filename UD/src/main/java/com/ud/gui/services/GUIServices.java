package com.ud.gui.services;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JSplitPane;

public interface GUIServices{
	
	public void showFrame();
	
	public JFrame getFrame();
	
	public void setLabelText(String text);
	
	@SuppressWarnings("rawtypes")
	public JList getJlist();
	
	public JButton getTableHeader();
	
	public String getTableCaption();
	
	public JSplitPane getSplitPane();
}