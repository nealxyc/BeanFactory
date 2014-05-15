package com.nealxyc.beanfactory.valueholder;


public class IndexedBeanAttribute<T> extends ValueHolder<T>{
	private final String name ;
	private int index ;
	
	public IndexedBeanAttribute(String name, Class<T> type){
		super(null, type);
		this.name = name ;
	}

	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public T get(){
		return super.getValue();
	}
	
	public void set(T t){
		super.setValue(t);
	}
	
}
