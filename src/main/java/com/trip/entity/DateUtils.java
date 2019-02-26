package com.trip.entity;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtils {

	public final static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	public final static String DATE_FORMAT_SOLIDUS = "yyyy/MM/dd";
	public final static String DATE_FORMAT_COMPACT = "yyyyMMdd";
	public final static String DATE_FORMAT_UTC_DEFAULT = "MM-dd-yyyy";
	public final static String DATE_FORMAT_UTC_SOLIDUS = "MM/dd/yyyy";
	public final static String DATE_FORMAT_YEAR_MONTH = "yyyyMM";

	public final static String DATE_TIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_TIME_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
	public final static String DATE_TIME_FORMAT_SOLIDUS = "yyyy/MM/dd HH:mm:ss";
	public final static String DATE_TIME_FORMAT_UTC_DEFAULT = "MM-dd-yyyy HH:mm:ss";
	public final static String DATE_TIME_FORMAT_UTC_SOLIDUS = "MM/dd/yyyy HH:mm:ss";

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String CMS_DRAW_SEQUENCE_FORMAT = "yyyyMMddHHmmss";
	public static final String XINBAO_DATE_FORMAT = "yyyy/MM/dd";
	public static final String MEMBER_SYSTEM_DATE_FORMAT = "yyyy-MM-dd";
	public static final String MATCH_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_STRING_FORMAT = "yyyyMMdd";

	private static Map<String, String> dateFormatRegisterMap = new HashMap<String, String>();

	static {
		dateFormatRegisterMap.put(DATE_FORMAT_COMPACT, "^\\d{8}$");
		dateFormatRegisterMap.put(DATE_FORMAT_DEFAULT, "^\\d{4}-\\d{1,2}-\\d{1,2}$");
		dateFormatRegisterMap.put(DATE_FORMAT_SOLIDUS, "^\\d{4}/\\d{1,2}/\\d{1,2}$");
		dateFormatRegisterMap.put(DATE_FORMAT_UTC_DEFAULT, "^\\d{1,2}-\\d{1,2}-\\d{4}$");
		dateFormatRegisterMap.put(DATE_FORMAT_UTC_SOLIDUS, "^\\d{1,2}/\\d{1,2}/\\d{4}$");
		dateFormatRegisterMap.put(DATE_TIME_FORMAT_DEFAULT, "^\\d{4}-\\d{1,2}-\\d{1,2}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$");
		dateFormatRegisterMap.put(DATE_TIME_FORMAT_SOLIDUS, "^\\d{4}/\\d{1,2}/\\d{1,2}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$");
		dateFormatRegisterMap.put(DATE_TIME_FORMAT_UTC_DEFAULT, "^\\d{1,2}-\\d{1,2}-\\d{4}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$");
		dateFormatRegisterMap.put(DATE_TIME_FORMAT_UTC_SOLIDUS, "^\\d{1,2}/\\d{1,2}/\\d{4}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$");

	}

	public static String format(String formatString, Date date) {
		return new SimpleDateFormat(formatString).format(date);
	}

	public static Date parseDate(String src) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}

		return parseDate(src, DATE_FORMAT_DEFAULT);
	}

	public static Date parseDate(String src, String dateTemplate) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}

		try {
			return getSimpleDateFormat(dateTemplate).parse(src);
		} catch (ParseException e) {
			throw new RuntimeException(String.format("unsupported date template:%s", src), e);
		}
	}

	public static <T> T parseDate(String src, Class<T> dateClazz) {

		if (StringUtils.isEmpty(src)) {
			return null;
		}

		return convertDate(parseDate(src), dateClazz);
	}

	public static <T> T parseDate(String src, String dateTemplate, Class<T> dateClazz) {

		if (StringUtils.isEmpty(src)) {
			return null;
		}

		return convertDate(parseDate(src, dateTemplate), dateClazz);
	}


	public static boolean isDate(Object obj) {

		if (obj ==  null) {
			return false;
		}
		return isDateClass(obj.getClass());
	}

	public static boolean isDateClass(Class<?> clazz) {
		return (Date.class.isAssignableFrom(clazz) || DateTime.class.isAssignableFrom(clazz));
	}

	public static String formatDate(Date date) {

		if (date == null ) {
			return null;
		}

		return formatDate(date, DATE_FORMAT_DEFAULT);
	}

	public static String formatDate(Date date, String dateTemplate) {
		if (date == null || StringUtils.isEmpty(dateTemplate)) {
			return null;
		}

		return getSimpleDateFormat(dateTemplate).format(date);
	}

	public static <T> T convertDate(Date src, Class<T> dateClazz) {

		if (src == null) {
			return null;
		}

		try {

			return dateClazz.getConstructor(long.class).newInstance(src.getTime());
		} catch (Exception e) {
			String errorMessage = String.format("unsupported date type:%s", dateClazz.getName());
			Logger.error(DateUtils.class, errorMessage);
			throw new RuntimeException(errorMessage, e);
		}
	}

	public static <T> T convertDate(DateTime src, Class<T> dateClazz) {

		if (src == null) {
			return null;
		}

		try {

			return dateClazz.getConstructor(long.class).newInstance(src.getMillis());
		} catch (Exception e) {
			String errorMessage = String.format("unsupported date type:%s", dateClazz.getName());
			Logger.error(DateUtils.class, errorMessage);
			throw new RuntimeException(errorMessage, e);
		}
	}

	public static SimpleDateFormat getSimpleDateFormat(String dateTemplate) {
		return new SimpleDateFormat(dateTemplate);
	}

	private static long render(long i, int j, int k) {
		return (i + (i > 0 ? j : -j)) / k;
	}

	public static long diffSecond(Date start, Date end) {
		return render(end.getTime() - start.getTime(), 999, 1000);
	}

	public static long diffMinute(Date end) {
		return diffMinute(new Date(System.currentTimeMillis()), end);
	}

	public static long diffMinute(Date start, Date end) {
		return render(diffSecond(start, end), 59, 60);
	}

	public static long diffHour(Date start, Date end) {
		return render(diffMinute(start, end), 59, 60);
	}

	public static long diffDay(Date start, Date end) {
		return offset(start, end, Calendar.DAY_OF_YEAR);
	}

	public static long diffMonth(Date start, Date end) {
		return offset(start, end, Calendar.MONTH) + diffYear(start, end);
	}

	public static long diffYear(Date start, Date end) {
		Calendar s = Calendar.getInstance();
		Calendar e = Calendar.getInstance();

		s.setTime(start);
		e.setTime(end);

		return e.get(Calendar.YEAR) - s.get(Calendar.YEAR);
	}

	private static long offset(Date start, Date end, int offsetCalendarField) {

		boolean bool = start.before(end);
		long rtn = 0;
		Calendar s = Calendar.getInstance();
		Calendar e = Calendar.getInstance();

		s.setTime(bool ? start : end);
		e.setTime(bool ? end : start);

		rtn -= s.get(offsetCalendarField);
		rtn += e.get(offsetCalendarField);

		while (s.get(Calendar.YEAR) < e.get(Calendar.YEAR)) {
			rtn += s.getActualMaximum(offsetCalendarField);
			s.add(Calendar.YEAR, 1);
		}

		return bool ? rtn : -rtn;
	}

	public static Date add(Date date, int n, int calendarField) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, n);

		return c.getTime();
	}

	public static Date addHour(Date date,int amount){
		return org.apache.commons.lang.time.DateUtils.addHours(date,amount);
	}

	public static String formatDateAsCmsDrawSequence(Date date) {
		return formatDate(date, CMS_DRAW_SEQUENCE_FORMAT);
	}

	public static DateTime parseAsDateTime(String date) {
		return new DateTime(parseDate(date));
	}

	public static Date startOfToday() {
		return startOfDay(new Date());
	}

	public static String formatDateTime(Date date) {
		return (date == null) ? null : formatDate(date, DATE_TIME_FORMAT);
	}

	public static Date parseDateTime(String date) {
		return parseDate(date, DATE_TIME_FORMAT);
	}

	public static String formatDateAsMatchTime(Date date) {
		return formatDate(date, MATCH_TIME_FORMAT);
	}

	public static boolean beforeToday(Date date) {
		return date.compareTo(DateUtils.startOfToday()) < 0;
	}

	public static boolean afterToday(Date date) {
		return date.compareTo(DateUtils.startOfToday()) > 0;
	}

	public static Date startOfDay(Date date) {
		return new DateTime(date).dayOfYear().roundFloorCopy().toDate();
	}

	public static String formatDateAsString(Date date) {
		return formatDate(date, DATE_STRING_FORMAT);
	}

	public static Date endOfToday() {
		return endOfDay(new Date());
	}

	public static Date endOfDay(Date date) {
		DateTime startDateTime = new DateTime(date).dayOfYear().roundFloorCopy();
		return startDateTime.plusDays(1).minusMillis(1).toDate();
	}

	public static boolean isOnSameDayOfMonth(DateTime datetime, DateTime other) {
		return datetime.getDayOfMonth() == other.getDayOfMonth();
	}

	public static String formatDateForXinbao(Date date) {
		return formatDate(date, XINBAO_DATE_FORMAT);
	}

	public static Date parseXinbaoDateFormat(String dateString) throws ParseException {
		return parseDate(dateString, XINBAO_DATE_FORMAT);
	}

	public static String parseMemberSystemDateFormat(String xinbaoDateFormat) throws ParseException {
		return formatDate(parseXinbaoDateFormat(xinbaoDateFormat), MEMBER_SYSTEM_DATE_FORMAT);
	}

	public static String getYearOfFourBits(Date date) {
		return new DateTime(date).getYear() + "";
	}

	public static String getMonthOfTwoBits(Date date) {
		String month = new DateTime(date).getMonthOfYear() + "";
		if (month.length() == 1) {
			month = "0" + month;
		}
		return month;
	}

	public static String getDayOfTwoBits(Date date) {
		String day = new DateTime(date).getDayOfMonth() + "";
		if (day.length() == 1) {
			day = "0" + day;
		}
		return day;
	}

	public static boolean compareTillSecond(Date oneDate, Date anotherDate) {
		if (oneDate == null || anotherDate == null)
			return false;
		return format(DATE_TIME_FORMAT_DEFAULT, oneDate).equals(format(DATE_TIME_FORMAT_DEFAULT, anotherDate));
	}

    public static Date firstTimeOfDay(Date date) {
        DateTime dt = new DateTime(date.getTime()).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        return dt.toDate();
    }

	public static Date lastTimeOfYear(Date date) {
		DateTime dt = new DateTime(new DateTime(date).getYear(),12,31,23,59,59,0);
		return dt.toDate();
	}

	public static String getFormatedDateString(float timeZoneOffset){
		if (timeZoneOffset > 13 || timeZoneOffset < -12) {
			timeZoneOffset = 0;
		}

		int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
		TimeZone timeZone;
		String[] ids = TimeZone.getAvailableIDs(newTime);
		if (ids.length == 0) {
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = new SimpleTimeZone(newTime, ids[0]);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(timeZone);
		return sdf.format(new Date());
	}

	public static Date getFormatedDate(float timeZoneOffset){
		String dateString = DateUtils.getFormatedDateString(timeZoneOffset);
		return DateUtils.parseDate(dateString,"yyyy-MM-dd HH:mm:ss");
	}
}
