package com.ud.test.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ud.database.services.ColumnService;
import com.ud.database.services.TableService;
import com.ud.test.PersistenceTest;

public class TableServiceTest  {
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private ColumnService  columnService;
	
	@Ignore
	@Test
	public void testGetAllName(){
		List<String> list = tableService.getAllTableName();
		assertNotNull(list);
		assertEquals(2L, list.size());
	}
	@Ignore
	@Test
	public void createTable() throws DatabaseOperationException, SQLException{
		
		String tableName = "usertest";
		
		List<Column> listColumns = new ArrayList<Column>();
		listColumns.add(columnService.createColumn("id", Types.BIGINT, true, true));
		listColumns.add(columnService.createColumn("login", Types.VARCHAR));
		listColumns.add(columnService.createColumn("pass", Types.VARCHAR));
		listColumns.add(columnService.createColumn("email", Types.VARCHAR));
		
		tableService.createTable(tableName, listColumns);
		
		Table table = tableService.findTableByName(tableName);
		assertNotNull(table);
		assertEquals(4L, table.getColumnCount());
	}
	@Ignore
	@Test
	public void testFindTableByName(){
		Table table = tableService.findTableByName("new_table");
		assertNotNull(table);
		assertEquals(2L, table.getColumnCount());
	}
	@Ignore
	@Test
	public void testgetAllTableRecord(){
		List<DynaBean> list = tableService.getAllTableRecord("new_table");
		assertNotNull(list);		
		assertEquals(10L, list.size());
	}
}
