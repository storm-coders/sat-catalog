package com.conta.cloud.sat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {
    private final static String DT_FORMAT = "dd/MM/yyyy";

    public static Date fromString(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DT_FORMAT);
            return format.parse(date);
        } catch (ParseException pe) {
            log.error("Unable to parse date {}", date, pe);
            return null;
        } catch (NullPointerException n) {
            log.error("Can not parse null values");
            return null;
        }
    }

    public static String fromDate(Date date) {
        if(date==null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(DT_FORMAT);
            return format.format(date);
        }
    }
}