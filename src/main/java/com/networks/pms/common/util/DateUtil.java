package com.networks.pms.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

	public static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static String DATE = "yyyy-MM-dd";
	
	public static String DATE_HHMM = "HH:mm";
	/**
	 * 功能: 将日期对象按照某种格式进行转换，返回转换后的字符串
	 * 
	 * @param date 日期对象
	 * @param pattern 转换格式 例：yyyy-MM-dd
	 */
	public static String DateToString(Date date, String pattern) {
		String strDateTime = null;
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		strDateTime = date == null ? null : formater.format(date);
		return strDateTime;
	}

	/**
	 * 功能: 将传入的日期对象按照yyyy-MM-dd格式转换成字符串返回
	 * 
	 * @param date 日期对象
	 * @return String
	 */
	public static String DateToString(Date date) {
		String _pattern = "yyyy-MM-dd";
		return date == null ? null : DateToString(date, _pattern);
	}

	/**
	 * 功能: 将传入的日期对象按照yyyy-MM-dd HH:mm:ss格式转换成字符串返回
	 * 
	 * @param date 日期对象
	 * @return String
	 */
	public static String DateTimeToString(Date date) {
		String _pattern = "yyyy-MM-dd HH:mm:ss";
		return date == null ? null : DateToString(date, _pattern);
	}

	/**
	 * 功能: 将插入的字符串按格式转换成对应的日期对象
	 * 
	 * @param str 字符串
	 * @param pattern 格式
	 * @return Date
	 */
	public static Date StringToDate(String str, String pattern) {
		Date dateTime = null;
		try {
			if (str != null && !str.equals("")) {
				SimpleDateFormat formater = new SimpleDateFormat(pattern);
				dateTime = formater.parse(str);
			}
		} catch (Exception ex) {
		}
		return dateTime;
	}

	/**
	 * 功能: 将传入的字符串按yyyy-MM-dd格式转换成对应的日期对象
	 * 
	 * @param str 需要转换的字符串
	 * @return Date 返回值
	 */
	public static Date StringToDate(String str) {
		String _pattern = "yyyy-MM-dd";
		return StringToDate(str, _pattern);
	}

	/**
	 * 功能: 将传入的字符串按yyyy-MM-dd HH:mm:ss格式转换成对应的日期对象
	 * 
	 * @param str 需要转换的字符串
	 * @return Date
	 */
	public static Date StringToDateTime(String str) {
		String _pattern = "yyyy-MM-dd HH:mm:ss";
		return StringToDate(str, _pattern);
	}

	/**
	 * 时间类型的String转String类型的时间戳
	 * @param s
	 * @return
	 */
	public static  String DataStringToTimeStampString(String s) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		String data = null ;
		try {
			d = simpleDateFormat.parse(s);
			data =  String.valueOf(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}
	public static  String DataStringToTimeStampString(String time,String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date d = null;
		String data = null ;
		try {
			d = simpleDateFormat.parse(time);
			data =  String.valueOf(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * String类型的时间戳转换"yyyy-MM-dd"的String类型
	 * @param s
	 * @return
	 */
	public static String TimeStampStringToString(String s){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(Long.parseLong(s));
		return simpleDateFormat.format(date);
	}
	/**
	 * 功能: 将传入的字符串转换成对应的Timestamp对象
	 * 
	 * @param str 待转换的字符串
	 * @return Timestamp 转换之后的对象
	 * @throws Exception
	 *             Timestamp
	 */
	public static Timestamp StringToDateHMS(String str) throws Exception {
		Timestamp time = null;
		time = Timestamp.valueOf(str);
		return time;
	}

	/**
	 * 功能: 根据传入的年月日返回相应的日期对象
	 * 
	 * @param year 年份
	 * @param month 月份
	 * @param day 天
	 * @return Date 日期对象
	 */
	public static Date YmdToDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	/**
	 * 功能: 将日期对象按照MM/dd HH:mm:ss的格式进行转换，返回转换后的字符串
	 * 
	 * @param date 日期对象
	 * @return String 返回值
	 */
	public static String communityDateToString(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MM/dd HH:mm:ss");
		String strDateTime = date == null ? null : formater.format(date);
		return strDateTime;
	}

	public static Date getMaxDateOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(11, calendar.getActualMaximum(11));
			calendar.set(12, calendar.getActualMaximum(12));
			calendar.set(13, calendar.getActualMaximum(13));
			calendar.set(14, calendar.getActualMaximum(14));
			return calendar.getTime();
		}
	}

	public static Date getMinDateOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(11, calendar.getActualMinimum(11));
			calendar.set(12, calendar.getActualMinimum(12));
			calendar.set(13, calendar.getActualMinimum(13));
			calendar.set(14, calendar.getActualMinimum(14));
			return calendar.getTime();
		}
	}

	/**
	 * 功能：返回传入日期对象（date）之后afterDays天数的日期对象
	 * 
	 * @param date 日期对象
	 * @param afterDays 往后天数
	 * @return java.util.Date 返回值
	 */
	public static Date getAfterDay(Date date, int afterDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	// day
	/**
	 * 功能: 返回date1与date2相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int DateDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()) / 3600 / 24 / 1000);
		return i;
	}
    /**
     * 日期加一天
     * @param date
     * @return
     */
	public static String DateAddOneDay(Date date){
		
	     Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
	     date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
         
	    return DateToString(date, DateUtil.DATE);
	}
	/**
	 * 日期减一天
	 * @param date
	 * @return
	 */
	public static String DateMinusOneDay(Date date){
		
	     Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(Calendar.DATE,-1);//把日期往前减一天.整数往后推,负数往前移动 
	     date=calendar.getTime();   //这个时间就是日期往前推一天的结果 
        
	    return DateToString(date, DateUtil.DATE);
	}
	// min
	/**
	 * 功能: 返回date1与date2相差的分钟数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int MinDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()) / 1000 / 60);
		return i;
	}
    
	// second
	/**
	 * 功能: 返回date1与date2相差的秒数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int TimeDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()));
		return i;
	}
	
	
	/**
	 * 在某个时间上 增加分钟
	 * @param date 需要增加的时间
	 * @param minute 增加具体分钟数
	 * @return
	 */
	public static Long timeAddMinute(Date date,int minute){
		
		Calendar afterTime = Calendar.getInstance();  
		afterTime.setTime(date);
	    afterTime.add(Calendar.MINUTE, minute); 
	   
	    Date afterDate = (Date) afterTime.getTime(); 
		
	    return afterDate.getTime();
	}
	 /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
    
    /**
     * 获取当前日期是星期几 例如 星期日 是 1<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDateInternat(Date dt) {
        String[] weekDays = {"1", "2", "3", "4", "5", "6", "7"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK)-1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
    /**
     * 给一个日期返回该日期是多少号
     * @param date
     * @return
     */
    public static String getDate(String date){
    	
    	Date newDate = DateUtil.StringToDate(date);
    	
    	return newDate.getDate()+"";
    }
    /**
	 * 功能：把日期字符串转换成BigInt类型
	 * 
	 */
	public static String  strChangeBigIntYYYYMMDD(String date){
		
		String strBigInt = "";
		if(date == null || date.equals("")){

			return "";
		}
		try {
			
			Date stat = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			
			strBigInt = stat.getTime()+"";
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return strBigInt;
	}
	
	  /**
		 * 功能：把日期字符串转换成BigInt类型
		 * param:type 日期格式
		 * 
		 */
		public static String  strChangeBigInt(String date,String type){
			
			String strBigInt = "";
			
			try {
				if(date != null && !date.equals("")){
					
					Date stat = new SimpleDateFormat(type).parse(date);
					
					strBigInt = stat.getTime()+"";
					
				}
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}

			return strBigInt;
		}
	
	/**
	 * 功能：把BigInt类型转换成日期字符串
	 * param:type 日期格式
	 */
	public static String  bigIntChangeStr(String strBigInt,String type){
		
		String strDate = "0";
		
		Date date=new Date(Long.parseLong(strBigInt));
		
		SimpleDateFormat formater  = new SimpleDateFormat(type);
		
		strDate = formater.format(date);
		
		return strDate;
	}

	/**
	 * 功能：把BigInt类型转换成日期字符串
	 *
	 */
	public static String  bigIntChangeStrYYYYMMDDHHSSMM(String strBigInt){

		String strDate = "0";

		Date date=new Date(Long.parseLong(strBigInt));

		SimpleDateFormat formater  = new SimpleDateFormat(DateUtil.DATE_TIME);

		strDate = formater.format(date);

		return strDate;
	}

	/**
	 * 功能：把BigInt类型转换成日期字符串
	 * 
	 */
	public static String  bigIntChangeStr(String strBigInt){
		
		String strDate = "0";
		
		Date date=new Date(Long.parseLong(strBigInt));
		
		SimpleDateFormat formater  = new SimpleDateFormat("yyyy-MM-dd");
		
		strDate = formater.format(date);
		
		return strDate;
	}
	
	/**
	 * 获取当前系统时间
	 * @return
	 */
	public static String getPresentTime(){
		
		Date date = new Date();
		
	    SimpleDateFormat formater  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
		String strDate = formater.format(date);
		
		return strDate;
	}

	
	/**
	 * 获取当前系统时间
	 * @return
	 */
	public static String getPresentTime(String dateFormater){

		Date date = new Date();

		SimpleDateFormat formater  = new SimpleDateFormat(dateFormater);

		String strDate = formater.format(date);

		return strDate;
	}
	 /**
	  * 判断一个时间是否在某个时间段内
	  * @param nowdate
	  * @param date1
	  * @param date2
	  * @return
	  */
	 public static boolean getDateContrast(String nowdate,String date1,String date2) {
			
			boolean isTrue = false;
		    SimpleDateFormat formater  = new SimpleDateFormat(DateUtil.DATE_TIME);
		   
			try {
				
				long nowTime   =  formater.parse(nowdate).getTime();
				long dateTime1 =  formater.parse(date1).getTime();
				long dateTime2 =  formater.parse(date2).getTime();
				
				if(dateTime1 > dateTime2){
					
					dateTime2 = DateUtil.getAfterDay(DateUtil.StringToDate(date2, DateUtil.DATE_TIME), 1).getTime();
					
				}
				
				if(dateTime1 <= nowTime && nowTime <= dateTime2){
			    	
			    	isTrue = true;
			    }
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
			return isTrue;
		}

	/**
	 * 获取当前系统时间
	 * 日期格式是：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getPresentTimeBigInt(){
		
		Date date = new Date();
		
		Long longDate = date.getTime();
		
		return longDate.toString();
	}
	
	/**
	 * 把时区日期转换成日期格式 
	 * @param dateFormat    转换的格式
	 * @param timeZooneDate 转换日期 
	 * @return 转换后的string
	 */
	public static String timeZoneString(String dateFormat,String timeZooneDate){
		
		String strTime = "";
		
		if(timeZooneDate != null && !timeZooneDate.equals("")){
			
			Date pubDate =new Date(timeZooneDate);
			
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			
			strTime = formatter.format(pubDate);
			
		}
		
		return strTime;
	}

	
	public static long strParseLong(String strBigInt){
		
		long lon = 0;
		if(strBigInt!=null && !strBigInt.equals("")){
			
			lon = Long.parseLong(strBigInt);
		}
		
		return lon;
	}
	
	public static String getDateFormat(Date date,String format){
		
		String dateTime = "";
		
		SimpleDateFormat formater = new SimpleDateFormat(format);
		
		dateTime = formater.format(date);
		
		return dateTime;
	} 
	
	 /**  
	     * 两个时间相差距离多少天多少小时多少分多少秒  
	     * @param str1 时间参数 1 格式：1990-01-01 12:00:00  
	     * @param str2 时间参数 2 格式：2009-01-01 12:00:00  
	     * @return String 返回值为：xx天xx小时xx分xx秒  
	     */  
	    public static String getDistanceTime(String str1, String str2) {   
	    	
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date one;   
	        Date two;   
	        long day = 0;   
	        long hour = 0;   
	        long min = 0;   
	        long sec = 0;   
	       try {   
	            one = df.parse(str1);   
	            two = df.parse(str2); 
	            System.out.println(DateUtil.strChangeBigInt(str1, DateUtil.DATE_TIME));
	            long time1 = one.getTime();  
	            System.out.println(str1+"==="+time1);
	            long time2 = two.getTime();   
	            long diff ;   
	            
	            diff = time2 - time1;   
	           
	            day = diff / (24*24 * 60 * 60 * 1000);   
	            hour = (diff / (60 * 60 * 1000) - day * 24);   
	            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);   
	            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);   
	        } catch (ParseException e) {   
	            e.printStackTrace();   
	        }   
	        return hour + ":" + min + ":" + sec + "";   
	    }  
	    
	    
        public static long getDiffLongTime(String str1, String str2) {   
	    	
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date one;   
	        Date two;   
	        
	        long diff = 0;   
	       try {   
	            one = df.parse(str1);   
	            two = df.parse(str2); 
	            long time1 = one.getTime();  
	            long time2 = two.getTime();   
	            diff = time2 - time1;   
	            
	        } catch (ParseException e) {   
	            e.printStackTrace();   
	        }   
	        return diff;   
	    } 
	    
        /**
         * 把bigint数值 转换成 HH:mm:ss 格式字符串
         * @param str
         * @return
         */
	    public static String StringToHHmmss(String str){
	    	
	    	if(str !=null && !str.equals("")){
	    		
	    		 long day = 0;   
		         long hour = 0;   
		         long min = 0;   
		         long sec = 0;   
		       	 long diff = 0;   
		       	 
		       	 if(str.lastIndexOf(".")!=-1){
		       		 
		       		str =  str.substring(0,str.lastIndexOf("."));
		       	 }
		            
	             diff = Long.parseLong(str);//5184671;
	             
	             day = diff / (24*24 * 60 * 60 * 1000);   
	             hour = (diff / (60 * 60 * 1000) - day * 24);   
	             min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);   
	             sec = (diff/1000-day*24*60*60-hour*60*60-min*60);   
	             
	             String strHHmmss = hour+"";
	             
	             if(hour < 10){
	            	 strHHmmss = "0"+hour;
	             }
	             
	             if(min < 10){
	            	 
	            	 strHHmmss = strHHmmss+ ":0"+min;
	             }else{
	            	 strHHmmss = strHHmmss+ ":"+min;
	             }
	             
	             if(sec < 10){
	            	 strHHmmss = strHHmmss+ ":0"+sec;
	             }else{
	            	 
	            	 strHHmmss = strHHmmss+ ":"+sec;
	             }
	             return strHHmmss;   
	    	}
	    	
	    	return "";
	    	
   }
	    
   /**
    * 获取某日期加上小时分钟秒后的时间
    * @param strDate   原始日期
    * @param strHHmmss 相加时分秒
    * @return
    */
   public static String timeAddHourMinuteSecond(String strDate,String strHHmmss){
	   
 	  Calendar cal= Calendar.getInstance();  
 	  cal.setTime(DateUtil.StringToDate(strDate, DateUtil.DATE_TIME));
 	  
 	  Calendar calHHmmss= Calendar.getInstance();  
 	  calHHmmss.setTime(DateUtil.StringToDate(strHHmmss, "HH:mm:ss"));
 	  
 	  int hourAdd = calHHmmss.get(Calendar.HOUR_OF_DAY);//时  
 	  int minuteAdd = calHHmmss.get(Calendar.MINUTE);//分  
 	  int secondAdd = calHHmmss.get(Calendar.SECOND);//秒 
 	  cal.add(Calendar.HOUR_OF_DAY, hourAdd);//加时  
 	  cal.add(Calendar.MINUTE, minuteAdd);//加分 
 	  cal.add(Calendar.SECOND, secondAdd);//加秒  
      
      SimpleDateFormat sdff=new SimpleDateFormat(DateUtil.DATE_TIME);	
      String sDate=sdff.format(cal.getTime());
      
      return sDate;
   }
   public static String getFcsJobEnquiryTime(String time) throws ParseException {
	   SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss",Locale.ENGLISH);
	   Date date = null;
	   date = sdf.parse(time);
	   return String.valueOf(date.getTime());
   }
   public static String getUUID(){
   	return  UUID.randomUUID().toString();
   }
   
   public static void main(String[] args) throws ParseException {
	  
	 // String s =  DateUtil.getDistanceTime("2015-06-20 13:20:01", "2015-06-22 15:35:03");
	  
	 // System.out.println(s);
	 //  System.out.println(""+day1+"天"+hour1+"小时"+minute1+"分"+second1+"秒");
	   String uuid = UUID.randomUUID().toString();
	   System.out.println(uuid);
	   if (1==2) {


		   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   Date one;
		   Date two;
		   long day = 0;
		   long hour = 0;
		   long min = 0;
		   long sec = 0;
		   long diff = 0;
		   String str1 = "3407184.5000";
		   str1 = str1.substring(0, str1.lastIndexOf("."));
		   diff = Long.parseLong(str1);//5184671;

		   day = diff / (24 * 24 * 60 * 60 * 1000);
		   hour = (diff / (60 * 60 * 1000) - day * 24);
		   min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		   sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		   System.out.println(hour + "小时" + min + "分" + sec + "秒");
		   // 1434684539413 开始时间
		   //1434698102000 //完成时间
		   //180902000
		   String s1 = DateUtil.getDistanceTime(DateUtil.bigIntChangeStr("1434684539413", DateUtil.DATE_TIME), DateUtil.bigIntChangeStr("1434698102000", DateUtil.DATE_TIME));

		   System.out.println(s1);

		   long ce = 5185000 - 5184671;
		   System.out.println(ce);
		   //-------------------------------------------------
		   String date = "00:30:30";

		   Calendar cal = Calendar.getInstance();
		   cal.setTime(DateUtil.StringToDate("2:00:00", "HH:mm:ss"));
    	  /*cal.set(Calendar.HOUR_OF_DAY, 12); 
    	  cal.set(Calendar.MINUTE, 30); 
    	  cal.set(Calendar.SECOND, 30); 
    	  System.out.println(cal.getTime());  *//**   * 时间相加 00:00:30   *//*  
    	  System.out.println(DateUtil.timeZoneString(DateUtil.DATE_TIME, cal.getTime().toString()));*/
		   int hourAdd = 5;//时
		   int minuteAdd = 50;//分
		   int secondAdd = 5;//秒
		   cal.add(Calendar.HOUR_OF_DAY, hourAdd);//加时
		   cal.add(Calendar.MINUTE, minuteAdd);//加分
		   cal.add(Calendar.SECOND, secondAdd);//加秒
		   System.out.println(cal.getTime());

		   SimpleDateFormat sdff = new SimpleDateFormat(DateUtil.DATE_TIME);
		   String sDate = sdff.format(cal.getTime());
		   System.out.println(sDate);
          
           /*  String s2 =  DateUtil.getDistanceTime("1970-01-01 00:00:00", "1970-01-03 03:10:12");
       	  
       	  System.out.println(s2);  */
		   // System.out.println(DateUtil.timeZoneString(DateUtil.DATE_TIME, cal.getTime().toString()));

		   //获取一个日期的小时分钟秒

		   Calendar cal1 = Calendar.getInstance();
		   cal1.setTime(DateUtil.StringToDate("2:04:08", "HH:mm:ss"));

		   System.out.println(cal1.get(Calendar.HOUR_OF_DAY));
		   System.out.println(cal1.get(Calendar.MINUTE));
		   System.out.println(cal1.get(Calendar.SECOND));
	   }
   }

}
