package com.ud.database.services.implement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ud.database.services.BaseService;
import com.ud.database.services.TableService;

@Service
public class TableServiceImpl implements TableService {
	
	@Autowired
	private BaseService baseService;
	
	public List<String> getAllTableName() {
		Table[] tables = baseService.getDatabase().getTables();
		List<String> tableName = new ArrayList<String>();
		if (tables!=null)
			for (Table table : tables)
				tableName.add(table.getName());
			
		return tableName;
	}

	@Transactional
	public Table createTable(String tableName, List<Column> columns) throws DatabaseOperationException, SQLException {
		Table table = new Table();
		table.setName(tableName);
		table.addColumns(columns);
		Database database = new Database();
		database.addTable(table);
		baseService.getPlatform().createTables(database,false, true);
		baseService.createPlatform();
		return table;
	}

	public Table findTableByName(String name) {
		return baseService.getDatabase().findTable(name);
	}

	@SuppressWarnings("rawtypes")
	public List<DynaBean> getAllTableRecord(String name) {
		List<DynaBean> list = new ArrayList<DynaBean>();
		Iterator iterator = baseService.getPlatform().query(
				baseService.getDatabase(),
				baseService.getDslContext().select().from(name).getSQL());
		while (iterator.hasNext()) {
			DynaBean dynaBean = (DynaBean) iterator.next();
			list.add(dynaBean);
		}
		return list.isEmpty() ? null : list;
	}	
}
