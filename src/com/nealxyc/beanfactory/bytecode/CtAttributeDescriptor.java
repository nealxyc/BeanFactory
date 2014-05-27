package com.nealxyc.beanfactory.bytecode;

import javassist.CtClass;

public interface CtAttributeDescriptor {
    public CtClass getType();
    public String getName();
}
