package com.ud.test.gui.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ud.gui.services.JTableService;
import com.ud.test.PersistenceTestJUnit;

public class JTableServiceTest extends PersistenceTestJUnit {
	
	
	@Autowired
	private JTableService jTableService;
	
	@Test
	public void testGetIds() {
		jTableService.createTable("new_table");
		Long[] ids = jTableService.getIds();
		assertNotNull(ids);
		assertEquals(10, ids.length);
	}
}
