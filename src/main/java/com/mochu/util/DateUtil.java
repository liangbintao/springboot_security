package com.mochu.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

	public static long TimeDay = 86400000;// 1000 * 60 * 60 * 24;
	public static long TimeHour = 3600000;// 1000 * 60 * 60;
	public static long TimeMinute = 60000;// 1000 * 60;
	public static long TimeSecond = 1000;// 1000 * 60;

	public static String DateFormatFull = "yyyy-MM-dd HH:mm:ss.S";
	public static String DateFormatStandard = "yyyy-MM-dd HH:mm:ss";
	public static String DateFormatShort = "MM-dd HH:mm";

	public static String DateFormatYearMonthDay = "yyyy-MM-dd";

	private static Map<String, SimpleDateFormat> formatMap = new HashMap<String, SimpleDateFormat>();

	public static SimpleDateFormat format(String pattern) {
		if (formatMap.size() > 1000) {
			formatMap.clear();
		}

		String key = Thread.currentThread().getId() + pattern;

		SimpleDateFormat format = formatMap.get(key);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			formatMap.put(key, format);
		}

		return format;
	}

	/**
	 * 当前秒钟
	 * 
	 * @return 1483607633
	 */
	public static Long currentSecond() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 当前分钟
	 * 
	 * @return 24726793
	 */
	public static Long currentMinute() {
		return currentSecond() / 60;
	}

	/**
	 * 星期几
	 * 
	 * @return 0星期天，1星期一，2星期二 ... 6星期六
	 */
	public static int weekDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 得到小时
	 * 
	 * @return 24小时制
	 */
	public static int dayHour() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int hourMinute() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MINUTE);
	}

	public static long daysBetween(Date endDate, Date startDate) {
		return (endDate.getTime() - startDate.getTime()) / TimeDay;
	}

	public static String textForDate(Date date, String pattern) {
		String dateStr = "";
		SimpleDateFormat bjSdf = new SimpleDateFormat(pattern);     // 北京
		bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			dateStr = bjSdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dateStr;
	}

	public static String weekDayOfDate(Date dt) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 将时间转换为“yyyy-MM-dd HH:mm:ss”的字符串形式
	 * 
	 * @param date
	 * @return
	 */
	public static String standardTextForDate(Date date) {
		String dateStr = textForDate(date, DateFormatStandard);
		return dateStr;
	}

	public static String fullTextForDate(Date date) {
		String dateStr = textForDate(date, DateFormatFull);
		return dateStr;
	}

	public static String gmtTextForDate(Date date) {
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
		String str = sdf.format(cd.getTime());
		return str;
	}

	/**
	 * 得到当前时间字符串
	 * 
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String currentDateText() {
		return dateTextWithDay(0);
	}

	public static String currentDateFullText() {
		return DateUtil.fullTextForDate(new Date());
	}

	/**
	 * 获得几天后日期
	 * 
	 * @param day
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String dateTextWithDay(int day) {
		Date date = new Date(System.currentTimeMillis() + day * TimeDay);
		return standardTextForDate(date);
	}

	/**
	 * 获取时间
	 * 
	 * @param minute
	 * 
	 * @return
	 */
	public static String dateTextWithMinute(int minute) {
		return standardTextForDate(dateWithMinute(minute));
	}

	/**
	 * 获取距离当前若干时间的日子
	 * 
	 * @param day
	 * @return "yyyy-MM-dd"
	 */
	public static String dayTextWithDay(int day) {
		// 获取今日
		Date timeDate = new Date();
		timeDate.setTime(timeDate.getTime() + day * TimeDay);
		DateFormat format = format(DateFormatYearMonthDay);
		String todayText = format.format(timeDate);
		return todayText;
	}

	/**
	 * 获取今日的年月日文本
	 * 
	 * @return "yyyy-MM-dd"
	 */
	public static String todayText() {
		return dayTextWithDay(0);
	}

	public static long minutesToNow(String t) {
		Date date = dateForText(t);

		long m = System.currentTimeMillis() - date.getTime();
		return (m / 1000) / 60;

	}

	public static Date dateWithMinute(int minute) {
		Date date = new Date(System.currentTimeMillis() + minute * 60 * 1000);
		return date;
	}

	/**
	 * 获得日期
	 * 
	 * @param day
	 * @return Date
	 */
	public static Date dateWithDay(int day) {
		Date date = new Date(System.currentTimeMillis() + day * TimeDay);
		return date;
	}

	public static Date dateForText(String text) {

		if (text == null || text.length() == 0) {
			return null;
		}
		Date date = null;

		DateFormat dateFormat = null;
		if (text.length() == DateFormatStandard.length()) {
			dateFormat = format(DateFormatStandard);
		} else if (text.length() == DateFormatShort.length()) {
			dateFormat = format(DateFormatShort);
		} else if (text.length() == DateFormatYearMonthDay.length()) {
			dateFormat = format(DateFormatYearMonthDay);
		} else {
			dateFormat = format(DateFormatFull);
		}

		try {
			date = dateFormat.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 将时间"yyyy-MM-dd HH:mm:ss"转换为统一的文本
	 * 
	 * @param timeString
	 * @return
	 */
	public static String getDisplayTime(String timeString) {
		String timeText = "";

		if (timeString != null) {
			Date date = dateForText(timeString);
			timeText = getDisplayTime(date);
		}

		return timeText;
	}

	public static String getDisplayTime(Date date) {
		String timeText = "";

		if (date != null) {
			if (date != null) {
				long minute = 60 * 1000;// 60秒

				long timeInterval = System.currentTimeMillis() - date.getTime();
				if (timeInterval < 5 * minute) {// 小于5分钟
					timeText = "刚刚";
				} else if (timeInterval < 60 * minute) {// 小于一个小时
					timeText = timeInterval / minute + "分钟前";
				} else if (timeInterval < 24 * 60 * minute) {
					timeText = timeInterval / (60 * minute) + "小时前";
				} else if (timeInterval < 48 * 60 * minute) {
					timeText = "昨天";
				} else {
					DateFormat dateFormat = format(DateFormatShort);
					timeText = dateFormat.format(date);
				}
			}

		}

		return timeText;
	}

}
