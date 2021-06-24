package com.ian.tools.collection;

// 求職者的實體
public class Person {

	private String name;// 姓名
	private Integer age;// 年龄
	private String gender;// 性别

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Person(String name, Integer age, String gender) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	// 重写toString，方便观看结果
	@Override
	public String toString() {
		return "Person{" + "name='" + name + '\'' + ", age=" + age + ", gender='" + gender + '\'' + '}';
	}

}
