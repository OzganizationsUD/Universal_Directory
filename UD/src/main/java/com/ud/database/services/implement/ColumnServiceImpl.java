package com.ud.database.services.implement;

import org.apache.ddlutils.model.Column;

import com.ud.database.services.ColumnService;

public class ColumnServiceImpl implements ColumnService {
	
	public Column createColumn(String name, int type){
		Column column = new Column();
		column.setName(name);
		column.setTypeCode(type);
		return column;
	}
	
	public Column createColumn(String name, int type, boolean autoIncrement, boolean primaryKey){
		Column column = createColumn(name, type);
		column.setAutoIncrement(autoIncrement);
		column.setPrimaryKey(primaryKey);
		return column;
	}
}
