package com.nealxyc.beanfactory.reflect;

import net.sf.cglib.beans.BeanGenerator;

public class AttributeDescriptorFactory {
	
	public AttributeDescriptorFactory(String name, Class<?> type){
		BeanGenerator gen = new BeanGenerator();
		gen.addProperty(name, type);
//		gen.
	}
	
}
