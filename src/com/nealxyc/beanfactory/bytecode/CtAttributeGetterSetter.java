package com.nealxyc.beanfactory.bytecode;

import java.lang.reflect.Method;

import javassist.CtMethod;

import com.nealxyc.beanfactory.reflect.AttributeDescriptor;

public interface CtAttributeGetterSetter {
    public AttributeDescriptor getDescriptor();
    public CtMethod getGetterMethod();
    public CtMethod getSetterMethod();
    public boolean hasMethod(Method method);
    public boolean isGetter(Method method);
    public boolean isSetter(Method method);
}
