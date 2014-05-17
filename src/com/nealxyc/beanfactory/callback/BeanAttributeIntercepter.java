package com.nealxyc.beanfactory.callback;

import java.lang.reflect.Method;

import com.nealxyc.beanfactory.reflect.AttributeGetterSetter;
import com.nealxyc.beanfactory.reflect.ClassInspector;
import com.nealxyc.beanfactory.valueholder.IndexedBeanAttribute;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class BeanAttributeIntercepter implements MethodInterceptor{

	private AttributeGetterSetter[] gsetters ;
	private IndexedBeanAttribute[] attrs;
	private Class<?> targetClass ;//Might be interface
	private Object targetInstance ;
	
	protected BeanAttributeIntercepter(ClassInspector ci){
		this.targetClass = ci.getClass();
		this.gsetters = ci.getAttributeGetterSetterList().toArray(new AttributeGetterSetter[ci.getAttributeGetterSetterList().size()]);
		this.attrs = new IndexedBeanAttribute[gsetters.length];
		for(int i = 0 ; i < gsetters.length; i ++){
			attrs[i] = new IndexedBeanAttribute(gsetters[i].getDescriptor());
		}
	}

	public Object getTargetInstance() {
		return targetInstance;
	}

	public void setTargetInstance(Object targetInstance) {
		this.targetInstance = targetInstance;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		if(targetInstance == obj ){//Check if they are the same reference
			if(args == null || args.length == 0){
				//find getter
				for(int i = 0 ; i < gsetters.length; i ++){
					if(gsetters[i].isGetter(method)){
						return attrs[i].getValue();
					}
				}
			}else if(args.length == 1){
				//find setter
				for(int i = 0 ; i < gsetters.length; i ++){
					if(gsetters[i].isSetter(method)){
						attrs[i].setValue(args[0]);
						return null ;
					}
				}
			}else{
				//TODO exception ?
			}
		}
		return null;
	}
	
}
