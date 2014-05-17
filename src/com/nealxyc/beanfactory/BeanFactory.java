package com.nealxyc.beanfactory;

import java.util.Map;

import net.sf.cglib.proxy.Enhancer;

import com.google.common.collect.Maps;
import com.nealxyc.beanfactory.callback.*;
import com.nealxyc.beanfactory.reflect.ClassInspector;

public class BeanFactory {

	private Map<Class, BeanAttributeIntercepterFactory> intercepterFactoryMap = Maps.newHashMap();
	private Map<Class, Enhancer> enhancerMap = Maps.newHashMap();

	private BeanAttributeIntercepterFactory getIntercepterFacotry(Class<?> cls) {
		if (!intercepterFactoryMap.containsKey(cls)) {
			BeanAttributeIntercepterFactory factory = new BeanAttributeIntercepterFactory(ClassInspector.readClass(cls));
			intercepterFactoryMap.put(cls, factory);
		}
		return intercepterFactoryMap.get(cls);
	}
	
	private <T> Enhancer enhanceClass(Class<T> cls){
		if(Enhancer.isEnhanced(cls) && enhancerMap.containsKey(cls)){
			return enhancerMap.get(cls);
		}
		Enhancer eh = new Enhancer();
		eh.setSuperclass(cls);
		eh.setUseFactory(false);
		enhancerMap.put(cls, eh);
		return eh;
	}

	public <T> T newInstance(Class<T> cls) {
		Enhancer eh = enhanceClass(cls);
		BeanAttributeIntercepterFactory factory = getIntercepterFacotry(cls);
		BeanAttributeIntercepter bai = factory.create();
		eh.setCallback(bai);
		T t = (T) eh.create();
		bai.setTargetInstance(t);
		return t ;
	}
	
	

}
