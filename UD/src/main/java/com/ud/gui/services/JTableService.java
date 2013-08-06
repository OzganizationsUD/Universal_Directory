package com.ud.gui.services;

import java.util.List;

import javax.swing.JTable;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.model.Table;

public interface JTableService {
	
	public JTable createTable(Table table, List<DynaBean> list);

}
