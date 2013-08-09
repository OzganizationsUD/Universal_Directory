package com.ud.gui.services.implement;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.TableService;
import com.ud.gui.services.DataPanelService;
import com.ud.gui.services.GUIServices;
import com.ud.gui.services.JTableService;
import com.ud.gui.services.ListService;
import com.ud.gui.services.MainMenu;

@Service
public class GUIServiceImpl implements GUIServices {
	
	private int width=1024;
	private int height=600;
	private String title="Универсальный СПРАВОЧНИК v0.0.1";
	private String tableCaption = "Содержымое справочника";
	
	@Autowired
	private MainMenu mainMenu;
	
	@Autowired
	private ListService listService;
	
	@Autowired
	private JTableService jTableService; 
	
	@Autowired
	private DataPanelService dataTablePaneService;
	
	@Autowired
	private TableService tableService;
	
	private JFrame frame = new JFrame(title);
	private JLabel label = new JLabel("Wowa!!");
	@SuppressWarnings("rawtypes")
	private JList jlist;
	
	private JButton  tableHeader;
	private JSplitPane splitPane;
			
	public void showFrame(){
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jlist = listService.createListComponent();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jlist, dataTablePaneService.createDataTablePanel());
		
		JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(splitPane,BorderLayout.CENTER);
        
        JPanel panelStartPage = new JPanel();
        panelStartPage.setLayout(new BorderLayout());
        JButton  button = new JButton("Список справочников");
        panelStartPage.add(button, BorderLayout.LINE_START);
        
		tableHeader = new JButton(tableCaption);
        panelStartPage.add(tableHeader, BorderLayout.CENTER);
        panel.add(panelStartPage, BorderLayout.PAGE_START);
		frame.add(panel);
		frame.setJMenuBar(mainMenu.createMainMenu());
		frame.setVisible(true);
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setLabelText(String text) {
		label.setText(text);
	}

	@SuppressWarnings("rawtypes")
	public JList getJlist() {
		return jlist;
	}

	public JButton getTableHeader() {
		return tableHeader;
	}

	public String getTableCaption() {
		return tableCaption;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}
}
