package com.sapient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.exeption.ApplicationException;

import lombok.extern.slf4j.Slf4j;
import static com.sapient.constants.Constants.*;

@Slf4j
public class CommonUtil {
	
	
	public static Map<String, List<Calendar>> periodMap = new HashMap<String, List<Calendar>>();
	
	static{
		String string1 = "11:59:59";
	    Date time1 = null;
		try {
			time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		}
		Calendar noonStartTime = Calendar.getInstance();
	    noonStartTime.setTime(time1);
	    noonStartTime.add(Calendar.DATE, 1);

	    String string2 = "15:59:59";
	    Date time2 = null;
		try {
			time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		}
	    Calendar noonEndTime = Calendar.getInstance();
	    noonEndTime.setTime(time2);
	    noonEndTime.add(Calendar.DATE, 1);
	    
	    List<Calendar> noonRange = new ArrayList<Calendar>();
	    noonRange.add(noonStartTime);
	    noonRange.add(noonEndTime);
	    
	    periodMap.put(DAY_PART_NOON, noonRange);
	}
	
	
	public static boolean isTimeFallsInTheRange(String timeStr, String periodName){
		boolean isWithinRange = false;
		try {
		    String inputTimeStr = timeStr + ":00";
		    Date d = new SimpleDateFormat("HH:mm:ss").parse(inputTimeStr);
		    Calendar inputTimeCalendar = Calendar.getInstance();
		    inputTimeCalendar.setTime(d);
		    inputTimeCalendar.add(Calendar.DATE, 1);
	
		    Date inputDateTime = inputTimeCalendar.getTime();
		    if (inputDateTime.after(periodMap.get(periodName).get(0).getTime()) && inputDateTime.before(periodMap.get(periodName).get(1).getTime())) {
		    	isWithinRange = true;
		    }
		} catch (ParseException e) {
		   throw new ApplicationException("Failed while calculating for offer time", "Failed while calculating for offer time");
		}
		return isWithinRange;
	}
	
	
	public static <T> List<T> safe( List<T> other ) {
	    return other == null ? Collections.EMPTY_LIST : other;
	}
	
	
	public static boolean  isDateInPast(LocalDate date) {
		return date.isBefore(LocalDate.now());
	}
	
	public static boolean  isTimeInPastToday(LocalDate date, String time) {
		return date.isEqual(LocalDate.now()) && LocalTime.now().isAfter(LocalTime.parse(time));
	}
	
	

}
