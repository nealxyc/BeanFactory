package com.nealxyc.beanfactory.bytecode;

import java.util.List;

import com.nealxyc.beanfactory.reflect.AttributeDescriptor;
import com.nealxyc.beanfactory.reflect.AttributeGetterSetter;
import com.nealxyc.beanfactory.reflect.ClassInspector;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/***
 * Implements abstract method using javassist lib 
 * @author nealx
 *
 */
public class AbstractMethodImplementer {

    public final static String IMPL_STUB = "$Impl";
    private ClassInspector ci ;
    
    public AbstractMethodImplementer(ClassInspector ci){
	this.ci = ci ;
    }
    public Class<?> doImplement() throws NotFoundException{
	Class<?> cls = ci.getCls() ;
	ClassPool pool = ClassPool.getDefault();
	CtClass cc = pool.get(cls.getName());
	CtClass impl = pool.makeClass(cls.getName() + IMPL_STUB);
	try {
	    impl.setSuperclass(cc);
	    List<AttributeGetterSetter> gsetters = ci.getAttributeGetterSetterList();
	    for(AttributeGetterSetter gs: gsetters){
		AttributeDescriptor attr = gs.getDescriptor();
		CtClass fieldType = pool.get(attr.getClass().getName());
		impl.addField(new CtField(fieldType, attr.getName(), impl));
		//CtMethod getter = cc.getMethod(gs.getGetterMethod().getName(),)
		//impl.addMethod(CtMethod.make(new MethodInfo(), impl));
	    }
	} catch (CannotCompileException e) {
	    e.printStackTrace();
	}
    }
}
