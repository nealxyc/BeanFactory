package com.nealxyc.beanfactory.reflect;

import java.lang.reflect.Method;

public interface AttributeGetterSetter {
    public AttributeDescriptor getDescriptor();
    public Method getGetterMethod();
    public Method getSetterMethod();
    public boolean hasMethod(Method method);
    public boolean isGetter(Method method);
    public boolean isSetter(Method method);
}
