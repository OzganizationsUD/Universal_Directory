package com.ud.gui.services.implement;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.TableService;
import com.ud.gui.services.JTableService;

@Service
public class JTableServiceImpl implements JTableService {

	private JTable jTable;
	
	@Autowired 
	private TableService tableService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JTable createTable(String tableName) {
		Table table = tableService.findTableByName(tableName);
		ForeignKey[] foreignKeys = table.getForeignKeys();
		
		List<DynaBean> list = tableService.getSelectAll(tableName);
		
		String[] columnNames = new String[table.getColumnCount()-1];
		for (int i = 1; i < table.getColumnCount(); i++)
			columnNames[i-1] = table.getColumn(i).getName();
		Object[][] data = null;
		if (list != null){
			data = new Object[list.size()][table.getColumnCount()-1];
			for (int i = 0; i < data.length; i++)
				for (int j = 1; j < table.getColumnCount(); j++) {
					
					Column column = table.getColumn(j);
					Object object=list.get(i).get(column.getName());
					ForeignKey foreignKey = findLocalKey(foreignKeys, column);
					
					if (foreignKey!=null){
						object = null;
					}else if (column.getTypeCode()==93){
						Timestamp date = (Timestamp) object;
						object = new SimpleDateFormat("dd.mm.yyyy").format(date);	
					}
					data[i][j-1] = object;
				}
		}
				
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		jTable = new JTable(model);
		for (int i = 0; i < table.getColumns().length; i++) {
			ForeignKey foreignKey = findLocalKey(foreignKeys, table.getColumn(i));
			if ((foreignKey!=null)&&(list!=null)){
				JComboBox comboBox = new JComboBox(getItemsComboBoxSubRecord(foreignKey.getForeignTable()));
				jTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));
			}
		}
		return jTable;
	}
	
	private ForeignKey findLocalKey(ForeignKey[] foreignKeys, Column column){
		if ((foreignKeys.length==0)||(column==null)||(foreignKeys==null))
			return null;
		
		for (ForeignKey foreignKey : foreignKeys) {
			if(foreignKey.getFirstReference().getLocalColumnName().equals(column.getName()))
				return foreignKey;
		}
		return null;
	}
	
	private Object[] getItemsComboBoxSubRecord(Table table){
		List<DynaBean> list = tableService.getSelectAll(table.getName());
		String[] data = new String[list.size()+1];
		
		for (String object : data) {
			object = "";
		}
		
		for (int i = 0; i < data.length-1; i++)
			for (int j = 1; j < table.getColumnCount(); j++) {
				Column column = table.getColumn(j);
				Object object=list.get(i).get(column.getName());
				if (column.getTypeCode()==93){
					Timestamp date = (Timestamp) object;
					object = new SimpleDateFormat("dd.mm.yyyy").format(date);	
				}
				data[i] += object.toString()+" | ";
			}
		
		return data;
	}

	public JTable getjTable() {
		return jTable;
	}
}
