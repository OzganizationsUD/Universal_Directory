package com.ud.database.services;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Database;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
	
	@Autowired
	private DataSource dataSource;
	private Platform platform;
	private Database database;
	private DSLContext dslContext;
	
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
			dslContext = (DSL.using(dataSource.getConnection(), SQLDialect.POSTGRES));
			System.out.println("Create new platform and Data base!!!");
		}else
			System.err.println("ERROR: Not inizialize dataSourse!!! Or not connect data base!!!");
		
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

	public DSLContext getDslContext() {
		return dslContext;
	}
}
