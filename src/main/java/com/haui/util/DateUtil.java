package com.haui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(Date date) {
        if (date == null) return "";
        return DATE_FORMAT.format(date);
    }

    public static Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try {
            return DATE_FORMAT.parse(dateStr.trim());
        } catch (ParseException e) {
            try {
                return SQL_DATE_FORMAT.parse(dateStr.trim());
            } catch (ParseException ex) {
                return null;
            }
        }
    }

    public static java.sql.Date toSqlDate(Date utilDate) {
        if (utilDate == null) return null;
        return new java.sql.Date(utilDate.getTime());
    }
}
