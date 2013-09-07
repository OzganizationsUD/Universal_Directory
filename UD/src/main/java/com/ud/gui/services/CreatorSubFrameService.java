package com.ud.gui.services;

import java.util.List;

import javax.swing.JFrame;

import org.apache.ddlutils.model.ForeignKey;

public interface CreatorSubFrameService {
	
	public JFrame createFrame();
	
	public String getTableNameSelected();
	
	public String getColumnNameSelected();
	
	public List<ForeignKey> getForeignKeys();
}
