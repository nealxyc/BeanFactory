package com.nealxyc.beanfactory.bytecode;

import javassist.CtClass;

public class CtAttributeDescriptorImpl 
			implements CtAttributeDescriptor
{
	public final CtClass type;
	public final String name ;
	
	public CtAttributeDescriptorImpl(String name, CtClass type){
		this.name = name ;
		this.type = type ;
	}
	
	public CtClass getType() {
	    return type;
	}

	public String getName() {
	    return name;
	}
	

}
