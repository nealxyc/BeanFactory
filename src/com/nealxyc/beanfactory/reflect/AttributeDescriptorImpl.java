package com.nealxyc.beanfactory.reflect;

public class AttributeDescriptorImpl 
			implements AttributeDescriptor
{
	public final Class<?> type;
	public final String name ;
	
	public AttributeDescriptorImpl(String name, Class<?> type){
		this.name = name ;
		this.type = type ;
	}
	
	public Class<?> getType() {
	    return type;
	}

	public String getName() {
	    return name;
	}
	
	public static AttributeDescriptorImpl fromGetter(Method getter){
	    
	}
	
}
