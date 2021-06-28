package com.ian.tools.json;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * 本類別詳細說明。
 * <p/>
 * Package: com.ian.json <br>
 * File Name: RemoveNullAttr <br>
 * <p/>
 * Purpose: 开发API，其中返回的Bean被ToJson后，未赋值的Bean属性会被带上""，该方法使用递归把所有的空对象去除<br>
 * 
 * @ClassName: com.ian.json.RemoveNullAttr
 * @Description: TODO
 * @Company: Team.
 * @author Ian
 * @version 1.0, 2019年2月21日
 * @see https://blog.csdn.net/jjwldxsk/article/details/68945824
 */
public class RemoveNullAttr {

	public static void main(String[] args) {

		String s = "{\"employees\": [{ \"firstName\":\"\" , \"lastName\":\"Doe\" },{ \"firstName\":\"\" , \"lastName\":\"Smith\" },{ \"firstName\":\"Peter\" , \"lastName\":\"Jones\" }]}";
		String a = "{ \"firstName\":\"John\" , \"lastName\":\"\" }";
		String b = "{ \"firstName\":\"\" , \"lastName\":\"Doe\" }";
		String test = "{\"ka\":\"va\",\"kb\":{\"kbk\":\"kbv\",\"kbkk\":\"\",\"kbkkk\":{\"aaa\":\"bbb\",\"bbb\":\"\"},\"suzu\":[{\"sz\":\"sz\",\"szk\":\"\"}]},\"kc\":\"kcv\"}";
		JSONObject js = JSONObject.parseObject(test);

		RemoveNullAttr r = new RemoveNullAttr();
		Object o = r.traverseJson(js);
		System.out.println(o);
	}

	private Object traverseJson(Object json) {
		// check null
		if (json == null) {
			return null;
		}
		try {
			if (json instanceof JSONObject) {// if json is a Map
				JSONObject jsonObj = (JSONObject) json;
				List keyList = new ArrayList();
				for (String k : jsonObj.keySet()) {
					String value = jsonObj.get(k).toString();
					if (StringUtil.isEmpty(value)) {
						keyList.add(k);
						// jsonObj.remove(k);
					} else {
						if (isJsonObj(value)) {
							jsonObj.put(k, traverseJson(JSONObject.parseObject(value)));
						} else {
							if (isJsonArr(value)) {
								// value=;
								jsonObj.put(k, traverseJson(JSONArray.parseArray(value)));
							}
						}
					}
				}
				for (String k : keyList) {
					jsonObj.remove(k);
				}
				return jsonObj;
			} else if (json instanceof JSONArray) {// if json is an Array
				JSONArray jsonArr = (JSONArray) json;
				int len = jsonArr.size();
				for (int i = 0; i < len; ++i) {
					// TODO: do something here
					jsonArr.set(i, traverseJson(jsonArr.get(i)));
				}
				// return retArr;
				//
				// } else {// if json is just a raw element
				//
				// // TODO: do something here
				// return json;
				return jsonArr;
				//
			}

		} catch (Exception e) {
			e.printStackTrace();// deal Exception or throw it
		}

		return null;
	}

	public boolean isJsonObj(Object o) {
		try {
			JSONObject js = JSONObject.parseObject(o.toString());
			return true;

		} catch (Exception e) {
			return false;
		}
	}
	public boolean isJsonArr(Object o) {
		try {
			JSONArray js = JSONArray.parseArray(o.toString());
			return true;

		} catch (Exception e) {
			return false;
		}
	}

}
