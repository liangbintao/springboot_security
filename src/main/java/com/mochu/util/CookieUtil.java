package com.mochu.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static final String SESSION = "session";

	public static final int MaxAge = 3600 * 24 * 30;

	/**
	 * @return
	 */
	public static String getSession(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String sessionid = "";
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equalsIgnoreCase(SESSION)) {
					sessionid = c.getValue();
					return sessionid;
				}
			}
		}
		return null;
	}

	/**
	 * @return
	 */
	public static String getAuthCallback(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String sessionid = "";
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equalsIgnoreCase("auth_callback")) {
					sessionid = c.getValue();
					return sessionid;
				}
			}
		}
		return null;
	}

	public static void deleteSession(HttpServletResponse response) {
		addCookie(response, SESSION, "", 0);
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);

		response.addCookie(cookie);
	}

	public static void setSession(HttpServletResponse response, String sessionid) {

		addCookie(response, SESSION, sessionid, MaxAge);
	}
}
