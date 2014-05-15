package com.nealxyc.beanfactory.reflect;

public class AttributeDescriptorImpl {
	public final Class<?> type;
	public final String name ;
	
	public AttributeDescriptor(String name, Class<?> type){
		this.name = name ;
		this.type = type ;
	}
	
}
