package com.nealxyc.beanfactory.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AttributeGetterSetterImpl implements AttributeGetterSetter {

    private AttributeDescriptor attrDesp;
    private Method getter;
    private Method setter;

    public AttributeGetterSetterImpl(AttributeDescriptor attrDesp, Method getter,
	    Method setter) {
	this.getter = getter;
	this.attrDesp = attrDesp;
	this.setter = setter;
    }

    @Override
    public AttributeDescriptor getDescriptor() {
	return attrDesp;
    }

    @Override
    public Method getGetterMethod() {
	return getter;
    }

    @Override
    public Method getSetterMethod() {
	return setter;
    }

    private static String getAttributeName(Method method) {

	String name = method.getName();
	if (GETTER_PREDICATE.apply(method)) {
	    return name.startsWith("get") ? name.replaceFirst("get", "") : name
		    .replaceFirst("is", "");
	}
	if (SETTER_PREDICATE.apply(method)) {
	    return name.replaceFirst("set", "");
	}
	throw new IllegalStateException(String.format(
		"Method %s.%s() is neither a getter nor a setter.", method
			.getDeclaringClass().getSimpleName(), name));
    }

    public static List<AttributeGetterSetter> getFromMethods(Method[] methods) {
	List<AttributeGetterSetter> ret = Lists.newArrayList();
	Map<String, Method> setterMap = Maps.newHashMap();
	Collection<Method> getters = getGetters(methods);
	Collection<Method> setters = getSetters(methods);
	
	for(Method m: setters){
	    setterMap.put(getAttributeName(m), m);
	}
	for(Method m: getters){
	    String getterAttrName = getAttributeName(m);
	    Method setter = setterMap.get(getterAttrName);
	    if(setter != null){
		//Validate that the return type of getter is the same as the parameter type of setter
		Class<?> paramType = setter.getParameterTypes()[0];
		Class<?> returnType = m.getReturnType();
		if(paramType.isAssignableFrom(returnType) && returnType.isAssignableFrom(paramType)){
		    ret.add(new AttributeGetterSetterImpl(new AttributeDescriptorImpl(getterAttrName, returnType), m, setter));
		}
	    }
	}
	return ret ;
    }

    public static Predicate<Method> GETTER_PREDICATE = new Predicate<Method>() {
	@Override
	public boolean apply(Method item) {
	    String name = item.getName();
	    return name != null && item.getParameterTypes().length == 0
		    && Modifier.isAbstract(item.getModifiers())
		    && ((name.startsWith("get") && item.getReturnType() != null)
			    || (name.startsWith("is") && Boolean.class.isAssignableFrom(item.getReturnType())));
	}
    };

    public static Predicate<Method> SETTER_PREDICATE = new Predicate<Method>() {
	@Override
	public boolean apply(Method item) {
	    String name = item.getName();
	    return name != null && (name.startsWith("set"))
		    && Modifier.isAbstract(item.getModifiers())
		    && item.getParameterTypes().length == 1;
	}
    };

    private static Collection<Method> getGetters(Method[] methods) {
	return Collections2.filter(Arrays.asList(methods), GETTER_PREDICATE);
    }

    private static Collection<Method> getSetters(Method[] methods) {
	return Collections2.filter(Arrays.asList(methods), SETTER_PREDICATE);
    }

	@Override
	public boolean hasMethod(Method method) {
		return isGetter(method) || isSetter(method);
	}

	@Override
	public boolean isGetter(Method method) {
		return getter.equals(method);
	}

	@Override
	public boolean isSetter(Method method) {
		return setter.equals(method);
	}
    
}
