package com.ud.database.services.implement;

import java.sql.Types;

import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Reference;
import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.ColumnService;
import com.ud.database.services.TableService;

@Service
public class ColumnServiceImpl implements ColumnService {
	
	@Autowired
	private TableService tableService;
	
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

	public ForeignKey createForeignKey(String name, String foreignTableName, String columnNameFk) {
		
		Table table = tableService.findTableByName(foreignTableName);
		
		if (table==null)
			return null;
		
		Reference reference = new Reference();
		reference.setLocalColumnName(columnNameFk);
		reference.setForeignColumn(table.getColumn(0));
		
		ForeignKey foreignKey = new ForeignKey(name);
		foreignKey.setForeignTable(table);
		foreignKey.setForeignTableName(foreignTableName);
		foreignKey.addReference(reference);
		
		return foreignKey;
	}
}
