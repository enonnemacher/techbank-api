package com.wipro.techbank.Utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public class Utils {

    private static final Integer NUMBER_TO_EXPIRATION = 5;

    public static final Integer LENGTH_CARD_NUMBER = 16;
    public static final Integer LENGTH_SECURITY_CODE = 3;

    public static LocalDateTime generateExpirationDate() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, NUMBER_TO_EXPIRATION);
        return  now.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();
    }

    public static String generateNumbers(int limit) {
        StringBuilder number = new StringBuilder();
        int range = 10;

        for (int index = 0; index < limit; index++) {
            int random = (int) (Math.random() * range);
            if (index % 4 == 0 && index > 0) {
                number.append(" ");
            }
            number.append(random);
        }
        return number.toString();
    }
}
