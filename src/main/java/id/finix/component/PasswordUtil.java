package id.finix.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PasswordUtil {

    private static Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

    private static final String SALT_1  = "xuiqryl239jldfr23";
    private static final String SALT_2  = "jlippomnbyu269nkg";
    private static final String SALT_3  = "09j2kd72jambhj1u0";
    private static final String APP_KEY = "id.finix";

    private static String LETTER_SMALL = Helper.getSmallLetter();

    private static String LETTER_BIG = Helper.getBigLetter();

    private static final int MIN_LENGTH = 8;

    public static String create() {
        int len = 8;
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        sb.append(LETTER_BIG.charAt(rnd.nextInt(LETTER_BIG.length())));
        sb.append(Helper.getNumbers().charAt(rnd.nextInt(Helper.getNumbers().length())));
        for (int i = 2; i < len; i++)
            sb.append(LETTER_SMALL.charAt(rnd.nextInt(LETTER_SMALL.length())));
        return sb.toString();
    }

    public static String encode(String raw) {
        String r1 = raw.substring(0, 2);
        String r2 = raw.substring(2, 4);
        String r3 = raw.substring(4);
        StringBuilder sb = new StringBuilder();
        sb.append(r1).append(SALT_1).append(r2).append(SALT_2).append(r3).append(SALT_3);
        return md5(sb.toString());
    }

    public static String md5(String raw) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        md.update(raw.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sBuilder.append(Integer.toString(bytes[i] & 0xff));
        }
        return sBuilder.toString();
    }

    public static boolean match(String raw, String encoded) {
        if (encoded == null)
            return false;
        if (raw == null)
            return false;
        return encode(raw).equals(encoded);
    }

    public static String isStrong(String plainPassword) {
        if (plainPassword.length() < MIN_LENGTH)
            return "Password too short. Minimum " + MIN_LENGTH + " characters";
        else
            return null;
//		else if (!Helper.containsNumber(plainPassword))
//			return "Password should contain NUMBER (0-9).";
//		else if (!Helper.containsLowChar(plainPassword))
//			return "Password should contain SMALL LETTER (a-z).";
//		else if (!Helper.containsBigChar(plainPassword))
//			return "Password should contain BIG LETTER (A-Z).";
//		else
//			return null;
    }

    private static boolean contains(String src, char[] chars) {
        for (char c : chars) {
            if (src.indexOf(c) < 0) return false;
        }
        return true;
    }

    public static String md5Hash(String raw) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        md.update(raw.getBytes());
        byte[] bytes = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();

    }

    public static boolean checkPublicKey(String publicKey, String id, String password) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(date);
        String generatedKey = md5Hash(id + password + today + APP_KEY);
        return publicKey.equals(generatedKey);
    }
}
