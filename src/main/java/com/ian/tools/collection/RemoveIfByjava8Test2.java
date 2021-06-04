package com.ian.tools.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 本類別詳細說明。
 * 
 * 	// ---------------------
	// 作者：李学凯
	// 来源：CSDN
	// 原文：https://blog.csdn.net/qq_27093465/article/details/79154566
	// 版权声明：本文为博主原创文章，转载请附上博文链接！
 * <p/>
 * Package: com.ian.collection <br>
 * File Name: RemoveIfByjava8Test2 <br>
 * <p/>
 * Purpose: <br>
 * 
 * @ClassName: com.ian.collection.RemoveIfByjava8Test2
 * @Description: TODO
 * @Company: Team.
 * @author Ian
 * @version 1.0, 2019年3月4日
 */
public class RemoveIfByjava8Test2 {

		// 最后，看看源码，多说一丢丢。
	//
	// 直接点击，跳转到源码位置，发现如下；

	// default boolean removeIf(Predicate<? super E> filter) {
	// Objects.requireNonNull(filter);
	// boolean removed = false;
	// final Iterator<E> each = iterator();
	// while (each.hasNext()) {
	// if (filter.test(each.next())) {
	// each.remove();
	// removed = true;
	// }
	// }
	// return removed;
	// }

	// 逻辑很简单，但是，我要说的，你不一定就知道哟。
	// 首先，这个方法是在  Collection  这个接口 里面的，接口哟，interface哟。不是 class。不是抽象类。是接口。
	//
	// 还有就是这个方法的修饰，没有常见的 public private  protect，没有，因为接口不写，默认就是 public 的吧。
	//
	// 这个地方使用的是 default 修饰的。
	//
	// 这个default，只是提供默认的方法实现，但是，这个接口的实现类是可以覆盖这个方法的，
	//
	// 对，所以，上面我的代码里面，使用的是arraylist，所以，这个方法在arraylist类里面被覆盖啦。
	//
	// 所以，你调用的不一定就是这个Collection里面的这个方法


	// 作用：
	// 删除集合中符合条件的成员，empty集合也可以，但是null就炸啦。
	//
	// 直接看代码和运行结果

	/**
	 * 删除集合中符合条件的成员，empty集合也可以，但是null就炸啦。
	 */
	private static void removeIfTest() {
		// List<String> list = Lists.newArrayList("1","12","13","14","15","0");
		// List<String> list = new
		// ArrayList<String>(Arrays.asList("1","12","13","14","15","0"));
		List<String> list = Arrays.asList("1", "12", "13", "14", "15", "0");

		System.out.println("初始时：" + list.toString());
		list.removeIf(s -> s.contains("1"));
		System.out.println("过滤完：" + list.toString());
	}

	// 可以看到，代码的运行效果就是把集合里面元素包含“1”的都给删除啦。

	// 下面是咱1.8之前的做法

	/**
	 * 咱之前是怎么从集合中过滤掉不要的元素的
	 */
	private static void beforeRemove() {
		// List<String> list = Lists.newArrayList("1", "12", "13", "14", "15", "0");
		List<String> list = new ArrayList<String>(Arrays.asList("1", "12", "13", "14", "15", "0"));
		System.out.println("初始时：" + list.toString());

		System.out.println("for i 循环删除元素");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).contains("1")) {
				list.remove(i);
				// 一般之前这么操作集合的时候，估计都是会忘记 i-- 的
				i--;
			}
		}
		System.out.println("过滤完：" + list.toString());

		System.out.println("Iterator 迭代器 循环删除元素");
		list = new ArrayList<String>(Arrays.asList("1", "12", "13", "14", "15", "0"));
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().contains("1")) {
				iterator.remove();
			}
		}
		System.out.println("过滤完：" + list.toString());
		System.out.println("加强 for 循环  循环删除元素，直接异常。");
		// list = Lists.newArrayList("1", "12", "13", "14", "15", "0");
		list = new ArrayList<String>(Arrays.asList("1", "12", "13", "14", "15", "0"));
		for (String s : list) {
			if (s.contains("1")) {
				list.remove(s);
			}
		}
		System.out.println("过滤完：" + list.toString());
	}




}
