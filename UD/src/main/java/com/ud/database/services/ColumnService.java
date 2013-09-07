package com.ud.database.services;

import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.ForeignKey;

public interface ColumnService {

	public Column createColumn(String name, int type);
	
	public Column createColumn(String name, int type, boolean autoIncrement, boolean primaryKey);
	
	public ForeignKey createForeignKey(String name, String foreignTableName, String columnNameFk);
}
