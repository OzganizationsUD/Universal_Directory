package com.ud.gui.services.implement;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.model.Table;
import org.springframework.stereotype.Service;

import com.ud.gui.services.JTableService;
import com.ud.gui.tablemodel.TableModelUD;

@Service
public class JTableServiceImpl implements JTableService {

	public JTable createTable(Table table, List<DynaBean> list) {
		TableModel model = new TableModelUD(table,list);
		JTable jTable = new JTable(model);
		return jTable;
	}

}
