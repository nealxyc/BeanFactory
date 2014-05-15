package com.nealxyc.beanfactory.valueholder;

public class ValueHolder<T> {
	private T value;
	private Class<? extends T> type ;
	
	public ValueHolder(T obj, Class<? extends T> type){
		if(obj != null){
			if(type == null){
				throw new NullPointerException("type cannot be null.");
			}
			if(!type.isInstance(obj)){
				throw new IllegalStateException("obj has to be an instance of type");
			}
		}
		this.setValue(obj);
		this.setType(type);
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public Class<? extends T> getType() {
		return type;
	}
	
	private void setType(Class<? extends T> type) {
		this.type = type;
	}
}
