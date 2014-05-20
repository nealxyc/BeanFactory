BeanFactory
===========
BeanFactory implements Java Bean style getter setter methods for your Interface.

## v0.1.0
Could implement simple Interface
```java
//definition of MyInterface
public interface MyInterface{
	public void setName(String name);
	public String getName();
	public void setNumber(int name);
	public int getNumber();
}

// User BeanFactory to create implementation
BeanFactory factory = new BeanFactory();
MyInterface mi = factory.newInstance(MyInterface.class);
assertNotNull(mi);
assertNull(mi.getName());
mi.setName("my name");
assertEquals("my name", mi.getName());
```
