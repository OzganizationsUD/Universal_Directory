package com.ud.gui.services.implement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.model.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.ColumnService;
import com.ud.database.services.TableService;
import com.ud.gui.enums.NamePane;
import com.ud.gui.enums.TypesForCreator;
import com.ud.gui.services.CreatorPanelService;
import com.ud.gui.services.GUIServices;
import com.ud.gui.services.ListService;

@Service
public class CreatorPaneServiceImpl implements CreatorPanelService {
	
	private JPanel jPanel;
	
	private JTable tableCtreator;
	
	private JTextField jTextField;

	@Autowired
	private GUIServices guiServices;  
	
	@Autowired
	private ColumnService columnService;
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private ListService listService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JPanel createCrereatorPane() {
		jPanel =  new JPanel(new BorderLayout());
		
				
		Object[][] data = new Object[30][2];
		for (int i = 0; i < 30; i++) {
			data[i][0] = "";
			data[i][1] = null;
		}
		tableCtreator = new JTable(data,getColumnName());
		tableCtreator.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(getItemsComboBoxType())));
		JScrollPane jScrollPane = new JScrollPane(tableCtreator);
		
		JPanel panelHeader = new JPanel(new BorderLayout());
		JLabel jLabel = new JLabel("Имя справочника:  ");
		
		jTextField = new JTextField("table1");
		panelHeader.add(jLabel, BorderLayout.LINE_START);
		panelHeader.add(jTextField, BorderLayout.CENTER);
		
		JButton buttonSave = new JButton("Создать");
		buttonSave.addActionListener(onClickButtonSave());
		
		jPanel.add(panelHeader, BorderLayout.PAGE_START);
		jPanel.add(jScrollPane, BorderLayout.CENTER);
		jPanel.add(buttonSave, BorderLayout.PAGE_END);
		
		jPanel.setName(NamePane.CREATOR.toString());
		guiServices.getSplitPane().setRightComponent(jPanel);
		guiServices.getTableHeader().setText("Создание справочника");
		return jPanel;
	}
	
	private ActionListener onClickButtonSave(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Column> columns = new ArrayList<Column>();
				for (int i = 0;i<tableCtreator.getRowCount();i++){
					Object name = tableCtreator.getValueAt(i, 0);
					if (name=="")
						break;
					String type = tableCtreator.getValueAt(i, 1).toString();
					TypesForCreator typesForCreator = TypesForCreator.valueOfDesc(type);
					Column column = columnService.createColumn(name.toString(), typesForCreator.getTypeSql());
					columns.add(column);
				}
				String tableName = jTextField.getText();
				try {
					if ((tableName!=null)&&(tableName!="")&&(!columns.isEmpty())){
						tableService.createTable(tableName,columns);	
						listService.setListData(tableService.getAllTableName().toArray());
					}
				} catch (DatabaseOperationException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		};
	}
	
	private String[] getColumnName(){
		String[] nameColumn = {
				"Имя поля",
				"Тип даных"
			};
		return nameColumn;
	}
	
	private String[] getItemsComboBoxType(){
		String [] itemComboBox = new String[TypesForCreator.values().length];
		for (int i = 0; i < itemComboBox.length; i++) {
			itemComboBox[i] = TypesForCreator.values()[i].getDescription();
		}
		return itemComboBox;
	}

	public JPanel getjPanel() {
		return jPanel;
	}

	public JTable getjTable() {
		return tableCtreator;
	}
}
