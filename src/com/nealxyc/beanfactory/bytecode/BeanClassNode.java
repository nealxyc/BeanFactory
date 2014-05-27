package com.nealxyc.beanfactory.bytecode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class BeanClassNode extends ClassNode{
    private Map<String, MethodNode> getterMethods = new HashMap<>();
    private Map<String, MethodNode> setterMethods = new HashMap<>();
    
    public void visitEnd() {
	List<MethodNode> list = methods ;
	
    }
}
