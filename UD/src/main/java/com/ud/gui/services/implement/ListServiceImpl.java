package com.ud.gui.services.implement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JList;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.TableService;
import com.ud.gui.services.GUIServices;
import com.ud.gui.services.JTableService;
import com.ud.gui.services.ListService;

@Service
public class ListServiceImpl implements ListService {
	
	@Autowired
	private TableService tableService;
	
	@Autowired 
	private GUIServices guiServices;
	
	@Autowired
	private JTableService jTableService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  JList createListComponent() {
		String[] elem = {"                                                 "}; 
		JList jList = new JList(elem);
		jList.setLayoutOrientation(JList.VERTICAL);
		jList.addMouseListener(onDoubleMouseClick());
		return jList;		
	}
	
	@SuppressWarnings("unchecked")
	public void setListData(Object[] listData){
		guiServices.getJlist().setListData(listData);
	}
	
	@SuppressWarnings({ "rawtypes" })
	private MouseAdapter onDoubleMouseClick(){
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				JList jlist = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            Table table = tableService.findTableByName((String)jlist.getSelectedValue());
		            List<DynaBean> list = tableService.getAllTableRecord(table.getName());
		            guiServices.setjTable(jTableService.createTable(table, list).getModel());
		            guiServices.getTableHeader().setText(guiServices.getTableCaption()+" \""+table.getName()+"\"");
		        }
			}
		};
	}
}
