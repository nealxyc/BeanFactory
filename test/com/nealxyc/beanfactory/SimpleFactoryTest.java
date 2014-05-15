package com.nealxyc.beanfactory;


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
	
	@Test
	public void testCreate() throws InstantiationException, IllegalAccessException{
		MyInterface mi  = SimpleFactory.create(MyInterface.class);
		Assert.assertNotNull(mi);
		Assert.assertNull(mi.getName());
		mi.setName(null);
	}
	
}
