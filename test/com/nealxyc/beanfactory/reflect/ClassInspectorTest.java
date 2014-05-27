package com.nealxyc.beanfactory.reflect;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ClassInspectorTest {

	public static interface MyInterface {
		public void setName(String name);

		public String getName();

		public void setCode(Object code);

		public Object getCode();
	}

	@Test
	public void testReadClass() {
		ClassInspector ci = ClassInspector.readClass(MyInterface.class);
		assertNotNull(ci);
		assertTrue(ci.getAttributeGetterSetterList().size() > 0);
		assertEquals(2, ci.getAttributeGetterSetterList().size());
		for (AttributeGetterSetter attr : ci.getAttributeGetterSetterList()) {
		    String name = attr.getDescriptor().getName() ;
		    Class<?> cls = attr.getDescriptor().getType() ;
			assertTrue(name.equals("Name") ||name.equals("Code"));
			assertTrue((String.class).equals(cls)
					|| (Object.class).equals(cls));
		}
	}
}
