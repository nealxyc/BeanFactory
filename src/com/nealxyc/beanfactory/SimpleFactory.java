package com.nealxyc.beanfactory;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.Maps;
import com.nealxyc.beanfactory.reflect.AttributeGetterSetter;
import com.nealxyc.beanfactory.reflect.ClassInspector;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
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
	
	private static Map<Class<?>, Enhancer> enhancerMap = Maps.newHashMap();
	
	public static class DoNothing2 implements InvocationHandler{

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			String a = "a";
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
	
	public static <T, O extends T> Class<O>  createClass(Class<T> cls){
		if(Enhancer.isEnhanced(cls) && enhancerMap.containsKey(cls)){
			return enhancerMap.get(cls).createClass();
		}
		Enhancer eh = new Enhancer();
		eh.setSuperclass(cls);
		eh.setUseFactory(false);
		eh.setCallbackTypes(new Class<?>[]{DoNothing2.class});
		enhancerMap.put(cls, eh);
		return eh.createClass();
	}
	
	public static <T> Class<? extends T> createClassWithBeanGenerator(Class<T> cls){
		BeanGenerator gen = new BeanGenerator() ;
		
		ClassInspector ci = ClassInspector.readClass(cls);
		for(AttributeGetterSetter attr: ci.getAttributeGetterSetterList()){
		    gen.addProperty(attr.getDescriptor().getName(), attr.getDescriptor().getType());
		}
		gen.setSuperclass(cls);
//		Enhancer eh = new Enhancer();
//		eh.setSuperclass(cls);
////		eh.setInterfaces(new Class[]{cls});
//		eh.setUseFactory(false);
//		eh.setCallback(new DoNothing());
		return (Class<? extends T>) gen.createClass();
	}
	
	public static <T> T createWithBeanGenerator(Class<T> cls){
		BeanGenerator gen = new BeanGenerator() ;
		gen.setSuperclass(cls);
		ClassInspector ci = ClassInspector.readClass(cls);
		for(AttributeGetterSetter attr: ci.getAttributeGetterSetterList()){
		    gen.addProperty(attr.getDescriptor().getName(), attr.getDescriptor().getType());
		}
		
		return (T) gen.create();
	}
	
	public static <T> T newInstance(Class<T> cls){
		if(!Enhancer.isEnhanced(cls) || !enhancerMap.containsKey(cls)){
			createClass(cls);
		}
		Enhancer eh = enhancerMap.get(cls);
		eh.setCallback(new DoNothing2());
		return (T)eh.create();
	}
	
	
}
