package com.mochu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json 工具
 * 
 * @author Administrator
 *
 */
public class JsonUtil {

	public static final String toJSONString(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 非法json重置
	 * 
	 * @param text
	 * @return
	 */
	public static String resetJsonStr(String text) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		text = text.replace("{", "").replace("}", "").replace("\"", "");
		String[] keyvalues = text.split(",");
		for (String keyvalueStr : keyvalues) {
			String[] keyvalue = keyvalueStr.split(":");
			if (keyvalue.length > 1) {
				jsonMap.put(keyvalue[0], keyvalue[1]);
			}
		}

		text = JsonUtil.toJSONString(jsonMap);
		return text;
	}

	public static final <T> T parseObject(String text, Class<T> clazz) {
		if (StrUtil.isNullStr(text)) {
			return null;
		}

		T t = null;
		try {
			t = (T) JSON.parseObject(text, clazz);
		} catch (Exception e) {
			try {
				text = resetJsonStr(text);
				t = (T) JSON.parseObject(text, clazz);
			} catch (Exception e2) {
				System.out.println(text);
				e2.printStackTrace();
			}
		}
		return t;
	}

	public static final <T> T parseObject(Object object, Class<T> clazz) {
		if (object == null) {
			return null;
		}
		return JsonUtil.parseObject(JsonUtil.toJSONString(object), clazz);
	}

	public static final <T> List<T> parseArray(String text, Class<T> clazz) {

		List<T> list = (List<T>) new ArrayList<T>();
		try {
			list = (List<T>) JSON.parseArray(text, clazz);
		} catch (Exception e) {
			System.out.println(text);
			e.printStackTrace();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<T> parseArray(String text) {

		@SuppressWarnings("rawtypes")
		List<T> list = new ArrayList();
		try {
			list = (List<T>) JSON.parseObject(text, new TypeReference<T>() {
			});
		} catch (Exception e) {
			System.out.println(text);
			e.printStackTrace();
		}

		return list;
	}




	/**
	 * json转成map<String,T>
	 *
	 * @param text
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Map<String, Object> parseMap(String text) {

		return parseObject(text, HashMap.class);
	}

	@SuppressWarnings("unchecked")
	public static final Map<String, String> parseStringMap(String text) {

		return parseObject(text, HashMap.class);
	}

}
