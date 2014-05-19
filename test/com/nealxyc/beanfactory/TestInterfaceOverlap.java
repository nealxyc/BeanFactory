package com.nealxyc.beanfactory;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class TestInterfaceOverlap {

    BeanFactory factory = new BeanFactory();
    public static interface MyInterface1{
	 
	public String getName();
	public void doA();
    }
    
    public static interface MyInterface2{
	 
	public String getName();
	public void doB();
    }
    
    public static interface MyInterface3 extends MyInterface1, MyInterface2{
	public int getInt();
	public void setInt(int num);
    }
    
    @Test
    public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
	MyInterface3 mi = factory.newInstance(MyInterface3.class);
	
	mi.setInt(1);
	assertEquals(1, mi.getInt());
	assertEquals(1, MyInterface3.class.getMethod("getInt").invoke(mi, null));
	System.out.println(mi.getClass().getPackage().getName());
    }
}
