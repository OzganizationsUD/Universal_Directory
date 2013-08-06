package com.ud.database.services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;
import org.springframework.transaction.annotation.Transactional;

public interface TableService {
	
	public List<String> getAllTableName();
	
	@Transactional
	public Table createTable(String tableName, List<Column> columns)throws DatabaseOperationException, SQLException;
	
	public Table findTableByName(String name);
	
	public List<DynaBean> getAllTableRecord(String name);
}
