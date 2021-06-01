package com.ian.tools.number;

import java.text.DecimalFormat;

/**
 * DecimalFormatTest
 */
public class DecimalFormatTest {
  public static void main(String[] args) {

    double d = 123456789;
    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
    System.out.println(decimalFormat.format(d));
    DecimalFormat decimalFormat2 = new DecimalFormat("#,###.00");
    System.out.println(decimalFormat2.format(d));
    double pi = 3.1415927;// 圓周率
    // 取一位元整數

    System.out.println(new DecimalFormat("0").format(pi));// 3

    // 取一位元整數和兩位元小數

    System.out.println(new DecimalFormat("0.00").format(pi));// 3.14

    // 取兩位元整數和三位元小數，整數不足部分以0填補。

    System.out.println(new DecimalFormat("00.000").format(pi));// 03.142

    // 取所有整數部分

    System.out.println(new DecimalFormat("#").format(pi));// 3

    // 以百分比方式計數，並取兩位小數

    System.out.println(new DecimalFormat("#.##%").format(pi));// 314.16%

    long c = 299792458;// 光速

    // 顯示為科學計數法，並取五位小數

    System.out.println(new DecimalFormat("#.#####E0").format(c));// 2.99792E8

    // 顯示為兩位元整數的科學計數法，並取四位小數

    System.out.println(new DecimalFormat("00.####E0").format(c));// 29.9792E7

    // 每三位以逗號進行分隔。

    System.out.println(new DecimalFormat(",###").format(c));// 299,792,458

    // 將格式嵌入文本

    System.out.println(new DecimalFormat("光速大小為每秒,###米").format(c)); // 光速大小為每秒299,792,458米

  }

}