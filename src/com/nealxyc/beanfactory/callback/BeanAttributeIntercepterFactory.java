package com.nealxyc.beanfactory.callback;

import com.nealxyc.beanfactory.reflect.ClassInspector;

public class BeanAttributeIntercepterFactory {
	private final ClassInspector classInspector ;
	
	public BeanAttributeIntercepterFactory(ClassInspector ci){
		this.classInspector = ci;
	}
	
	public BeanAttributeIntercepter create(){
		return new BeanAttributeIntercepter(this.classInspector);
	}
	
	public BeanAttributeIntercepter createOptimized(){
		return new OptimizedBeanAttributeIntercepter(this.classInspector);
	}
	
	public static BeanAttributeIntercepter createIntercepter(ClassInspector ci){
		return new BeanAttributeIntercepter(ci);
	}
		
}
