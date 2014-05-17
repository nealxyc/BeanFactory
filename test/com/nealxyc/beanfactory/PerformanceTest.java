package com.nealxyc.beanfactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.nealxyc.beanfactory.BeanFactoryTest.MyInterface;
import com.nealxyc.beanfactory.BeanFactoryTest.PrimitiveTest;

public class PerformanceTest {

    private BeanFactory factory = new BeanFactory();

    @Before
    public void setup() {
	factory.newInstance(MyInterface.class);
	factory.newInstance(PrimitiveTest.class);
	factory.newInstance(ReferenceTest.class);
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

	PrimitiveTest pt = factory.newInstance(PrimitiveTest.class);
	long start = System.currentTimeMillis();
	int n = 1000000 ;// 1 million ops
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
	long end = System.currentTimeMillis();
	System.out.println(String.format("BeanFacotry instance: %sx%s set and %sx%s get method call spent %s ms", 8,n, 8, n, end - start));
	
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
	System.out.println(String.format("Pojo instance: %sx%s set and %sx%s get method call spent %s ms", 8,n, 8, n, end - start));
	
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
    public void testReferenceVsPojo() {
	System.out.println(String.format("Testing reference type field access performance"));
	ReferenceTest rt = factory.newInstance(ReferenceTest.class);
	long start = System.currentTimeMillis();
	int n = 1000000 ;// 1 million ops
	for (int i = 0; i < n; i++) {
	    rt.setObj(new Object());
	    rt.getObj();
	}
	long end = System.currentTimeMillis();
	System.out.println(String.format("BeanFactory instance: %sx%s set and %sx%s get method call spent %s ms", 1,n, 1, n, end - start));
	
	//pojo
	rt = new ReferenceTestImpl();
	start = System.currentTimeMillis();
	for (int i = 0; i < n; i++) {
	    rt.setObj(new Object());
	    rt.getObj();
	}
	end = System.currentTimeMillis();
	System.out.println(String.format("Pojo instance: %sx%s set and %sx%s get method call spent %s ms", 1,n, 1, n, end - start));
	
    }
}
