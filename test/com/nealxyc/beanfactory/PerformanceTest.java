package com.nealxyc.beanfactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.beans.BeanGenerator;

import org.junit.Before;
import org.junit.Test;

import com.nealxyc.beanfactory.BeanFactoryTest.MyInterface;
import com.nealxyc.beanfactory.BeanFactoryTest.PrimitiveTest;

public class PerformanceTest {

    private BeanFactory factory = new BeanFactory();
    
    int n ;
    long start;
    long end ;
	
    @Before
    public void setup() {
	factory.newInstance(MyInterface.class);
	factory.newInstance(PrimitiveTest.class);
	factory.newInstance(ReferenceTest.class);
	n = 1000000 ;// 1 million ops
	start = System.currentTimeMillis();
    }

    public static class PojoPrimitive implements PrimitiveTest {

	private boolean b;
	private char c;
	private byte by;
	private short sh;
	private int num;
	private long l;
	private float f;
	private double d;

	@Override
	public void setBoolean(boolean num) {
	    b = num;
	}

	@Override
	public boolean getBoolean() {
	    return b;
	}

	@Override
	public void setChar(char c) {
	    this.c = c;
	}

	@Override
	public char getChar() {
	    return c;
	}

	@Override
	public void setByte(byte num) {
	    this.by = num;
	}

	@Override
	public byte getByte() {
	    return by;
	}

	@Override
	public void setShort(short num) {
	    sh = num;
	}

	@Override
	public short getShort() {
	    return sh;
	}

	@Override
	public void setInt(int num) {
	    this.num = num;
	}

	@Override
	public int getInt() {
	    return num;
	}

	@Override
	public void setLong(long num) {
	    l = num;
	}

	@Override
	public long getLong() {
	    return l;
	}

	@Override
	public void setFloat(float num) {
	    f = num;
	}

	@Override
	public float getFloat() {
	    return f;
	}

	@Override
	public void setDouble(double num) {
	    d = num;
	}

	@Override
	public double getDouble() {
	    return d;
	}

    }

    @Test
    public void testPrimitiveVsPojo() {
	System.out.println();
	System.out.println(String.format("====> Testing primitive type field access performance"));
	PrimitiveTest pt = factory.newInstance(PrimitiveTest.class);
	start = System.currentTimeMillis();
	n = 1000000 ;// 1 million ops
	System.out.println(String.format("%sx%s set and %sx%s get method call",8,n, 8, n));
	for (int i = 0; i < n; i++) {
	    pt.setBoolean(true);
	    pt.setByte((byte) 2);
	    pt.setChar('a');
	    pt.setShort((short) 2);
	    pt.setInt(2);
	    pt.setLong(2l);
	    pt.setFloat(2.0f);
	    pt.setDouble(2.0);

	    pt.getBoolean();
	    pt.getChar();
	    pt.getByte();
	    pt.getShort();
	    pt.getInt();
	    pt.getLong();
	    pt.getFloat();
	    pt.getDouble();
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("BeanFacotry instance: %s ms",  end - start));
	
	
	PrimitiveTest optimized = factory.newOptimizedInstance(PrimitiveTest.class);
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    optimized.setBoolean(true);
	    optimized.setByte((byte) 2);
	    optimized.setChar('a');
	    optimized.setShort((short) 2);
	    optimized.setInt(2);
	    optimized.setLong(2l);
	    optimized.setFloat(2.0f);
	    optimized.setDouble(2.0);

	    optimized.getBoolean();
	    optimized.getChar();
	    optimized.getByte();
	    optimized.getShort();
	    optimized.getInt();
	    optimized.getLong();
	    optimized.getFloat();
	    optimized.getDouble();
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("BeanFacotry optimized instance: %s ms", end - start));

	
	//pojo implPrimitiveTest 
	pt = new PojoPrimitive();
	
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    pt.setBoolean(true);
	    pt.setByte((byte) 2);
	    pt.setChar('a');
	    pt.setShort((short) 2);
	    pt.setInt(2);
	    pt.setLong(2l);
	    pt.setFloat(2.0f);
	    pt.setDouble(2.0);

	    pt.getBoolean();
	    pt.getChar();
	    pt.getByte();
	    pt.getShort();
	    pt.getInt();
	    pt.getLong();
	    pt.getFloat();
	    pt.getDouble();
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("Pojo instance: %s ms", end - start));
	
    }
    
    public static interface ReferenceTest{
	public void setObj(Object obj);
	public Object getObj();
    }
    
    public static class ReferenceTestImpl implements ReferenceTest{
	
	private Object obj ;
	public void setObj(Object obj) {
	    this.obj = obj ;
	}
	public Object getObj(){
	    return obj ;
	}
    }
    
    @Test
    public void testReferenceVsPojo() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	System.out.println();
	System.out.println(String.format("====> Testing reference type field access performance"));
	ReferenceTest rt = factory.newInstance(ReferenceTest.class);
	n = 10000000 ;// 10 million ops
	System.out.println(String.format("%sx%s set and %sx%s get method call",  1,n, 1, n));
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    rt.setObj(new Object());
	    rt.getObj();
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("BeanFactory instance: %s ms", end - start));
	
	ReferenceTest optimized = factory.newOptimizedInstance(ReferenceTest.class);
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    optimized.setObj(new Object());
	    optimized.getObj();
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("BeanFactory optimized instance: %s ms", end - start));

	
	//pojo
	rt = new ReferenceTestImpl();
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    rt.setObj(new Object());
	    rt.getObj();
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("Pojo instance: %s ms", end - start));
	
	Object obj = createFromBeanGenerator();
	
	Method getter = obj.getClass().getMethod("getObj", null);
	Method setter = obj.getClass().getMethod("setObj", new Class[]{Object.class});
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    setter.invoke(obj, new Object());
	    getter.invoke(obj);
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("Bean Generator instance: %s ms", end - start));
	
    }
    
    protected Object createFromBeanGenerator(){
	BeanGenerator gen = new BeanGenerator();
	gen.addProperty("obj", Object.class);
	return gen.create();
    }
    
    @Test
    public void testNewInstance(){
	n = 100000 ;
	System.out.println();
	System.out.println(String.format("====> Testing constructor method performance"));
	System.out.println(String.format("Create %s instances.", n));
	
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    new ReferenceTestImpl();
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("Pojo instance: %s ms", end - start));
	
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    factory.newInstance(ReferenceTest.class);
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("BeanFactory: %s ms",end - start));
	
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    createFromBeanGenerator();
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("Bean Generator: %s ms", end - start));
    }
}
