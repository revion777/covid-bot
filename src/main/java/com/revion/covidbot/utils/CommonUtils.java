package com.revion.covidbot.utils;

import org.apache.logging.log4j.util.Strings;

import java.text.*;
import java.util.Locale;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
public class CommonUtils {

    public final static DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

    public static Integer stringToNumber(Object value) {
        return Integer.parseInt(safetyCleanWhiteSpaces(String.valueOf(value)));
    }

    public static String safetyCleanWhiteSpaces(String string) {
        if (string != null) {
            String res = string.replaceAll("(\\s|\\t)", "");
            return res.trim();
        }
        return Strings.EMPTY;
    }

}
