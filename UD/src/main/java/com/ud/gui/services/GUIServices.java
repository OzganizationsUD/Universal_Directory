package com.ud.gui.services;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public interface GUIServices{
	
	public void showFrame();
	
	public void setLabelText(String text);
	
	@SuppressWarnings("rawtypes")
	public JList getJlist();
	
	public JTable getjTable();
	
	public void setjTable(TableModel model);
	
	public JButton getTableHeader();
	
	public String getTableCaption();
}