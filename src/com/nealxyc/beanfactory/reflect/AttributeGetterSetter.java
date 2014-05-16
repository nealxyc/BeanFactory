package com.nealxyc.beanfactory.reflect;

import java.lang.reflect.Method;

public interface AttributeGetterSetter {
    public AttributeDescriptor getDescriptor();
    public Method getGetterMethod();
    public Method getSetterMethod();
}
