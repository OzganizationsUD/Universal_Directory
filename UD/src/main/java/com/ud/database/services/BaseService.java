package com.ud.database.services;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
	
	@Autowired
	private DataSource dataSource;
	private Platform platform;
	private Database database;
	
	public BaseService() throws DatabaseOperationException, SQLException{
		createPlatform();
	}
	
	public BaseService(DataSource dataSource) throws DatabaseOperationException, SQLException{
		this.dataSource = dataSource;
		createPlatform();
	}
	
	public void createPlatform() throws DatabaseOperationException, SQLException{
		if (dataSource != null){
			platform = PlatformFactory.createNewPlatformInstance(dataSource);
			database = platform.readModelFromDatabase(dataSource.getConnection().getCatalog());
			System.out.println("Create new platform and Data base!!!");
		}else
			System.err.println("Not inizialize dataSourse!!! Or not connect data base!!!");
		
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public Platform getPlatform() {
		return platform;
	}

	public Database getDatabase() {
		return database;
	}
}
