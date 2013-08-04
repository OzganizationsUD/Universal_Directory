package com.ud.database.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.BaseService;
import com.ud.database.services.TableService;

@Service
public class TableServiceImpl implements TableService {
	
	@Autowired
	private BaseService baseService;
	
	public List<String> getAllTableName() {
		Table[] tables = baseService.getDatabase().getTables();
		List<String> tableName = new ArrayList<String>();
		if (tables!=null)
			for (Table table : tables)
				tableName.add(table.getName());
			
		return tableName;
	}
	
}
