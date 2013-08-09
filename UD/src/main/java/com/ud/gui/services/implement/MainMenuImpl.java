package com.ud.gui.services.implement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.TableService;
import com.ud.gui.services.CreatorPanelService;
import com.ud.gui.services.GUIServices;
import com.ud.gui.services.ListService;
import com.ud.gui.services.MainMenu;

@Service
public class MainMenuImpl implements MainMenu {
	
	@Autowired
	private GUIServices guiServices;
	
	@Autowired
	private ListService listService;
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private CreatorPanelService creatorPanelService;
	
	public MainMenuImpl(){
		
	}
	
	public MainMenuImpl(GUIServices guiServices){
		this.guiServices = guiServices;
	}
	
	public JMenuBar createMainMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuS = new JMenu("Справочник");
		JMenu menuInfo = new JMenu("О Програме");
		
		JMenuItem itemCreate = new JMenuItem("Создать");
		itemCreate.addActionListener(onClickItemCreate());
		menuS.add(itemCreate);
		
		JMenuItem itemShowDirectory = new JMenuItem("Показать все справочники");
		itemShowDirectory.addActionListener(onClickShowDirectory());
		menuS.add(itemShowDirectory);
		
		menuBar.add(menuS);
		menuBar.add(menuInfo);
		return menuBar;
	}
	
	private ActionListener onClickItemCreate(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creatorPanelService.createCrereatorPane();
			}
		};
	}
	
	private ActionListener onClickShowDirectory(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listService.setListData(tableService.getAllTableName().toArray());
			}
		};
	}
}
