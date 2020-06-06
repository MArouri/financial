package edu.birzeit.financial.util;

import java.util.regex.Pattern;

public class GenericUtil {

    private static final Pattern PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    // Check of a string is an integer
    public static boolean isNumeric(String strNum) {

        if (strNum == null) {
            return false;
        }
        return PATTERN.matcher(strNum).matches();
    }
}
