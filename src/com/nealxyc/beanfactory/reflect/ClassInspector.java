package com.nealxyc.beanfactory.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.matcher.Predicate;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.sun.xml.internal.ws.util.StringUtils;

public class ClassInspector {
    
	private Class<?> cls ;
//	private List<Method> methods ;
	private Set<AttributeDescriptor> attributes ;
	private Set<AttributeDescriptor> readOnlyAttributes ;
	
	//private
	private ClassInspector(){}
	
	public Set<AttributeDescriptor> getAttributes() {
	    return ImmutableSet.copyOf(attributes);
	}

	
	public static ClassInspector readClass(Class<?> cls){
	    ClassInspector ci = new ClassInspector();
	    Method[] methods = cls.getDeclaredMethods();
	    Set<Method> methodSet = Sets.newHashSet(methods);
	    List<Method> getters = getGetters(methods);
	    List<Method> setters = getSetters(methods);
	}
	
	public static Predicate<Method> GETTER_PREDICATE = new Predicate<Method>(){
		@Override
		public boolean apply(Method item) {
		    String name = item.getName();
		    return name != null && (name.startsWith("get") || name.startsWith("is")) && item.getParameterTypes().length == 0;
		}
	};
	
	public static Predicate<Method> SETTER_PREDICATE = new Predicate<Method>(){
		@Override
		public boolean apply(Method item) {
		    String name = item.getName();
		    return name != null && (name.startsWith("set")) && item.getParameterTypes().length == 1;
		}
	};
	
	private static List<Method> getGetters(Method[] methods){
	    return Lambda.filter(GETTER_PREDICATE, methods);
	}
	
	private static List<Method> getSetters(Method[] methods){
	    return Lambda.filter(SETTER_PREDICATE, methods);
	}
}
