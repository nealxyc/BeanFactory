package com.nealxyc.beanfactory.valueholder;

import com.nealxyc.beanfactory.reflect.AttributeDescriptor;

public class IndexedBeanAttribute extends ValueHolder{
	private final String name ;
	private int index ;
	
	public IndexedBeanAttribute(AttributeDescriptor desp){
		super(null, desp.getType());
		this.name = desp.getName() ;
	}

	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
