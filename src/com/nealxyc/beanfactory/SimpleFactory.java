package com.nealxyc.beanfactory;

import java.lang.reflect.Method;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class SimpleFactory {

	public static class DoNothing implements MethodInterceptor{

		@Override
		public Object intercept(Object obj, Method method, Object[] args,
				MethodProxy proxy) throws Throwable {
			return null;
		}
		
	}
	public static <T> Class<? extends T> implementInterface(Class<T> cls){
		Enhancer eh = new Enhancer();
//		eh.setSuperclass(cls);
		eh.setInterfaces(new Class[]{cls});
		eh.setUseFactory(false);
		eh.setCallbackType(DoNothing.class);
		return eh.createClass();
	}
	
	public static <T, O extends T> O  create(Class<T> cls){
		Enhancer eh = new Enhancer();
		eh.setSuperclass(cls);
//		eh.setInterfaces(new Class[]{cls});
		eh.setUseFactory(false);
		eh.setCallback(new DoNothing());
		return (O) eh.create(new Class[0], new Object[0]);
	}
	
	public static <T, O extends T> O  createWithBeanGenerator(Class<T> cls){
		BeanGenerator gen = new BeanGenerator() ;
		Enhancer eh = new Enhancer();
		eh.setSuperclass(cls);
//		eh.setInterfaces(new Class[]{cls});
		eh.setUseFactory(false);
		eh.setCallback(new DoNothing());
		return (O) eh.create(new Class[0], new Object[0]);
	}
}
