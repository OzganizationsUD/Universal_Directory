package com.ud.gui.services;

import javax.swing.JList;

public interface ListService {
	
	@SuppressWarnings("rawtypes")
	public JList createListComponent();
	
	public void setListData(Object[] listData);
}
