package com.ud.gui.services.implement;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.TableService;
import com.ud.gui.services.JTableService;

@Service
public class JTableServiceImpl implements JTableService {

	@Autowired 
	private TableService tableService;
	
	public JTable createTable(String tableName) {
		Table table = tableService.findTableByName(tableName);
		List<DynaBean> list = tableService.getSelectAll(tableName);
		
		String[] columnNames = new String[table.getColumnCount()-1];
		for (int i = 1; i < table.getColumnCount(); i++)
			columnNames[i-1] = table.getColumn(i).getName();
		Object[][] data = null;
		if (list != null){
			data = new Object[list.size()][table.getColumnCount()-1];
			for (int i = 0; i < data.length; i++)
				for (int j = 1; j < table.getColumnCount(); j++) {
					Object object=list.get(i).get(table.getColumn(j).getName());
					if (table.getColumn(j).getTypeCode()==93){
						Timestamp date = (Timestamp) object;
						object = new SimpleDateFormat("dd.mm.yyyy").format(date);	
					}
					data[i][j-1] = object;
				}
		}
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable jTable = new JTable(model);
		return jTable;
	}

}
