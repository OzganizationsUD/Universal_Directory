package com.ud.test.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Table;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ud.database.services.ColumnService;
import com.ud.database.services.TableService;
import com.ud.test.PersistenceTestJUnit;

public class TableServiceTest extends PersistenceTestJUnit  {
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private ColumnService  columnService;
	
	@Test
	public void testGetAllName(){
		List<String> list = tableService.getAllTableName();
		assertNotNull(list);
		assertEquals(2L, list.size());
	}

	@Test
	public void createAndDeleteTable() throws DatabaseOperationException, SQLException{
		
		String tableName = "usertest";
		
		List<Column> listColumns = createColumns();
		
		tableService.createTable(tableName, listColumns,null);
		
		Table table = tableService.findTableByName(tableName);
		assertNotNull(table);
		assertEquals(4L, table.getColumnCount());
		
		tableService.deleteTable(tableName);
		table = tableService.findTableByName(tableName);
		assertNull(table);
	}

	private List<Column> createColumns() {
		List<Column> listColumns = new ArrayList<Column>();
		listColumns.add(columnService.createColumn("login", Types.VARCHAR));
		listColumns.add(columnService.createColumn("pass", Types.DECIMAL));
		listColumns.add(columnService.createColumn("date", Types.TIMESTAMP));
		return listColumns;
	}
	
	@Test
	public void testFindTableByName(){
		Table table = tableService.findTableByName("new_table");
		assertNotNull(table);
		assertEquals(2L, table.getColumnCount());
	}

	@Test
	public void testgetAllTableRecord(){
		List<DynaBean> list = tableService.getSelectAll("new_table");
		assertNotNull(list);		
		assertEquals(10L, list.size());
	}
	
	@Test
	public void createTableWidthForeignKey() throws DatabaseOperationException, SQLException{
		String tableName1 = "usertable1";
		String tableName2 = "usertable2";
		
		Table table1 = tableService.createTable(tableName1, createColumns(), null);
		assertNotNull(table1);
		
		List<Column> columns = new ArrayList<Column>();
		columns.add(columnService.createColumn("column_id", Types.BIGINT));
		
		List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
		foreignKeys.add(columnService.createForeignKey("foreign_key", tableName1, columns.get(0).getName()));
		
		Table table2 = tableService.createTable(tableName2, columns, foreignKeys);
		assertNotNull(table2);
		
		tableService.deleteTable(tableName2);
		tableService.deleteTable(tableName1);
	}
}
