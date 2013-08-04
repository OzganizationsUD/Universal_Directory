package com.ud.gui.services.implement;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.gui.services.GUIServices;
import com.ud.gui.services.ListService;
import com.ud.gui.services.MainMenu;

@Service
public class GUIServiceImpl implements GUIServices {
	
	private int width=1024;
	private int height=600;
	private String title="Frame1";
	
	@Autowired
	private MainMenu mainMenu;
	
	@Autowired
	private ListService listService;
	
	private JFrame frame = new JFrame(title);
	private JLabel label = new JLabel("Wowa!!");
	@SuppressWarnings("rawtypes")
	private JList jlist;
	
	public void showFrame(){
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.LINE_START;

		JPanel panel = new JPanel();
		jlist = listService.createListComponent();
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jlist, new JPanel());

        panel.setLayout(new BorderLayout());
        panel.add(sp,BorderLayout.CENTER);
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
}
