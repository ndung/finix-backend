package id.finix.component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Helper {

    public static String getActivationCode(){
        int len = 6;
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(getNumbers().charAt(rnd.nextInt(getNumbers().length())));
        return sb.toString();
    }

    public static String implode(String[] data, String delimiter){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]);
            if (i != data.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    private static String BIG_LETTER = null;
    private static String SMALL_LETTER = null;
    private static String NUMBERS = null;

    public static String getBigLetter() {
        if (BIG_LETTER == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 65; i <= 90; i++) {
                sb.append((char) i);
            }
            BIG_LETTER = sb.toString();
        }
        return BIG_LETTER;
    }

    public static String getSmallLetter() {
        if (SMALL_LETTER == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 97; i <= 122; i++) {
                sb.append((char) i);
            }
            SMALL_LETTER = sb.toString();
        }
        return SMALL_LETTER;
    }

    public static String getNumbers() {
        if (NUMBERS == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 48; i <= 57; i++) {
                sb.append((char) i);
            }
            NUMBERS = sb.toString();
        }
        return NUMBERS;
    }

    private static boolean inRange(int a, int b, int c) {
        return a >= b && a <= c;
    }

    public static boolean containsNumber(String s) {
        // decimal ascii 0 = 48
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (inRange((int) c, 48, 57)) return true;
        }
        return false;
    }

    public static boolean containsBigChar(String s) {
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (inRange((int) c, 65, 90)) return true;
        }
        return false;
    }

    public static boolean containsLowChar(String s) {
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (inRange((int) c, 97, 122)) return true;
        }
        return false;
    }

    public static String getOrderCode(long orderCount) {
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyMMddHH").format(new Date()));
        sb.append(String.format("%04d", orderCount % 9999));
        return sb.toString();
    }

    public static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    public static Date addMinutesToDate(long minutes, Date beforeTime) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    public static Date minMinutesToDate(long minutes, Date beforeTime) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
        long curTimeInMs = beforeTime.getTime();
        Date afterMinusMins = new Date(curTimeInMs - (minutes * ONE_MINUTE_IN_MILLIS));
        return afterMinusMins;
    }

}
