package com.ud.database.services;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ud.database.connect.DataBaseConnect;

public class BaseService {
	@Autowired
	private DataBaseConnect baseConnect;
	
	public void work(){
		Platform platform = PlatformFactory.createNewPlatformInstance(baseConnect.getDs());
		
	}
}
