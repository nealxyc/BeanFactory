package com.nealxyc.beanfactory;

import static org.junit.Assert.*;

import org.junit.Test;

public class CompositTypeTest {

    BeanFactory factory = new BeanFactory();
    
    public interface I1{
	public String getName();
	public void setName(String name);
	public String toString();
	public String toStringName();
    }
    
    public interface I2 extends I1{
	public String getName2();
	public void setName2(String name);
    }
    
    public interface I3 extends I2{
	public String getName3();
	public void setName3(String name);
	public String toString();
	public String toStringName();
    }
    
    public abstract class I1Impl implements I1{
	
	public String toString(){
	    return "I1.toString()";
	}
	
	public String toStringName(){
	    return "I1.toStringName()";
	}
    }
    
    public class Impl extends I1Impl implements I3{

	
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
	
    }
}
