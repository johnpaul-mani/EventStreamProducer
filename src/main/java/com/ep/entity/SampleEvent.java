package com.ep.entity;

public class SampleEvent {

	private String name;

	private String age;

	public SampleEvent() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "SampleEvent [name=" + name + ", age=" + age + "]";
	}

}
