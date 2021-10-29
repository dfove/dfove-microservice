/*
 * Copyright (c) 2021 dfove.com Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package common.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import common.entity.Constants;

public class DateUtil {

	private static final String[] REGEXS = new String[] {
			"^\\d{4}-\\d{1,2}-\\d{1,2} {1}(\\d{1,2}:\\d{1,2}:\\d{1,2}) {1}(\\d{1,7})$",
			"^\\d{4}-\\d{1,2}-\\d{1,2} {1}(\\d{1,2}:\\d{1,2}:\\d{1,2})$", "^\\d{4}-\\d{1,2}-\\d{1,2}$",
			"^\\d{4}-\\d{1,2}$", };

	private static final String[] FORMATS = new String[] { "yyyy-MM-dd HH:mm:ss SSS", "yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd", "yyyy-MM" };

	/**
	 * 日期格式化 now.yyyy-mm-dd HH:mm:ss
	 * 
	 * @return 日期字符串
	 */
	public static String dateTime() {
		return dateTime(new Date());
	}

	/**
	 * 日期格式化 date.yyyy-mm-dd HH:mm:ss
	 * 
	 * @param date java.util.Date
	 * @return 日期字符串
	 */
	public static String dateTime(Date date) {
		return dateTime(date, Constants.SHORT_TIME);
	}

	/**
	 * 日期格式化
	 * 
	 * @param date    java.util.Date
	 * @param pattern yyyy-mm-dd 任意格式
	 * @return 日期字符串
	 */
	public static String dateTime(Date date, String pattern) {
		if (date == null) {
            date = new Date();
        }
		return (new SimpleDateFormat(pattern)).format(date);
	}

	/**
	 * 字符串转换为日期
	 * 
	 * @param string
	 * @return
	 */
	public static Date toDate(String string) {
		try {
			for (int i = 0; i < REGEXS.length; i++) {
				if (StringUtil.match(REGEXS[i], string)) {
					return (new SimpleDateFormat(FORMATS[i])).parse(string);
				}
			}
		} catch (ParseException e) {
			return new Date();
		}

		return new Date();
	}
	/**
	 * 字符串转换为日期,返回值错误和默认值为空
	 * @param string
	 * @return
	 */
	public static Date toDateNull(String string) {
		try {
			for (int i = 0; i < REGEXS.length; i++) {
				if (StringUtil.match(REGEXS[i], string)) {
					return (new SimpleDateFormat(FORMATS[i])).parse(string);
				}
			}
		} catch (ParseException e) {
			return null;
		}
		
		return null;
	}

	/**
	 * 时间操作
	 * 
	 * @param date   原始日期
	 * @param field  年1，月2，日6，小时10，分12，秒13
	 * @param amount
	 * @return
	 */
	public static Date add(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (field == 6) {
			field = 10;
			amount = amount * 24;
		}
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 日期转换为自1970-1-1 0:0:0起的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static Long toMilliseconds(String date) {
		SimpleDateFormat format = new SimpleDateFormat(Constants.LONG_TIME);
		try {
			long time = format.parse(date).getTime();
			return time;
		} catch (ParseException e) {
			return (long) 0;
		}
	}

	/**
	 * 自1970-1-1 0:0:0起的毫秒数转换为日期
	 * 
	 * @param milliseconds
	 * @return
	 */
	public static Date fromMilliseconds(Long milliseconds) {
		return new Date(milliseconds);
	}

	/**
	 * 当前时刻转换为日期格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String toDayString() {
		return toDayString(new Date());
	}

	/**
	 * 日期转换为日期格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String toDayString(Date date) {
		return dateTime(date, Constants.DAY_ONLY);
	}

	/**
	 * 当前时刻转换为短时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String toShortTimeString() {
		return toShortTimeString(new Date());
	}

	/**
	 * 日期转换为短时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String toShortTimeString(Date date) {
		return dateTime(date, Constants.SHORT_TIME);
	}

	/**
	 * 当前时刻转换为长时间yyyy-MM-dd HH:mm:ss SSS格式
	 * 
	 * @param date
	 * @return
	 */
	public static String toLongTimeString() {
		return toLongTimeString(new Date());
	}

	/**
	 * 日期转换为长时间yyyy-MM-dd HH:mm:ss SSS格式
	 * 
	 * @param date
	 * @return
	 */
	public static String toLongTimeString(Date date) {
		return dateTime(date, Constants.LONG_TIME);
	}

	/**
	 * 当前时刻转换为日期格式 yyyyMMdd的整数
	 * 
	 * @return
	 */
	public static Integer toDayInteger() {
		return toDayInteger(new Date());
	}

	/**
	 * 日期转换为日期格式 yyyyMMdd的整数
	 * 
	 * @param date
	 * @return
	 */
	public static Integer toDayInteger(Date date) {
		return Integer.parseInt(dateTime(date, Constants.DAY_INTEGER));
	}

	/**
	 * 日期转换为整数
	 * 
	 * @param date
	 * @return
	 */
	public static Integer toInteger(Date date, String pattern) {
		return Integer.parseInt(dateTime(date, pattern));
	}

	/**
	 * 当前时刻转换为短时间格式 yyyyMMddHHmmss的整数
	 * 
	 * @return
	 */
	public static Integer toShortTimeInteger() {
		return toShortTimeInteger(new Date());
	}

	/**
	 * 日期转换为短时间格式 yyyyMMddHHmmss的整数
	 * 
	 * @param date
	 * @return
	 */
	public static Integer toShortTimeInteger(Date date) {
		return Integer.parseInt(dateTime(date, Constants.SHORT_TIME_INTEGER));
	}

	/**
	 * 当前时刻 转换为长时间格式 yyyyMMddHHmmssSSS的整数
	 * 
	 * @return
	 */
	public static Long toLongTimeInteger() {
		return toLongTimeInteger(new Date());
	}

	/**
	 * 日期转换为长时间格式 yyyyMMddHHmmssSSS的整数
	 * 
	 * @param date
	 * @return
	 */
	public static Long toLongTimeInteger(Date date) {
		return Long.parseLong(dateTime(date, Constants.LONG_TIME_INTEGER));
	}

	/**
	 * 验证date2是否在date1之后
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Boolean isAfter(Date date1, Date date2) {
		return date2.getTime() > date1.getTime();
	}
	/**
	 * 
	 * 返回当前时间-dateFrom 的时间差
	 * @param dateFrom
	 * @return
	 */
	
	public static Integer daysBetweenTwoDateBySecondsToInteger(Date dateFrom) {
		Date dateTo = new Date();
		dateFrom.setHours(0);
		dateFrom.setMinutes(0);
		dateFrom.setSeconds(0);
		dateTo.setHours(0);
		dateTo.setMinutes(0);
		dateTo.setSeconds(0);
		long from = dateFrom.getTime();
		long to = dateTo.getTime();
		return (int) ((to - from) / (1000 * 60 * 60 * 24L));
	}

	/**
	 * 获取本周的第一天日期和最后一天日期（按中国周）
	 * @param todayTime :"2017-03-15"
	 * @return arr[0] 第一天日期 ；arr[1]最后一天日期
	 * @throws Exception
	 */
	public static String[] getWeekStartandEndDate(String todayTime) throws Exception{
		String[] arr = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(todayTime));
		int d = 0;
		if(cal.get(Calendar.DAY_OF_WEEK)==1){
			d = -6;
		}else{
			d = 2-cal.get(Calendar.DAY_OF_WEEK);
		}
		cal.add(Calendar.DAY_OF_WEEK, d);
//所在周开始日期
		arr[0]=sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_WEEK, 6);
//所在周结束日期
		arr[1]=sdf.format(cal.getTime());
		return arr;
	}

	/**
	 * 获取指定月的第一天日期和最后一天日期
	 * @param todayTime:"2017-03-15"
	 * @return arr[0] 第一天日期 ；arr[1]最后一天日期
	 * @throws ParseException
	 */
	public static String[] getMonthStartAndEndDate(String todayTime) throws Exception{
		String[] arr = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(todayTime));
		c.set(Calendar.DAY_OF_MONTH, 1);
		arr[0] =sdf.format(c.getTime());
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		arr[1]=sdf.format(c.getTime());
		return arr;

	}

	/**
	 * 获取指定年的第一天日期和最后一天日期
	 * @param todayTime :"2017-03-15"
	 * @return arr[0] 第一天日期 ；arr[1]最后一天日期
	 * @throws ParseException
	 */
	public static String[] getYearStartAndEndDate(String todayTime) throws Exception{
		String[] arr = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(todayTime));
		c.set(Calendar.DAY_OF_YEAR, 1);
		arr[0] =sdf.format(c.getTime());
		c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
		arr[1]=sdf.format(c.getTime());
		return arr;

	}
}
