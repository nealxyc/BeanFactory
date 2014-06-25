package com.nealxyc.beanfactory;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import org.junit.Test;

public class CompositTypeTest {

    BeanFactory factory = new BeanFactory();
    
    public static interface I1{
	public String getName();
	public void setName(String name);
	public String toString();
	public String toStringName();
    }
    
    public static interface I2 extends I1{
	public String getName2();
	public void setName2(String name);
    }
    
    public static interface I3 extends I2{
	public String getName3();
	public void setName3(String name);
	public String toString();
	public String toStringName();
    }
    
    public static abstract class I1Impl implements I1{
	
	public String toString(){
	    return "I1.toString()";
	}
	
	public String toStringName(){
	    return "I1.toStringName()";
	}
    }
    
    public static class Impl extends I1Impl implements I3{

	
	public String toStringName(){
	    return "I3.toStringName()";
	}

	@Override
	public String getName2() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public void setName2(String name) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public String getName() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public void setName(String name) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public String getName3() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public void setName3(String name) {
	    // TODO Auto-generated method stub
	    
	}
	
    }
    
    @Test
    public void testCallToString(){
	I3 i = new Impl();
	assertEquals("I1.toString()", i.toString());
	assertEquals("I3.toStringName()", i.toStringName());
    }
    
    @Test
    public void testCreateCompositeType(){
	I1 i = factory.newJavassistInstance(I1.class, I1Impl.class);
	assertNotNull(i);
	
	i.setName("my name");
	assertEquals("my name", i.getName());
	
	assertEquals("I1.toStringName()", i.toStringName());
	assertEquals("I1.toString()", i.toString());
	
	assertTrue(I1.class.isAssignableFrom(i.getClass()));
	assertNotEquals(I1.class, i.getClass());
	println(i.getClass());
    }
    
    @Test
    public void testI3I1Impl(){
	I3 i = factory.newJavassistInstance(I3.class, I1Impl.class);
	assertNotNull(i);
	
	i.setName("my name");
	assertEquals("my name", i.getName());
	
	i.setName2("my name 2");
	assertEquals("my name 2", i.getName2());
	
	i.setName3("my name 3");
	assertEquals("my name 3", i.getName3());
	
	assertEquals("I1.toStringName()", i.toStringName());
	assertEquals("I1.toString()", i.toString());
	
	assertTrue(I1.class.isAssignableFrom(i.getClass()));
	assertNotEquals(I1.class, i.getClass());
	println(i.getClass());
    }
    
    @Test
    public void testI3Impl(){
	I3 i = factory.newJavassistInstance(I3.class, Impl.class);
	assertNotNull(i);
	
	i.setName("my name");
	assertNull(i.getName());
	
	i.setName2("my name 2");
	assertNull( i.getName2());
	
	i.setName3("my name 3");
	assertNull( i.getName3());
	
	assertEquals("I3.toStringName()", i.toStringName());
	assertEquals("I1.toString()", i.toString());
	
	assertTrue(I1.class.isAssignableFrom(i.getClass()));
	assertNotEquals(I1.class, i.getClass());
	println(i.getClass());
    }
    
    @Test
    public void testI1ImplHasDefaultConstructorJavassist() throws NotFoundException{
	CtClass cls = ClassPool.getDefault().get(I1Impl.class.getName());
	CtConstructor[] constructors = cls.getConstructors();
	assertNotNull(constructors);
	assertEquals(1, constructors.length);
    }
    
    @Test
    public void testI1ImplHasDefaultConstructor() throws NotFoundException{
	Constructor[] constructors = I1Impl.class.getConstructors();
	assertNotNull(constructors);
	assertEquals(1, constructors.length);
	assertEquals(0, constructors[0].getTypeParameters().length);
    }
    
    public void println(Object o){
	System.out.println(o);
    }
}
