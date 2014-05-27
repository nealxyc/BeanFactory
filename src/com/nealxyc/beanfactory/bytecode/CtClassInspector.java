package com.nealxyc.beanfactory.bytecode;

import java.util.List;

import javassist.CtClass;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class CtClassInspector {
    
	private final CtClass cls ;
	
	public CtClass getCls() {
	    return cls;
	}

	private List<CtAttributeGetterSetter> attributes = Lists.newArrayList();
	private CtClassInspector(CtClass cls){
	    this.cls = cls ;
	}
	
	public List<CtAttributeGetterSetter> getAttributeGetterSetterList() {
	    return ImmutableList.copyOf(attributes);
	}
	
	public static CtClassInspector readClass(CtClass cls){
	    CtClassInspector ci = new CtClassInspector(cls);
	    List<CtAttributeGetterSetter> attrGetterSetters = CtAttributeGetterSetterImpl.getFromMethods(cls.getMethods());
	    ci.attributes.addAll(attrGetterSetters);
	    return ci ;
	}
	
	
}
