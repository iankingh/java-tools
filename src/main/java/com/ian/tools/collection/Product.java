package com.ian.tools.collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Product {

	private String name;
	private String id;
	private Double price;

	public Product(String name, String id, Double price) {
		this.name = name;
		this.id = id;
		this.price = price;
	}

	@Override
	public String toString() {
		return "id:" + this.id + "\t name:" + this.name + "\t price:" + this.price + "\t\n";
	}

	public static void main(String[] args) {
		List<Product> list = new LinkedList<Product>();
		Product p1 = new Product("ipad", "K001", 299d);
		Product p2 = new Product("iphone", "K101", 299d);
		Product p3 = new Product("ipod", "K002", 299d);
		Product p4 = new Product("imac", "K015", 500d);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);

		System.out.println("加入ipad, iphone的產品資料:\n" + list.toString());
		System.out.println("產品總計:" + list.size());

		// 移除資料例子
		if (list.remove(p1)) {
			System.out.println("移除ipad的產品資料:" + p1);
		} else {
			System.out.println("資料庫沒有資料");
		}

		System.out.println("目前的資料:\n" + list.toString());

		// java判断list为空
		/*
		 * 这就相当与，你要喝水， 前面就是判断是不是连水杯都没有， 后面就是判断水杯里面没有水， 连盛水的东西都没有， 这个水从何而来？
		 * 所以一般的判断是 if(list!=null && !list.isEmpty()){ 这个里面取list中的值 }else{ 做其他处理
		 * }
		 */

		if (list != null && !list.isEmpty()) {
			// 使用iterator
			System.out.println("使用iterator集合走訪集合:");
			Iterator<Product> it = list.iterator();
			while (it.hasNext()) {
				System.out.println(it.next()); // 取出元素
			}

			// 使用foreach
			System.out.println("使用foreach走訪集合:");
			for (Product XXX : list) {
				System.out.println(XXX);
			}

		}
	}

}