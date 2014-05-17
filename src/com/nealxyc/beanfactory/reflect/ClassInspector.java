package com.nealxyc.beanfactory.reflect;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ClassInspector {
    
	private final Class<?> cls ;
//	private List<Method> methods ;
	private List<AttributeGetterSetter> attributes = Lists.newArrayList();
//	private Set<AttributeDescriptor> readOnlyAttributes = Sets.newHashSet();
	
	//private
	private ClassInspector(Class<?> cls){
	    this.cls = cls ;
	}
	
	public List<AttributeGetterSetter> getAttributeGetterSetterList() {
	    return ImmutableList.copyOf(attributes);
	}
	
	public static ClassInspector readClass(Class<?> cls){
	    ClassInspector ci = new ClassInspector(cls);
	    List<AttributeGetterSetter> attrGetterSetters = AttributeGetterSetterImpl.getFromMethods(cls.getDeclaredMethods());
	    ci.attributes.addAll(attrGetterSetters);
	    return ci ;
	}
	
	
}
