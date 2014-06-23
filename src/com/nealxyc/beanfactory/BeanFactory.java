package com.nealxyc.beanfactory;

import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import net.sf.cglib.proxy.Enhancer;

import com.google.common.collect.Maps;
import com.nealxyc.beanfactory.bytecode.AbstractMethodImplementer;
import com.nealxyc.beanfactory.bytecode.CtClassInspector;
import com.nealxyc.beanfactory.callback.*;
import com.nealxyc.beanfactory.reflect.ClassInspector;

public class BeanFactory {

    private Map<Class, BeanAttributeIntercepterFactory> intercepterFactoryMap = Maps
	    .newHashMap();
    private Map<Class, Enhancer> enhancerMap = Maps.newHashMap();
    private Map<Class, AbstractMethodImplementer> implmeneterMap = Maps.newHashMap();

    private synchronized BeanAttributeIntercepterFactory getIntercepterFacotry(
	    Class<?> cls) {
	if (!intercepterFactoryMap.containsKey(cls)) {
	    BeanAttributeIntercepterFactory factory = new BeanAttributeIntercepterFactory(
		    ClassInspector.readClass(cls));
	    intercepterFactoryMap.put(cls, factory);
	}
	return intercepterFactoryMap.get(cls);
    }

    private synchronized <T> Enhancer enhanceClass(Class<T> cls) {
	if (Enhancer.isEnhanced(cls) && enhancerMap.containsKey(cls)) {
	    return enhancerMap.get(cls);
	}
	Enhancer eh = new Enhancer();
	eh.setSuperclass(cls);
	eh.setUseFactory(false);
	enhancerMap.put(cls, eh);
	return eh;
    }

    private synchronized AbstractMethodImplementer getImplementer(Class<?> cls)
	    throws NotFoundException {

	if (!implmeneterMap.containsKey(cls)) {
	    CtClass cc = ClassPool.getDefault().get(cls.getName());
	    AbstractMethodImplementer implementer = new AbstractMethodImplementer(CtClassInspector.readClass(cc));
	    implementer.doImplement();
	    implmeneterMap.put(cls, implementer);

	}
	return implmeneterMap.get(cls);
    }
    
    private synchronized AbstractMethodImplementer getCompositeImplementer(Class<?> cls, Class<?> partial)
	    throws NotFoundException {

	if (!implmeneterMap.containsKey(cls)) {
	    CtClass cc = ClassPool.getDefault().get(cls.getName());
	    CtClass cp = ClassPool.getDefault().get(partial.getName());
	    AbstractMethodImplementer implementer = new AbstractMethodImplementer(CtClassInspector.readClass(cc) );
	    implementer.doImplement(cp);
	    implmeneterMap.put(cls, implementer);

	}
	return implmeneterMap.get(cls);
    }

    public <T> T newInstance(Class<T> cls) {
	Enhancer eh = enhanceClass(cls);
	BeanAttributeIntercepterFactory factory = getIntercepterFacotry(cls);
	BeanAttributeIntercepter bai = factory.create();
	eh.setCallback(bai);
	T t = (T) eh.create();
	bai.setTargetInstance(t);
	return t;
    }

    public <T> T newOptimizedInstance(Class<T> cls) {
	Enhancer eh = enhanceClass(cls);
	BeanAttributeIntercepterFactory factory = getIntercepterFacotry(cls);
	BeanAttributeIntercepter bai = factory.createOptimized();
	eh.setCallback(bai);
	T t = (T) eh.create();
	bai.setTargetInstance(t);
	return t;
    }

    public <T> T newJavassistInstance(Class<T> cls) {
	CtClass cc;
	try {
	    AbstractMethodImplementer implementer = getImplementer(cls);
	    Class<?> implClass = implementer.getImplClass();
	    return (T) implClass.newInstance();
	} catch (NotFoundException e1) {
	    e1.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}

	return null;
    }
    
    public <T> T newJavassistInstance(Class<T> cls, Class<?> partial) {
	CtClass cc;
	try {
	    AbstractMethodImplementer implementer = getCompositeImplementer(cls, partial);
	    Class<?> implClass = implementer.getImplClass();
	    return (T) implClass.newInstance();
	} catch (NotFoundException e1) {
	    e1.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}

	return null;
    }

}
