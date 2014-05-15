package com.nealxyc.beanfactory.reflect;

import java.lang.reflect.Method;

public class AttributeGetterSetter {

    public AttributeDescriptor attrDesp ;
    private Method getter ;
    private Method setter ;
    
    public AttributeGetterSetter(Method getter){
	this.getter = getter ;
	attrDesp = new AttributeDescriptorImpl(getter.getName())
    }
    
    public static String getAttributeName(String getterMethodName){
	
    }
}
