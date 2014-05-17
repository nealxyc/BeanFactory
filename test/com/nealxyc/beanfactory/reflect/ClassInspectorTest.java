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
		assertThat(ci.getAttributeGetterSetterList(), is(not(empty())));
		assertThat(ci.getAttributeGetterSetterList(), hasSize(2));
		for (AttributeGetterSetter attr : ci.getAttributeGetterSetterList()) {
			assertThat(attr.getDescriptor().getName(),
					anyOf(is("Name"), is("Code")));
			assertTrue(attr.getDescriptor().getType().equals(String.class)
					|| attr.getDescriptor().getType().equals(Object.class));
		}
	}
}
