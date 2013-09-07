package com.ud.gui.enums;

public enum Prefixes {
	
	LOOCKUP("loock_up_");
	
	private String label;
	
	private Prefixes(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}	
}
