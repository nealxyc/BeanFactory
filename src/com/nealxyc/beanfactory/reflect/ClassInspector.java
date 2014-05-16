package com.nealxyc.beanfactory.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.xml.internal.ws.util.StringUtils;

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
