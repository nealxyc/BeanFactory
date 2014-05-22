package com.nealxyc.beanfactory;

import net.sf.cglib.proxy.Mixin;

import org.junit.Test;

import com.nealxyc.beanfactory.BeanFactoryTest.PrimitiveTest;

public class MixinTest {

    public static class IntImpl {
	private int num;

	public void setInt(int num) {
	    this.num = num;
	}

	public int getInt() {
	    return num;
	}

    }

    @Test
    public void test() {
	Mixin m = Mixin.create(new Class[]{PrimitiveTest.class}, new Object[]{new IntImpl()});
	
    }
}
