package com.nealxyc.beanfactory.callback;

import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.nealxyc.beanfactory.reflect.AttributeGetterSetter;
import com.nealxyc.beanfactory.reflect.ClassInspector;
import com.nealxyc.beanfactory.valueholder.IndexedBeanAttribute;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class OptimizedBeanAttributeIntercepter extends BeanAttributeIntercepter {

    private IdentityHashMap<Method, Integer> getterIndexMap = Maps.newIdentityHashMap();
    private IdentityHashMap<Method, Integer> setterIndexMap = Maps.newIdentityHashMap();

    protected OptimizedBeanAttributeIntercepter(ClassInspector ci) {
	super(ci);
	int i = 0;
	for (; i < super.gsetters.length; i++) {
	    getterIndexMap.put(gsetters[i].getGetterMethod(), i);
	    setterIndexMap.put(gsetters[i].getSetterMethod(), i);
	}
    }

    protected int findSetterIndex(Method method) {
	Integer i = setterIndexMap.get(method);
	if (i == null) {
	    i = -1;
	}
	return i;
    }

    protected int findGetterIndex(Method method) {
	Integer i = getterIndexMap.get(method);
	if (i == null) {
	    i = -1;
	}
	return i;
    }

}
