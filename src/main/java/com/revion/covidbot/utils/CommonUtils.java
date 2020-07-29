package com.revion.covidbot.utils;

import org.apache.logging.log4j.util.Strings;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
public class CommonUtils {

    private static DecimalFormat formatter;

    public static Integer stringToNumber(Object value) {
        return Integer.parseInt(CommonUtils.safetyCleanWhiteSpaces(String.valueOf(value)));
    }

    public static String safetyCleanWhiteSpaces(String string) {
        if (string != null) {
            String res = string.replaceAll("(\\s|\\t)", "");
            return res.trim();
        }
        return Strings.EMPTY;
    }

    public static DecimalFormat getDecimalFormatter() {
        return createDecimalFormatter();
    }

    private static DecimalFormat createDecimalFormatter() {
        if (formatter != null)
            return formatter;

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter;
    }

}
