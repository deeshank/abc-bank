package com.abc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    public boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
        return testDate.getTime() >= startDate.getTime() &&
                testDate.getTime() <= endDate.getTime();
    }

    public Date addDays(Date d, int days)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, days);
        Date d1 = new Date();
        d1.setTime( c.getTime().getTime() );
        return d1;
    }

    public Date toDate(String date_str){
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(date_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String toString(Date dt){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(dt);
    }
}
