package com.ud.main;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.ud.database.services.BaseService;
import com.ud.database.services.ColumnService;
import com.ud.database.services.TableService;
import com.ud.database.services.implement.ColumnServiceImpl;
import com.ud.gui.services.GUIServices;

@Component
public class MainApp 
{
	private static Scanner cin= new Scanner(System.in);
   
	@Autowired
	private BaseService baseService;
	
	@Autowired 
	private GUIServices guiService;
	
	@Autowired
	private TableService tableService;
	
	public static void main(String[] args ) throws SQLException
    {
		
		try{
	        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/application-context.xml");
	        MainApp mainApp = context.getBean(MainApp.class);
	       	mainApp.start();
	        
	       	cin.nextBigInteger();
		}catch (BeanCreationException e){
			System.out.println("Not Data Base!!!");
		}        
    }

	private void start() throws SQLException{
		baseService.getPlatform().dropTables(baseService.getDatabase(), false);
		//baseService.getPlatform().dropDatabase("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/ud_test", "postgres", "1");
		baseService.createPlatform();
		ColumnService columnService = new ColumnServiceImpl();

		List<Column> list = new ArrayList<Column>();
		list.add(columnService.createColumn("id", Types.BIGINT, true, true));
		list.add(columnService.createColumn("name", Types.VARCHAR));

		Table table = tableService.createTable("new_table", list);

		DynaBean dynaBean = baseService.getDatabase().createDynaBeanFor(table);
		for (int i = 0; i < 10; i++) {
			dynaBean.set(table.getColumn(1).getName(), "name"+i);
			baseService.getPlatform().insert(baseService.getDatabase(), dynaBean);
		}
		
		List<Column> columns = new ArrayList<Column>();
		columns.add(columnService.createColumn("id", Types.BIGINT, true, true));
		columns.add(columnService.createColumn("login", Types.VARCHAR));
		columns.add(columnService.createColumn("pass", Types.VARCHAR));
		columns.add(columnService.createColumn("email", Types.VARCHAR));
		
		Table table1 = tableService.createTable("user_ud", columns);
		dynaBean = baseService.getDatabase().createDynaBeanFor(table1);
		for (int i = 0; i < 60; i++) {
			dynaBean.set(table1.getColumn(1).getName(), "login"+i+10);
			dynaBean.set(table1.getColumn(2).getName(), i+1000);
			dynaBean.set(table1.getColumn(3).getName(), "email"+i+100+"@gmail.com");
			baseService.getPlatform().insert(baseService.getDatabase(), dynaBean);
		}	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		        guiService.showFrame();
			}
		});
//     System.out.print();
	}
}
