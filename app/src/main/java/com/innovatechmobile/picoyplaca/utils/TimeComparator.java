package com.innovatechmobile.picoyplaca.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeComparator {

    private static final String INPUT_FORMAT = "HH:mm";
    private final SimpleDateFormat inputParser = new SimpleDateFormat(INPUT_FORMAT, Locale.US);

    //Hours to check Add as many
    enum HOURS {
        MORNING("6:59", "9:30"),
        EVENING("15:59", "19:30");
        private final String start;
        private final String end;

        HOURS(final String start, final String end) {
            this.start = start;
            this.end = end;
        }
    }


    public TimeComparator() {
        //no need to implement
    }

    //comparator of hours
    public boolean timeComparator(Calendar time) {
        int hour = time.get(Calendar.HOUR);
        int minute = time.get(Calendar.MINUTE);
        Date date = parseDate(hour + ":" + minute);
        for (HOURS hoursReview : HOURS.values()) {
            try {
                if (date.after(inputParser.parse(hoursReview.start)) && date.before(inputParser.parse(hoursReview.end))) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}
