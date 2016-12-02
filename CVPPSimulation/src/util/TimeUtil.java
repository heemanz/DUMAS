package util;

import java.util.Date;

public class TimeUtil {

	public static final long A_DAY = 24 * 60 * 60 * 1000;
	public static final long A_HOUR = 60 * 60 * 1000;
	public static final long HALF_HOUR = 30 * 60 * 1000;
	
	public TimeUtil() {
		// TODO Auto-generated constructor stub
	}

	public static long getMidnightTime(long time) {
		return getMidnightDate(time).getTime();
	}
	
	@SuppressWarnings("deprecation")
	public static Date getMidnightDate(long time) {
		time = (time / 1000) * 1000;
		
		Date date = new Date(time);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		
		return date;
	}
	
	public static Date getYesterdayMidnight(long curTime) {
		long time = curTime - TimeUtil.A_DAY;
		
		return TimeUtil.getMidnightDate(time);
	}
	
	public static Date getTomorrowMidnight(long curTime) {
		long time = curTime + TimeUtil.A_DAY;
		
		return TimeUtil.getMidnightDate(time);
	}
	
	public static boolean isAfter(long curTime, Date date, long afterTime) {
		return curTime > date.getTime() + afterTime;
	}
}
