package com.nealxyc.beanfactory;

import java.lang.reflect.Method;

import org.junit.Test;

import static org.junit.Assert.* ;

public class EquilityTest {

    @Test
    public void testClassIdentityEquility() throws ClassNotFoundException{
	Class<?> cls = PerformanceTest.class ;
	Class<?> cls2 = Class.forName("com.nealxyc.beanfactory.PerformanceTest");
	assertEquals(cls, cls2);
	assertSame(cls, cls2);
	assertTrue(cls == cls2);
    }
    
    @Test
    public void testMethodIdentityEquility() throws ClassNotFoundException, NoSuchMethodException, SecurityException{
	Method m = PerformanceTest.class.getMethod("setup", null) ;
	Method m2 = null ; 
	Method[] methods = PerformanceTest.class.getMethods() ;
	for(Method method: methods){
	    if(method.getName().equals("setup") && method.getParameterTypes().length == 0){
		m2 = method ;
		break ;
	    }
	}
	assertEquals(m, m2);
	assertNotSame(m, m2);
	assertFalse(m == m2);
    }
}
