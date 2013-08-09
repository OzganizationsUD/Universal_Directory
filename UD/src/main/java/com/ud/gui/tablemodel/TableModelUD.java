package com.ud.gui.tablemodel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.model.Table;

public class TableModelUD implements TableModel {
	
	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
	
	private List<DynaBean> list;
	private Table table;
	
	public TableModelUD(Table table, List<DynaBean> list){
		this.table = table;
		this.list = list;
	}
	public void addTableModelListener(TableModelListener arg0) {
		listeners.add(arg0);
	}

	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	public int getColumnCount() {
		return table.getColumnCount();
	}

	public String getColumnName(int arg0) {
		return table.getColumn(arg0).getName();
	}

	public int getRowCount() {
		return list==null ? 0: list.size();
	}

	public Object getValueAt(int arg0, int arg1) {
		if ((list==null)||(list.isEmpty()))
			return null;
		DynaBean dynaBean = list.get(arg0);
		return dynaBean.get(table.getColumn(arg1).getName());
	}

	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeTableModelListener(TableModelListener arg0) {
		listeners.remove(arg0);
	}

	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
