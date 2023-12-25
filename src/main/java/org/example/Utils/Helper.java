package org.example.Utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.*;

public class Helper {
    public static Date addDureation(Date date, int mins) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, mins);
        return calendar.getTime();
    }

    public static int findDifferenceInMin(Date start_date, Date end_date)
    {
        // Calculate time difference in milliseconds
        long difference_In_Time
                = end_date.getTime() - start_date.getTime();

        long difference_In_Minutes
                = (difference_In_Time
                / (1000 * 60));
        return Integer.parseInt(String.valueOf(difference_In_Minutes));

    }

    public static Date stringToDate(String dateInString) throws ParseException {
        return DateUtils.parseDate(dateInString,
                "yyyy-MM-dd HH:mm", "yyyy-MM-dd");
    }

    public static<T> void print(ArrayList<T> o){
        if(o==null) return;
        for(Object x: o){
            if(o == null) continue;
            System.out.println(x.toString());
        }
    }

    static String getMonth(String m) {
        switch(m){
            case("Jan"): return "01";
            case("Feb"): return "02";
            case("Mar"): return "03";
            case("Apr"): return "04";
            case("May"): return "05";
            case("Jun"): return "06";
            case("Jul"): return "07";
            case("Aug"): return "08";
            case("Sep"): return "09";
            case("Oct"): return "10";
            case("Nov"): return "11";
            case("Dec"): return "12";
            default: return null;
        }
    }

    public static String getDateToString(Date d){
        String[] s = d.toString().split(" ");
        return s[5]+"-"+getMonth(s[1])+"-"+s[2]+" "+ s[3].substring(0,5);
    }

    public static Date getTime(Date date1, Date dateTime1) throws ParseException {
        String dateString = getDateToString(date1).split(" ")[0];
        String timeString = getDateToString(dateTime1).split(" ")[1];

        return Helper.stringToDate(dateString + " " + timeString);
    }
}