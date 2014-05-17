package com.nealxyc.beanfactory;


import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SimpleFactoryTest {

	public static interface MyInterface{
		public void setName(String name);
		public String getName();
	}
	@Ignore
	@Test
	public void testImplement() throws InstantiationException, IllegalAccessException{
		Class<? extends MyInterface> cls = SimpleFactory.implementInterface(MyInterface.class);
		Assert.assertNotNull(cls);
		
		MyInterface mi = cls.newInstance();
		Assert.assertNotNull(mi);
		Assert.assertNull(mi.getName());
		mi.setName(null);
	}
	
	@Ignore
	@Test
	public void testCreateClass() throws InstantiationException, IllegalAccessException{
		Class<? extends MyInterface> cls  = SimpleFactory.createClass(MyInterface.class);
		Assert.assertNotNull(cls);
		MyInterface mi = SimpleFactory.newInstance(cls);
		assertNotNull(mi);
		assertNull(mi.getName());
		mi.setName(null);
	}
	

	@Test
	public void testCreate() throws InstantiationException, IllegalAccessException{
		MyInterface mi  = SimpleFactory.create(MyInterface.class);
		Assert.assertNotNull(mi);
		Assert.assertNull(mi.getName());
		mi.setName(null);
	}
	
	@Ignore
	@Test
	public void testCreateClassFromBeanGenerator() throws InstantiationException, IllegalAccessException{
		Class<? extends MyInterface> cls  = SimpleFactory.createClassWithBeanGenerator(MyInterface.class);
		MyInterface mi = cls.newInstance();
		Assert.assertNotNull(mi);
		Assert.assertNull(mi.getName());
		mi.setName("myName");
		Assert.assertEquals("myName",mi.getName());
	}
	
	@Ignore
	@Test
	public void testCreateFromBeanGenerator() throws InstantiationException, IllegalAccessException{
		MyInterface mi = SimpleFactory.createWithBeanGenerator(MyInterface.class);
		Assert.assertNotNull(mi);
		Assert.assertNull(mi.getName());
		mi.setName("myName");
		Assert.assertEquals("myName",mi.getName());
	}
	
}
