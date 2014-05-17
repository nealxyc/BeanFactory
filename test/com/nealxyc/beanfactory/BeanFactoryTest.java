package com.nealxyc.beanfactory;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeanFactoryTest {

	public static interface MyInterface{
		public void setName(String name);
		public String getName();
		public void setNumber(int name);
		public int getNumber();
	}
	
	private BeanFactory factory = new BeanFactory();
	
	@Test
	public void testNewInstance(){
		MyInterface mi = factory.newInstance(MyInterface.class);
		assertNotNull(mi);
		assertNull(mi.getName());
		mi.setName("my name");
		assertEquals("my name", mi.getName());
		
		assertEquals(0, mi.getNumber());
		mi.setNumber(12);
		assertEquals(12, mi.getNumber());
	}
	
	@Test
	public void testTwoInstanceDontIntervene(){
		MyInterface mi = factory.newInstance(MyInterface.class);
		MyInterface mi2 = factory.newInstance(MyInterface.class);
		assertNotNull(mi);
		assertNotNull(mi2);
		assertNull(mi.getName());
		mi.setName("my name");
		assertEquals("my name", mi.getName());
		
		assertNull(mi2.getName());
		mi2.setName("an other name");
		assertEquals("an other name", mi2.getName());
		assertEquals("my name", mi.getName());
	}
}
