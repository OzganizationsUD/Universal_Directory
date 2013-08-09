package com.ud.gui.services.implement;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.stereotype.Service;

import com.ud.gui.enums.NamePane;
import com.ud.gui.services.DataPanelService;

@Service
public class DataPaneServiceImpl implements DataPanelService {
	
	private JPanel jPanel;
	
	private JTable jTable;
	
	public JPanel createDataTablePanel() {
		jPanel = new JPanel();
		jTable = new JTable();
		jPanel.add(new JScrollPane(jTable));
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
		jPanel.setName(NamePane.DEMONSTRATOR.toString());
		return jPanel;
	}
	
	public JTable getjTable() {
		return jTable;
	}
}
