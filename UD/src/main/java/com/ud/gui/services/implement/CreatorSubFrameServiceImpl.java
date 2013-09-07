package com.ud.gui.services.implement;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.database.services.ColumnService;
import com.ud.database.services.TableService;
import com.ud.gui.services.CreatorPanelService;
import com.ud.gui.services.CreatorSubFrameService;
import com.ud.gui.services.GUIServices;

@Service
public class CreatorSubFrameServiceImpl implements CreatorSubFrameService {
	
	private JFrame jFrame;
	
	private JComboBox comboBoxColumn;
	
	private String tableNameSelected;
	
	private String columnNameSelected;
	
	List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private GUIServices guiServices;
	
	@Autowired
	private CreatorPanelService creatorPanelService;
	
	@Autowired
	private ColumnService columnService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JFrame createFrame() {
		jFrame = new JFrame("Выбор подсправочника");
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		List<String> allTableName = tableService.getAllTableName();
		allTableName.add(0, "");
		Object[] items = allTableName.toArray();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label1 = new JLabel("Имя справочника: "); 
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label1,c);
		
		JLabel label2 = new JLabel("Подстановочное поле: "); 
		c.gridx = 0;
		c.gridy = 1;
		panel.add(label2,c);
		
		JComboBox comboBoxTable = new JComboBox(items);
		c.ipadx = 100;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth=2;
		comboBoxTable.addItemListener(onChangeComboBoxTable());
		panel.add(comboBoxTable,c);
		
		comboBoxColumn = new JComboBox();		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth=2;
		panel.add(comboBoxColumn,c);
		
		JButton buttonOk = new JButton("OK");
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 0;
		c.insets = new Insets(10, 0, 10, 0);
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=1;
		buttonOk.addActionListener(onClickButtonOk());
		panel.add(buttonOk,c);
		
		JButton buttonClose = new JButton("Close");
		c.gridx=1;
		c.gridy=2;
		buttonClose.addActionListener(onClickButtonClose());		
		panel.add(buttonClose,c);
		
		jFrame.add(panel);
		int x = guiServices.getFrame().getX()+guiServices.getFrame().getWidth()/2;
		int y = guiServices.getFrame().getY()+guiServices.getFrame().getHeight()/2;
		
		jFrame.setBounds(x, y, 250, 150);
		
		jFrame.setVisible(true);
		jFrame.pack();
		return jFrame;
	}
	
	private ItemListener onChangeComboBoxTable(){
		return new ItemListener() {
			
			@SuppressWarnings("unchecked")
			public void itemStateChanged(ItemEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox comboBox = (JComboBox)e.getSource();
				tableNameSelected = (String)comboBox.getSelectedItem();
				comboBoxColumn.removeAllItems();
				if ((tableNameSelected != null)&&(tableNameSelected != "")){
					Table table = tableService.findTableByName(tableNameSelected);
					Column[] columns = table.getColumns();
					comboBoxColumn.addItem("");
					for (int i = 0; i < columns.length; i++)
						comboBoxColumn.addItem(columns[i].getName());
				}
			}
		}; 
	}
	
	private ActionListener onClickButtonOk(){
		return new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (comboBoxColumn.getItemCount()>0){
					columnNameSelected = (String)comboBoxColumn.getSelectedItem();
					if((tableNameSelected!=null)&&(tableNameSelected!="")&&(columnNameSelected!=null)&&(columnNameSelected!="")){
						Integer indexRow = creatorPanelService.getjTable().getSelectedRow();
						ForeignKey foreignKey = columnService.createForeignKey("foreing_"+tableNameSelected, tableNameSelected, (String)creatorPanelService.getjTable().getValueAt(indexRow, 0));
						foreignKeys.add(foreignKey);
					}
					jFrame.dispose();
				}
			}
		};
	}
	
	private ActionListener onClickButtonClose(){
		return new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jFrame.dispose();
			}
		};
	}
	
	public String getTableNameSelected() {
		return tableNameSelected;
	}

	public String getColumnNameSelected() {
		return columnNameSelected;
	}

	public List<ForeignKey> getForeignKeys() {
		return foreignKeys.isEmpty() ? null:foreignKeys;
	} 
}
