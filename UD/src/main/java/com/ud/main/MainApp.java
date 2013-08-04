package com.ud.main;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;
import javax.swing.SwingUtilities;
import javax.xml.stream.events.StartDocument;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.ud.database.services.BaseService;
import com.ud.database.services.ColumnService;
import com.ud.database.services.implement.ColumnServiceImpl;
import com.ud.gui.services.GUIServices;
import com.ud.gui.services.implement.GUIServiceImpl;

@Component
public class MainApp 
{
	private static Scanner cin= new Scanner(System.in);
   
	@Autowired
	private BaseService baseService;
	
	@Autowired 
	private GUIServices guiService;
	
	public static void main(String[] args ) throws SQLException
    {
		
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/application-context.xml");
       	MainApp mainApp = context.getBean(MainApp.class);
       	mainApp.start();
        
       cin.nextBigInteger();
    }
	
	@SuppressWarnings("rawtypes")
	private void start() throws SQLException{
		DSLContext dslContext = DSL.using(baseService.getDataSource().getConnection(), SQLDialect.POSTGRES);
		baseService.getPlatform().dropTables(baseService.getDatabase(), false);
		baseService.createPlatform();
		
		ColumnService columnService = new ColumnServiceImpl();

		List<Column> list = new ArrayList<Column>();
		list.add(columnService.createColumn("id", Types.BIGINT, true, true));
		list.add(columnService.createColumn("name", Types.VARCHAR));

		Table table = new Table();
		table.addColumns(list);
		table.setName("new_table");

		baseService.getDatabase().addTable(table);
		baseService.getPlatform().createTables(baseService.getDatabase(),
				false, true);

		DynaBean dynaBean = baseService.getDatabase().createDynaBeanFor(table);
		dynaBean.set(table.getColumn(1).getName(), "vasa");
		baseService.getPlatform().insert(baseService.getDatabase(), dynaBean);
		System.out.println(dslContext.select().from(table.getName()).getSQL());
		Iterator iterator = baseService.getPlatform().query(
				baseService.getDatabase(),
				dslContext.select().from(table.getName()).getSQL());
		while (iterator.hasNext()) {
			DynaBean bean = (DynaBean) iterator.next();
			System.out.println(bean.get(table.getColumn(1).getName()));
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
		        guiService.showFrame();
			}
		});
//     System.out.print();
	}
}
