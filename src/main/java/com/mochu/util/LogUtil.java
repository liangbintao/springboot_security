package com.mochu.util;

import java.util.Date;

public class LogUtil {

	public static void main(String[] args) {
		try {

			String[][] str = { { "a", "b", "c", }, { "d", "e", "f", }, { "g", "h", "i" } };
			log(str.length + "");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("finally");
		}
	}

	public static void log(String content) {

		String position = "";

		StackTraceElement[] elements = (new Exception()).getStackTrace();
		if (elements.length >= 2) {
			StackTraceElement traceElement = elements[1];

			String[] arr = traceElement.getClassName().split("\\.");
			String classname = arr[arr.length - 1];
			position = "[" + classname + "." + traceElement.getMethodName() + ":" + traceElement.getLineNumber() + "] ";
		}

		String prefix = DateUtil.textForDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS") + " " + position;

		System.out.println(prefix + content);
	}
}
