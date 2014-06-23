package com.nealxyc.beanfactory.bytecode;

import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.Modifier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class CtClassInspector {
    
    	static final String COMPOSITE_STUB = "$Composite" ;
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
	
	public static CtClassInspector readCompositeClass(CtClass cls, CtClass partial){
	    if(!cls.isInterface()){
		throw new IllegalStateException(String.format("%s should be an interface.", cls.getName()));
	    }
	    
	    if(partial.isInterface()){
		throw new IllegalStateException(String.format("%s should not be an interface.", partial.getName()));
	    }
	    CtClass comp = cls.getClassPool().makeClass(cls.getName() + COMPOSITE_STUB);
	    comp.setModifiers(comp.getModifiers() + Modifier.ABSTRACT);
	    comp.setInterfaces(new CtClass[]{cls});
	    try {
		comp.setSuperclass(partial);
	    } catch (CannotCompileException e) {
		e.printStackTrace();
	    }
	    
	    CtClassInspector ci = new CtClassInspector(comp);
	    List<CtAttributeGetterSetter> attrGetterSetters = CtAttributeGetterSetterImpl.getFromMethods(cls.getMethods());
	    ci.attributes.addAll(attrGetterSetters);
	    return ci ;
	}
	
	
}
