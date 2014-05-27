package com.nealxyc.beanfactory.bytecode;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CtAttributeGetterSetterImpl implements CtAttributeGetterSetter {

    private CtAttributeDescriptor attrDesp;
    private CtMethod getter;
    private CtMethod setter;

    public CtAttributeGetterSetterImpl(CtAttributeDescriptor attrDesp, CtMethod getter,
	    CtMethod setter) {
	this.getter = getter;
	this.attrDesp = attrDesp;
	this.setter = setter;
    }

    @Override
    public CtAttributeDescriptor getDescriptor() {
	return attrDesp;
    }

    @Override
    public CtMethod getGetterMethod() {
	return getter;
    }

    @Override
    public CtMethod getSetterMethod() {
	return setter;
    }

    private static String getAttributeName(CtMethod CtMethod) {

	String name = CtMethod.getName();
	if (GETTER_PREDICATE.apply(CtMethod)) {
	    return name.startsWith("get") ? name.replaceFirst("get", "") : name
		    .replaceFirst("is", "");
	}
	if (SETTER_PREDICATE.apply(CtMethod)) {
	    return name.replaceFirst("set", "");
	}
	throw new IllegalStateException(String.format(
		"CtMethod %s.%s() is neither a getter nor a setter.", CtMethod
			.getDeclaringClass().getSimpleName(), name));
    }

    public static List<CtAttributeGetterSetter> getFromMethods(CtMethod[] CtMethods) {
	List<CtAttributeGetterSetter> ret = Lists.newArrayList();
	Map<String, CtMethod> setterMap = Maps.newHashMap();
	Collection<CtMethod> getters = getGetters(CtMethods);
	Collection<CtMethod> setters = getSetters(CtMethods);
	
	for(CtMethod m: setters){
	    setterMap.put(getAttributeName(m), m);
	}
	for(CtMethod m: getters){
	    String getterAttrName = getAttributeName(m);
	    CtMethod setter = setterMap.get(getterAttrName);
	    if(setter != null){
		try {
		    CtClass paramType = setter.getParameterTypes()[0];
		    CtClass returnType = m.getReturnType();
		  //Validate that the return type of getter is the same as the parameter type of setter
		    if((paramType.isPrimitive() && paramType.equals(returnType)) 
			   || paramType.subclassOf(returnType) && returnType.subclassOf(paramType)){
			    ret.add(new CtAttributeGetterSetterImpl(new CtAttributeDescriptorImpl(getterAttrName, returnType), m, setter));
		    }
		} catch (NotFoundException e) {
		    //do nothing
		}
		
	    }
	}
	return ret ;
    }

    public static Predicate<CtMethod> GETTER_PREDICATE = new Predicate<CtMethod>() {
	@Override
	public boolean apply(CtMethod item) {
	    String name = item.getName();
	    try {
		CtClass[] paramTypes = item.getParameterTypes() ;
		CtClass returnType = item.getReturnType() ;
		return name != null && (paramTypes == null || paramTypes.length == 0)
		    && Modifier.isAbstract(item.getModifiers())
		    && returnType != null
		    && (name.startsWith("get")
			    || (name.startsWith("is") 
				    && (CtClass.booleanType.equals(item.getReturnType()) 
					    || returnType.equals(returnType.getClassPool().getCtClass("java.lang.Boolean")))));
	    } catch (NotFoundException e) {
		return false ;
	    }
	}
    };

    public static Predicate<CtMethod> SETTER_PREDICATE = new Predicate<CtMethod>() {
	@Override
	public boolean apply(CtMethod item) {
	    String name = item.getName();
	    try {
		return name != null && (name.startsWith("set"))
		    && Modifier.isAbstract(item.getModifiers())
		    && item.getParameterTypes().length == 1;
	    } catch (NotFoundException e) {
		return false ;
	    }
	}
    };

    private static Collection<CtMethod> getGetters(CtMethod[] CtMethods) {
	return Collections2.filter(Arrays.asList(CtMethods), GETTER_PREDICATE);
    }

    private static Collection<CtMethod> getSetters(CtMethod[] CtMethods) {
	return Collections2.filter(Arrays.asList(CtMethods), SETTER_PREDICATE);
    }

	@Override
	public boolean hasMethod(CtMethod CtMethod) {
		return isGetter(CtMethod) || isSetter(CtMethod);
	}

	@Override
	public boolean isGetter(CtMethod CtMethod) {
		return getter.equals(CtMethod);
	}

	@Override
	public boolean isSetter(CtMethod CtMethod) {
		return setter.equals(CtMethod);
	}
    
}
