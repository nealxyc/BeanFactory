package com.nealxyc.beanfactory.bytecode;

import java.util.List;

import com.nealxyc.beanfactory.reflect.AttributeDescriptor;
import com.nealxyc.beanfactory.reflect.AttributeGetterSetter;
import com.nealxyc.beanfactory.reflect.ClassInspector;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.Modifier;
import javassist.NotFoundException;

/***
 * Implements abstract method using javassist lib
 * 
 * @author nealx
 * 
 */
public class AbstractMethodImplementer {

    static final String IMPL_STUB = "$Impl";
    static final String COMPOSITE_STUB = "$CompositeImpl" ;
    private CtClassInspector ci;
    private Class<?> implClass;
    private boolean implemented = false;
    private String implName;

    public AbstractMethodImplementer(CtClassInspector ci) {
	this.ci = ci;
    }

    public boolean isImpelented() {
	return implemented;
    }

    /**
     * Do the implementation. This method is supposed to be called only once.<br>
     * Internally it will check if the target class has been implemented
     * already. If so it will just return without doing any thing.
     */
    public synchronized void doImplement() {
	if (this.implemented) {
	    return;
	}
	CtClass cls = ci.getCls();
	CtClass impl;
	implName = implName != null? implName: (cls.getName() + IMPL_STUB);
	try {
	    impl = cls.getClassPool().get(implName);
	    if (impl != null) {
		// It is likely that the class has been implemented and loaded
		// already
		// Check first
		try {
		    this.implClass = this.getClass().getClassLoader()
			    .loadClass(implName);
		    if (this.implClass != null) {
			implemented = true;
			return;
		    }
		} catch (ClassNotFoundException e) {
		    // Do nothing
		    // Not found, then we are safe to load from CtClass
		}
		this.implClass = impl.toClass(this.getClass().getClassLoader(),
			this.getClass().getProtectionDomain());
		implemented = true;
		return;
		// this.implClass =
		// impl.toClass(this.getClass().getClassLoader(),
		// this.getClass().getProtectionDomain());
	    }
	} catch (NotFoundException e) {
	    // do following implementation
	} catch (CannotCompileException e) {
	    e.printStackTrace();
	    return;
	}

	impl = cls.getClassPool().makeClass(implName);
	try {
	    if (cls.isInterface()) {
		impl.addInterface(cls);
	    } else {
		impl.setSuperclass(cls);
	    }

	    List<CtAttributeGetterSetter> gsetters = ci
		    .getAttributeGetterSetterList();
	    for (CtAttributeGetterSetter gs : gsetters) {
		CtAttributeDescriptor attr = gs.getDescriptor();
		CtClass fieldType = attr.getType();
		// Add field
		impl.addField(new CtField(fieldType, attr.getName(), impl));

		// implements getter
		CtMethod getter = gs.getGetterMethod();
		CtMethod implGetter = new CtMethod(getter, impl, null);
		int mod = getter.getModifiers();
		mod -= Modifier.isAbstract(mod) ? Modifier.ABSTRACT : 0;
		implGetter.setModifiers(mod);
		implGetter.setBody("return " + attr.getName() + ";");
		impl.addMethod(implGetter);

		// implements setter
		CtMethod setter = gs.getSetterMethod();
		CtMethod implSetter = new CtMethod(setter, impl, null);
		mod = setter.getModifiers();
		mod -= Modifier.isAbstract(mod) ? Modifier.ABSTRACT : 0;

		implSetter.setModifiers(mod);
		implSetter.setBody("this." + attr.getName() + "=$1" + ";");
		impl.addMethod(implSetter);
	    }
	    this.implClass = impl.toClass(this.getClass().getClassLoader(),
		    this.getClass().getProtectionDomain());
	    this.implemented = true;
	} catch (CannotCompileException e) {
	    e.printStackTrace();
	}
    }

    public synchronized void doImplement(CtClass partial) {
	if (this.implemented) {
	    return;
	}
	CtClass cls = ci.getCls();
	CtClass impl;
	implName = implName != null? implName: (cls.getName() + COMPOSITE_STUB);
	try {
	    impl = cls.getClassPool().get(implName);
	    if (impl != null) {
		// It is likely that the class has been implemented and loaded
		// already
		// Check first
		try {
		    this.implClass = this.getClass().getClassLoader()
			    .loadClass(implName);
		    if (this.implClass != null) {
			implemented = true;
			return;
		    }
		} catch (ClassNotFoundException e) {
		    // Do nothing
		    // Not found, then we are safe to load from CtClass
		}
		this.implClass = impl.toClass(this.getClass().getClassLoader(),
			this.getClass().getProtectionDomain());
		implemented = true;
		return;
		// this.implClass =
		// impl.toClass(this.getClass().getClassLoader(),
		// this.getClass().getProtectionDomain());
	    }
	} catch (NotFoundException e) {
	    // do following implementation
	} catch (CannotCompileException e) {
	    e.printStackTrace();
	    return;
	}

	//
	if (!cls.isInterface()) {
	    throw new IllegalStateException(String.format(
		    "%s should be an interface.", cls.getName()));
	}

	if (partial.isInterface()) {
	    throw new IllegalStateException(String.format(
		    "%s should not be an interface.", partial.getName()));
	}
	CtClass comp = cls.getClassPool().makeClass(
		implName);
	try {
	    comp.setSuperclass(partial);
	} catch (CannotCompileException e) {
	    e.printStackTrace();
	}
	comp.addInterface(cls);
	//
	ci = CtClassInspector.readClass(comp);
	impl = comp ;
	try {

	    List<CtAttributeGetterSetter> gsetters = ci
		    .getAttributeGetterSetterList();
	    for (CtAttributeGetterSetter gs : gsetters) {
		CtAttributeDescriptor attr = gs.getDescriptor();
		CtClass fieldType = attr.getType();
		// Add field
		impl.addField(new CtField(fieldType, attr.getName(), impl));

		// implements getter
		CtMethod getter = gs.getGetterMethod();
		CtMethod implGetter = new CtMethod(getter, impl, null);
		int mod = getter.getModifiers();
		mod -= Modifier.isAbstract(mod) ? Modifier.ABSTRACT : 0;
		implGetter.setModifiers(mod);
		implGetter.setBody("return " + attr.getName() + ";");
		impl.addMethod(implGetter);

		// implements setter
		CtMethod setter = gs.getSetterMethod();
		CtMethod implSetter = new CtMethod(setter, impl, null);
		mod = setter.getModifiers();
		mod -= Modifier.isAbstract(mod) ? Modifier.ABSTRACT : 0;

		implSetter.setModifiers(mod);
		implSetter.setBody("this." + attr.getName() + "=$1" + ";");
		impl.addMethod(implSetter);
	    }
	    
	    this.implClass = impl.toClass(this.getClass().getClassLoader(),
		    this.getClass().getProtectionDomain());
	    this.implemented = true;
	} catch (CannotCompileException e) {
	    e.printStackTrace();
	}
    }

    public Class<?> getImplClass() {
	return implClass;
    }

	public String getImplName() {
		return implName;
	}

	public void setImplName(String implName) {
		this.implName = implName;
	}
}
