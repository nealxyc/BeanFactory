BeanFactory
===========
BeanFactory implements Java Bean style getter setter methods for your Interface. Underlying it uses [cglib](https://github.com/cglib/cglib) to intercept method calls.

## v0.1.0
Could implement simple Interface
```java
//definition of MyInterface
public interface MyInterface{
	public void setName(String name);
	public String getName();
	public void setNumber(int num);
	public int getNumber();
}

// Use BeanFactory to create implementation
BeanFactory factory = new BeanFactory();
MyInterface mi = factory.newInstance(MyInterface.class);
assertNotNull(mi);
assertNull(mi.getName());
mi.setName("my name");
assertEquals("my name", mi.getName());
```
