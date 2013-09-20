package com.ud.database.services.implement;

import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.io.converters.TimestampConverter;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truemesh.squiggle.SelectQuery;
import com.truemesh.squiggle.criteria.MatchCriteria;
import com.ud.database.services.BaseService;
import com.ud.database.services.ColumnService;
import com.ud.database.services.TableService;


@Service
public class TableServiceImpl implements TableService {
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private ColumnService columnService;
	
	public List<String> getAllTableName() {
		Table[] tables = baseService.getDatabase().getTables();
		List<String> tableName = new ArrayList<String>();
		if (tables!=null)
			for (Table table : tables)
				tableName.add(table.getName());
			
		return tableName;
	}

	public Table createTable(String tableName, List<Column> columns, List<ForeignKey> foreignKeys) throws DatabaseOperationException, SQLException {
		Table table = new Table();
		table.setName(tableName);
		
		Column id = columnService.createColumn("id_"+tableName, Types.BIGINT, true, true);
		columns.add(0, id);
		
		table.addColumns(columns);
		
		if (foreignKeys!=null)
			table.addForeignKeys(foreignKeys);
		
		Database database = baseService.getDatabase();
		database.addTable(table);
		baseService.getPlatform().alterTables(database, true);
		return table;
	}
	
	public void deleteTable(String nameTable) {
		Table table = findTableByName(nameTable);	
		baseService.getPlatform().dropTable(baseService.getDatabase(), table, false);
		baseService.getDatabase().removeTable(table);
	}

	public Table findTableByName(String name) {
		return baseService.getDatabase().findTable(name);
	}

	@SuppressWarnings("rawtypes")
	public List<DynaBean> getSelectAll(String name) {
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
	
	public DynaBean findById(String tableName, Long id) {//TODO types convert. write Query!
		com.truemesh.squiggle.Table tableSql = new com.truemesh.squiggle.Table(tableName);
		SelectQuery select = new SelectQuery();
		Table tableDB = findTableByName(tableName);
		select.addToSelection(tableSql.getWildcard());
		select.addCriteria(new MatchCriteria(tableSql, tableDB.getPrimaryKeyColumns()[0].getName(),
				MatchCriteria.EQUALS, id ));
		System.out.println(select.toString());
		Iterator iterator = baseService.getPlatform().query(baseService.getDatabase(), select.toString());
		while (iterator.hasNext()) {
			return (DynaBean) iterator.next();
		}
		return null;
	}

	public void insert(String tableName, Object[][] data) {
		Table table = findTableByName(tableName);
		DynaBean dynaBean = baseService.getDatabase().createDynaBeanFor(table);
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++){
				System.out.println(table.getColumn(j+1).getTypeCode());
				dynaBean.set(table.getColumn(j+1).getName(), convertType(data[i][j], table.getColumn(j+1).getTypeCode()));
			}
			baseService.getPlatform().insert(baseService.getDatabase(), dynaBean);
		}	
	}	
	
	private Object convertType(Object data, int type){
		Object res = null;
		switch (type) {
		case 8: //Double
			res = new Double(data.toString());
			break;
		case -5://Long
			res = new Long((String)data);
			break;
		case 93://Date				
			try {
				Date date = new SimpleDateFormat("dd.mm.yyyy").parse(data.toString());
				data = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
				TimestampConverter converter = new TimestampConverter();
				res = converter.convertFromString(data.toString(), type);
			break;
		default:
			res = data;
			break;
		}
		
		return res;
	}
}
