package com.innovatechmobile.picoyplaca.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {

    private CurrentDate() {
    }

    public static String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        // get current date time with Date()
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        // get current date time with Date()
        return dateFormat.format(date);
    }
}
