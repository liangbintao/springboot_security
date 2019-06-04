package com.mochu.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Md5Util {
	/**
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/**
	 * MD5 加密
	 */
	public static String getMD5Str(Object... objects) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Object object : objects) {
			stringBuilder.append(object.toString());
		}
		return getMD5Str(stringBuilder.toString());
	}

	private final static String sk = "16:58:97:04:40:D1:9C:5D:A2:B3:29:F0:AE:88:32:E2:47:12:18:65";

	public static String buildSign(Map<String, String> sArray) {
		return buildMysign(sArray, sk);
	}

	/**
	 * 生成签名结果
	 * 
	 * @param sArray
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildMysign(Map<String, String> sArray,
			String secretKey) {
		String mysign = "";
		try {
			String prestr = createLinkString(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
			prestr = prestr + secretKey; // 把拼接后的字符串再与安全校验码直接连接起来
			mysign = getMD5Str(prestr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mysign;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}
}
