package com.ud.gui.services;

import javax.swing.JPanel;
import javax.swing.JTable;

public interface DataPanelService {
	
	public JPanel createDataTablePanel();
	
	public JTable getjTable();
	
	public void setjTable(JTable jTable);
	
	public JPanel getjPanel();
	
}
