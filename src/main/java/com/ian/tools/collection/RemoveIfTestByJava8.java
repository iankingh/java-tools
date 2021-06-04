package com.ian.tools.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * 
 * 本類別詳細說明。
 * <p/>
 * Package: com.ian.collection <br>
 * File Name: RemoveIfTestByJava8 <br>
 * <p/>
 * 
 * // 作者：黄嘉成
// 来源：CSDN
// 原文：https://blog.csdn.net/qq_33829547/article/details/80277956
// 版权声明：本文为博主原创文章，转载请附上博文链接！

 * 
 * Purpose:在JDK1.8中，Collection以及其子类新加入了removeIf方法，作用是按照一定规则过滤集合中的元素。这里给读者展示removeIf的用法。
 * 首先设想一个场景，你是公司某个岗位的HR，收到了大量的简历，为了节约时间，现需按照一点规则过滤一下这些简历。比如这个岗位是低端岗位，只招30岁以下的求职者。
 * <br>
 * 
 * @ClassName: com.ian.collection.RemoveIfTestByJava8
 * @Description:
 * @Company: Team.
 * @author Ian
 * @version 1.0, 2019年2月20日
 */
public class RemoveIfTestByJava8 {

	public static void main(String[] args) {

		// 该Person类只有三个成员属性，分别是姓名name，年龄age和性别gender。现要过滤age大于等于30的求职者。
		// 下面是不用removeIf，而是使用Iterator的传统写法：
		Collection<Person> collection = new ArrayList();
		collection.add(new Person("张三", 22, "男"));
		collection.add(new Person("李四", 19, "女"));
		collection.add(new Person("王五", 34, "男"));
		collection.add(new Person("赵六", 30, "男"));
		collection.add(new Person("田七", 25, "女"));
		// 过滤30岁以上的求职者
		Iterator<Person> iterator = collection.iterator();
		while (iterator.hasNext()) {
			Person person = iterator.next();
			if (person.getAge() >= 30)
				iterator.remove();
		}
		System.out.println(collection.toString());// 查看结果
		// 下面再看看使用removeIf的写法：
		collection.removeIf(person -> person.getAge() >= 30);// 过滤30岁以上的求职者
		System.out.println(collection.toString());// 查看结果
		// 30岁以上的王五和赵六都被过滤掉了。
		// 当然，如果对lambda表达式不熟悉的话，也可以使用不用lambda的removeIf，代码如下：
		collection.removeIf(new Predicate<Person>() {
			@Override
			public boolean test(Person person) {
				return person.getAge() >= 30;// 过滤30岁以上的求职者
			}
		});
		System.out.println(collection.toString());// 查看结果
	}

}

