package com.ud.gui.services.implement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.TableService;
import com.ud.gui.enums.NamePane;
import com.ud.gui.services.DataPanelService;
import com.ud.gui.services.JTableService;

@Service
public class DataPaneServiceImpl implements DataPanelService {
	
	private JPanel jPanel;
	
	private JTable jTable;
	
	private JButton buttonSave;
	
	private JScrollPane jScrollPane;
	
	private int rowCount=-1;
	
	@Autowired
	private JTableService jTableService;
	
	@Autowired
	private TableService tableService;
	
	public JPanel createDataTablePanel() {
		jPanel = new JPanel(new BorderLayout());
		jPanel.setName(NamePane.DEMONSTRATOR.toString());
		
		jTable = new JTable();
		jScrollPane = new JScrollPane(jTable);
		jScrollPane.setName(NamePane.SCROLL_DEMONSTRATOR.toString());
		jPanel.add(jScrollPane, BorderLayout.CENTER);
		
		JPanel panelPageStart = new JPanel(new BorderLayout());
		
		JButton buttonAddRow = new JButton("Добавить запись");
		buttonAddRow.addActionListener(onClickButtonAdd());
		panelPageStart.add(buttonAddRow, BorderLayout.PAGE_START);
		
		buttonSave = new JButton("Сохранить");
		buttonSave.addActionListener(onClickButtonSave());
		buttonSave.setVisible(false);
		
		jPanel.add(buttonSave, BorderLayout.PAGE_END);
		jPanel.add(panelPageStart, BorderLayout.PAGE_START);
		return jPanel;
	}
	
	private ActionListener onClickButtonAdd(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowCount = jTable.getRowCount();
				Object[] dataRow = new Object[jTable.getColumnCount()];
				for (int i = 0; i < dataRow.length; i++)
					dataRow[i] = null;
				DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
				tableModel.addRow(dataRow);
				buttonSave.setVisible(true);
			}
		};
	}
	
	private ActionListener onClickButtonSave(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int countNewRecord = jTable.getRowCount()-rowCount;
				
				if (countNewRecord<=0)
					return;
				Object[][] data = new Object[countNewRecord][jTable.getColumnCount()]; 
				for (int i = 0; i < countNewRecord; i++) 
					for (int j = 0; j < jTable.getColumnCount(); j++) {
						data[i][j] = jTable.getValueAt(i+rowCount, j);
					}
				tableService.insert(jTable.getName(), data);
				setjTable(jTableService.createTable(jTable.getName()));
				buttonSave.setVisible(false);
			}
		};
	}
	
	public JTable getjTable() {
		return jTable;
	}

	public void setjTable(JTable jTable) {
		this.jTable = jTable;
        jPanel.remove(jScrollPane);
        jScrollPane = new JScrollPane(jTable);
		jPanel.add(jScrollPane, BorderLayout.CENTER);
	}

	public JPanel getjPanel() {
		return jPanel;
	}
}
