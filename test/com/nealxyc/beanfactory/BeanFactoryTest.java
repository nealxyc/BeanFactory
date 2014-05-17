package com.nealxyc.beanfactory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BeanFactoryTest {

	public static interface MyInterface{
		public void setName(String name);
		public String getName();
		public void setNumber(int name);
		public int getNumber();
	}
	
	private BeanFactory factory = new BeanFactory();
	
	@Before
	public void setup(){
	    factory.newInstance(MyInterface.class);
	    factory.newInstance(PrimitiveTest.class);
	}
	
	@Test
	public void testNewInstance(){
		MyInterface mi = factory.newInstance(MyInterface.class);
		assertNotNull(mi);
		assertNull(mi.getName());
		mi.setName("my name");
		assertEquals("my name", mi.getName());
		
		assertEquals(0, mi.getNumber());
		mi.setNumber(12);
		assertEquals(12, mi.getNumber());
	}
	
	@Test
	public void testTwoInstanceDontIntervene(){
		MyInterface mi = factory.newInstance(MyInterface.class);
		MyInterface mi2 = factory.newInstance(MyInterface.class);
		assertNotNull(mi);
		assertNotNull(mi2);
		assertNull(mi.getName());
		mi.setName("my name");
		assertEquals("my name", mi.getName());
		
		assertNull(mi2.getName());
		mi2.setName("an other name");
		assertEquals("an other name", mi2.getName());
		assertEquals("my name", mi.getName());
	}
	
	public static interface PrimitiveTest{
	    /*
                java.lang.Boolean.TYPE
                java.lang.Character.TYPE
                java.lang.Byte.TYPE
                java.lang.Short.TYPE
                java.lang.Integer.TYPE
                java.lang.Long.TYPE
                java.lang.Float.TYPE
                java.lang.Double.TYPE
                java.lang.Void.TYPE
	     */
	    public void setBoolean(boolean num);
	    public boolean getBoolean();
	    
	    public void setChar(char c);
	    public char getChar();
	    
	    public void setByte(byte num);
	    public byte getByte();
	    
	    public void setShort(short num);
	    public short getShort();
	    
	    public void setInt(int num);
	    public int getInt();
	    
	    public void setLong(long num);
	    public long getLong();
	    
	    public void setFloat(float num);
	    public float getFloat();
	    
	    public void setDouble(double num);
	    public double getDouble();
	    
	}
	
	@Test
	public void testPrimitiveDefaultValue(){
	   PrimitiveTest pt = factory.newInstance(PrimitiveTest.class);
	   assertFalse(pt.getBoolean());
	   assertEquals((char)0, pt.getChar());
	   assertEquals((byte)0, pt.getByte());
	   assertEquals((short)0, pt.getShort());
	   assertEquals(0, pt.getInt());
	   assertEquals(0l, pt.getLong());
	   assertEquals(0.0f, pt.getFloat(), 0f);
	   assertEquals(0.0, pt.getDouble(), 0);
	}
	
	@Test
	public void testPrimitiveGetterSetter(){
	   PrimitiveTest pt = factory.newInstance(PrimitiveTest.class);
	   pt.setBoolean(true);
	   pt.setByte((byte)2);
	   pt.setChar('a');
	   pt.setShort((short)2);
	   pt.setInt(2);
	   pt.setLong(2l);
	   pt.setFloat(2.0f);
	   pt.setDouble(2.0);
	   
	   assertTrue(pt.getBoolean());
	   assertEquals('a', pt.getChar());
	   assertEquals((byte)2, pt.getByte());
	   assertEquals((short)2, pt.getShort());
	   assertEquals(2, pt.getInt());
	   assertEquals(2l, pt.getLong());
	   assertEquals(2.0f, pt.getFloat(), 0f);
	   assertEquals(2.0, pt.getDouble(), 0);
	}
}
