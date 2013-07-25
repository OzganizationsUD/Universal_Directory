package com.ud.main;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import java.sql.*;

import javax.sql.DataSource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;
import org.apache.ddlutils.platform.SqlBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@Service
public class MainApp 
{
	private static Scanner cin= new Scanner(System.in);
    
	public static void main(String[] args ) throws SQLException
    {
		
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/application-context.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        Database database = platform.readModelFromDatabase(dataSource.getConnection().getCatalog());
        System.out.println();
        platform.dropTables(database, false);
        Column column = new Column();
        column.setAutoIncrement(true);
        column.setTypeCode(Types.BIGINT);
        column.setName("id");
        
        Column column1 = new Column();
        column1.setTypeCode(Types.VARCHAR);
        column1.setName("name");
        
        
        Table table = new Table();
        table.addColumn(column);
        table.addColumn(column1);
        table.setName("new_table");
        database.addTable(table);
        platform.createTables(database, false, false);
        
        DynaBean dynaBean = database.createDynaBeanFor(table);
        dynaBean.set(table.getColumn(1).getName(), "vasa");
        platform.insert(database, dynaBean);
        
       Iterator iterator = platform.query(database,"select * from "+table.getName());
       while (iterator.hasNext()){
    	   DynaBean bean = (DynaBean) iterator.next();
    	   System.out.println(bean.get(column1.getName()));
       }
      // System.out.print();
       cin.nextBigInteger();
    }
}
