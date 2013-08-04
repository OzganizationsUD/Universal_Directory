package com.ud.database.services;

import org.apache.ddlutils.model.Column;

public interface ColumnService {

	public Column createColumn(String name, int type);
	public Column createColumn(String name, int type, boolean autoIncrement, boolean primaryKey);
}
