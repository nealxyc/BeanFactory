package com.nealxyc.beanfactory.bytecode;

import javassist.CtMethod;


public interface CtAttributeGetterSetter {
    public CtAttributeDescriptor getDescriptor();
    public CtMethod getGetterMethod();
    public CtMethod getSetterMethod();
    public boolean hasMethod(CtMethod method);
    public boolean isGetter(CtMethod method);
    public boolean isSetter(CtMethod method);
}
