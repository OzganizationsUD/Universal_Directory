package com.ud.gui.enums;

import java.sql.Types;

public enum TypesForCreator {

	DOUBLE(Types.DOUBLE, "Числовой"),
	STRING(Types.VARCHAR, "Текстовый"),
	DATE(Types.TIMESTAMP, "Дата");
	
	public final int typeSql;
	public final String description;
	
	private TypesForCreator(int typeSql, String description){
		this.typeSql = typeSql;
		this.description = description;
	}
	
	public static TypesForCreator valueOfDesc(String description){
		for (TypesForCreator typesForCreator : values())
			if (typesForCreator.description.equals(description))
				return typesForCreator;
		return null;
	}

	public int getTypeSql() {
		return typeSql;
	}

	public String getDescription() {
		return description;
	}
}
