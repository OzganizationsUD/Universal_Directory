package com.ud.gui.services.implement;

import javax.swing.JList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.TableService;
import com.ud.gui.services.GUIServices;
import com.ud.gui.services.ListService;

@Service
public class ListServiceImpl implements ListService {
	
	@Autowired
	private TableService tableService;
	
	@Autowired 
	private GUIServices guiServices;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  JList createListComponent() {
		String[] elem = {"                                                 "}; 
		JList jList = new JList(elem);
		jList.setLayoutOrientation(JList.VERTICAL);
		return jList;		
	}
	
	@SuppressWarnings("unchecked")
	public void setListData(Object[] listData){
		guiServices.getJlist().setListData(listData);
	}
}
