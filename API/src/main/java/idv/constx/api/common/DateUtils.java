package idv.constx.api.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
    
	/**
	 * 获取一周开始时间（周一零点）
	 * 
	 * @return
	 *
	 * @author const.x
	 * @createDate 2015年1月28日
	 */
	public static String getNowWeekBegin() {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime()) + " 00:00:00";
	}
	
	/**
	 * 获取一周开始时间（周日23点）
	 * 
	 * @return
	 *
	 * @author const.x
	 * @createDate 2015年1月28日
	 */
	public static String getNowWeekEnd() {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, 7 -dayofweek );
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime()) + " 23:59:59";
	}
	
	/**
	 * 获取当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 *
	 * @author const.x
	 * @createDate 2015年1月29日
	 */
	public static String getNowDateTime() {
		return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	/**
	 * 获取当前日期
	 * 
	 * @return yyyy-MM-dd
	 *
	 * @author const.x
	 * @createDate 2015年1月29日
	 */
	public static String getNowDate() {
		return  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	/**
	 * 与当前时间比对
	 * 
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return =0 等于 ； <0 小于当前时间 ； >0 大于当前时间  
	 * 
	 * @author const.x
	 * @throws ParseException 
	 * @createDate 2015年2月9日
	 */
	public static int compareWithNowTime(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date d =  format.parse(date);
		Date now = format.parse(format.format(new Date()));
		return  d.compareTo(now);
	}
	
	/**
	 * 与当前日期比对
	 * 
	 * @param date yyyy-MM-dd
	 * @return =0 等于 ； <0 小于当前日期  ； >0 大于当前日期  
	 * 
	 * @author const.x
	 * @throws ParseException 
	 * @createDate 2015年2月9日
	 */
	public static int compareWithNowDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Date d =  format.parse(date);
		Date now = format.parse(format.format(new Date()));
		return  d.compareTo(now);
	}
	
	
	/**
	 * 转换为 xx月xx日 周x 的格式
	 * 
	 * @return
	 *
	 * @author const.x
	 * @throws ParseException 
	 * @createDate 2015年1月28日
	 */
	public static String getWeek(String date) throws ParseException {
		if(StringUtils.isBlank(date)){
			return  date;
		}
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");     
		DateFormat format2 = new SimpleDateFormat("MM月dd日 周"); 
		Date d = format1.parse(date);
		int day = d.getDay();
		String res =  format2.format(d);
		switch(day){
			case 0: res += "日";break;
			case 1: res += "一";break;
			case 2: res += "二";break;
			case 3: res += "三";break;
			case 4: res += "四";break;
			case 5: res += "五";break;
			case 6: res += "六";break;
		}
		return res;
	}
	
	/**
	 * 获得当天24点时间 
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 *
	 * @author const.x
	 * @createDate 2015年2月10日
	 */
	public static String getTimesnight(){ 
		Calendar cal = Calendar.getInstance(); 
		cal.set(Calendar.HOUR_OF_DAY, 24); 
		cal.set(Calendar.SECOND, 0); 
		cal.set(Calendar.MINUTE, 0); 
		cal.set(Calendar.MILLISECOND, 0); 
		Date date = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
		} 
	
	/**
	 * 获取日期间隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 *
	 * @author const.x
	 * @createDate 2015年2月10日
	 */
	public static Long getDaysBetween(Date startDate, Date endDate) {  
        Calendar fromCalendar = Calendar.getInstance();  
        fromCalendar.setTime(startDate);  
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        fromCalendar.set(Calendar.MINUTE, 0);  
        fromCalendar.set(Calendar.SECOND, 0);  
        fromCalendar.set(Calendar.MILLISECOND, 0);  
  
        Calendar toCalendar = Calendar.getInstance();  
        toCalendar.setTime(endDate);  
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        toCalendar.set(Calendar.MINUTE, 0);  
        toCalendar.set(Calendar.SECOND, 0);  
        toCalendar.set(Calendar.MILLISECOND, 0);  
  
        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);  
    }  
}
