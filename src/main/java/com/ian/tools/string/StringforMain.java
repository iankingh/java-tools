package com.ian.string;

import java.io.UnsupportedEncodingException;

public class StringforMain {

	public static void main(String[] args) {

		String UAA = "010200";
		System.out.println(UAA.subSequence(0, 2));

		// 用于存储html字符串
		StringBuilder stringHtml = new StringBuilder();
		// 输入HTML文件内容
		stringHtml.append("<html><head>");
		stringHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		stringHtml.append("<title>测试报告文档</title>");
		stringHtml.append("</head>");
		stringHtml.append("<body>");
		stringHtml.append("<div>hello</div>");
		stringHtml.append("</body></html>");

		System.out.println(stringHtml.toString());

		String uaaZ = null;

		Object i = 1;
		int is = (int) i;
		String uaa = null;
		boolean b = StringUtils.isBlank((String) uaa);
		System.out.println((String) uaa);
		/**
		 * 分開 字母和數字
		 */
		System.out.println("*******使用正則表達式******");
		String letterAndNumber = "UAA001";

		// 使用正則表達式
		String stringNoEngilsh = letterAndNumber.replaceAll("\\d+", "");// \d :
																		// 匹配數字字符等價於[0-9]
		String stringEnglish = letterAndNumber.replaceAll("\\D+", "");// \D :
																		// 匹配非數字字符等價於[^
																		// 0-9]
		System.out.println("stringNoEngilsh : " + stringNoEngilsh);
		System.out.println("stringEnglish : " + stringEnglish);
		// 使用正則表達式
		System.out.println("******使用正則表達式END*******");

		// substring
		System.out.println("******使用substring*******");
		String MESSAGE_CATALOG = letterAndNumber.substring(0, 3); // 字串中 0-3 位
																	// 的值
		String MESSAGE_CODE = letterAndNumber.substring(3); // 字串中 3位以後 的值

		System.out.println("MESSAGE_CATALOG : " + MESSAGE_CATALOG);
		System.out.println("MESSAGE_CODE : " + MESSAGE_CODE);
		System.out.println("******使用substring END*******");
		/*
		 * 分開 字母和數字 end
		 */

		/**
		 * 轉換大小寫 非字母的字元不會做任何改變
		 */
		String str = "abcd++ef";
		// 轉換成大寫
		System.out.println(" 轉換str(abcd++ef)成大寫 : " + str.toUpperCase());
		// 轉換成小寫
		System.out.println(" 轉換str(abcd++ef)成小寫 : " + str.toLowerCase());

		// 字元轉換
		char ch = 'a';
		// 轉換成大寫
		System.out.println(Character.toUpperCase(ch));
		// 轉換成小寫
		System.out.println(Character.toLowerCase(ch));

		/*
		 * 轉換大小寫 END
		 */

		/**
		 * 轉換UTF-8
		 */
		String strt = "任意字符串";
		try {

			String strtoutf8p2 = java.net.URLDecoder.decode(strt, "UTF-8");

			System.out.println("strtoutf8 : " + strtoutf8p2);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 轉換UTF-8 end

		// 判斷某字串是否為空值，為空值的標準是 字串等於null 、字串的長度為 0 或者是字串是由空白所組成的的狀態下
		System.out.println("******StringUtils.isBlank*******");
		System.out.println(StringUtils.isBlank(null));// true
		System.out.println(StringUtils.isBlank(""));// true
		System.out.println(StringUtils.isBlank(" "));// true
		System.out.println(StringUtils.isBlank("abc"));// false
		System.out.println(StringUtils.isBlank("  abc  "));// false
		System.out.println(StringUtils.isBlank("\t \n \f \r")); // true
		System.out.println("******StringUtils.isBlankEND*******");
		// 而相反的就是不為空值，字串不等於null 、字串的長度不為 0 或者是字串不是由空白所組成的的狀態下
		System.out.println("******StringUtils.isNotBlank*******");
		System.out.println(StringUtils.isNotBlank(null));// false
		System.out.println(StringUtils.isNotBlank(""));// false
		System.out.println(StringUtils.isNotBlank(" "));// false
		System.out.println(StringUtils.isNotBlank("abc"));// true
		System.out.println(StringUtils.isNotBlank("  abc  "));// true
		System.out.println(StringUtils.isNotBlank("\t \n \f \r"));// false
		System.out.println("******StringUtils.isNotBlankEND*******");
		// 判斷 cid 不是空的嗎? 不是>>轉大寫 : 是 "";
		// String cid =
		// StringUtils.isNotBlank(req.getParameter(PARAM_CUSTOMER_ID))
		// ? req.getParameter(PARAM_CUSTOMER_ID).toUpperCase() : "";
		String Stringutilstest = "abcdefg123";
		String cid = StringUtils.isNotBlank(Stringutilstest) ? Stringutilstest.toUpperCase() : "";
		System.out.println("cid : " + cid);

		String numX = "15165S1651s";

		// java 驗證密碼的正則表達式,要求同時有數字和字母，長度最小6，最大16
		String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
		// 規則 第一位[1-9], 第二位以後[0-9] 出現次數{4,14}
		String regex = "[1-9][0-9]{4,14}";
		System.out.println(Stringutilstest.matches(reg));
		boolean flag = Stringutilstest.matches(reg);
		if (flag) {
			System.out.println("OK");
		} else {
			System.out.println("No");
		}

		System.out.println("******Object轉換為String的一些總結*******");
		// Object轉換為String的一些總結 // Object 3 種轉型

		// 1 .採用Object.toString（）方法

		Object objToNull = null;
		Object objToInt = new Integer(100);
		Object objToString = "String";

		// 1. toString null 會 錯誤
		// String objToNullToString = objToNull.toString();
		// System.out.println("objToNullToString" + objToNullToString);// true
		String objToIntToString = objToInt.toString();
		System.out.println("objToIntToString  : " + objToIntToString);

		// 2. (String) 轉型 null = null
		// 這是標準的類型轉換，將object轉成String類型的值。使用這種方法時，需要注意的是類型必須能轉成String類型。因此最好用instanceof做個類型檢查，以判斷是否可以轉換。否則容易拋出CalssCastException異常。此外，需特別小心的是因定義為Object
		// 類型的對像在轉成String時語法檢查並不會報錯，這將可能導致潛在的錯誤存在。這時要格外小心。
		// null 時 StringUtils.isBlank(XXC) == true
		String objToNullforObject = (String) objToNull;
		System.out.println("objToNullforObject : " + objToNullforObject);// true
		// //Object = 123(int類型) 會錯誤
		// String objToIntforObject = (String) objToInt;
		// System.out.println("objToIntforObject : " + objToIntforObject);

		// 3. valueOf 轉型 null ==>>> "null"
		// null 時 不 ==null
		// null 時 StringUtils.isBlank(XXC) == false
		String objToNullvalueOf = String.valueOf(objToNull);
		System.out.println("objToNullvalueOf : " + objToNullvalueOf);// true
		String objToIntvalueOf = String.valueOf(objToInt);
		System.out.println("objToIntvalueOf  : " + objToIntvalueOf);

		// Object 3 種轉型 END
		System.out.println("******Object 3 種轉型 END*******");

		// 新使用者代號，格式錯誤，長度限6-16位英數字 , 並限制不可有特殊自元
		String newAccount = "CHRIL";
		String regX = "[A-Za-z0-9]{6,16}";
		if (!newAccount.matches(regX)) {
			String returnErro = "不可有特殊字元";
			System.out.println(returnErro);
		}
		// java中用正则表达式验证一个字符串中是否包含连续的4位数字
		String regS = "^.*\\d{4}.*$";
		String s = "aa2aa11ab3333aa11";
		if (s.matches(regS)) {
			System.out.println("SSS");
		}

	}

	// 抽成 外部檔
	public static void checkNum() {
		String num = "151651651";

		// java 驗證密碼的正則表達式,要求同時有數字和字母，長度最小6，最大16
		String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
		// 規則 第一位[1-9], 第二位以後[0-9] 出現次數{4,14}
		String regex = "[1-9][0-9]{4,14}";
		boolean flag = num.matches(regex);

		if (flag) {
			System.out.println("OK");
		} else {
			System.out.println("No");
		}
	}

}
