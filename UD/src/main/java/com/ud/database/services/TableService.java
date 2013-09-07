package com.ud.database.services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Table;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface TableService {
	
	public List<String> getAllTableName();

	public Table createTable(String tableName, List<Column> columns, List<ForeignKey> foreignKeys)throws DatabaseOperationException, SQLException;
	
	public void deleteTable(String nameTable);
	
	public Table findTableByName(String name);
	
	public List<DynaBean> getSelectAll(String tableName);
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void insert(String tableName, Object[][] data);
}
