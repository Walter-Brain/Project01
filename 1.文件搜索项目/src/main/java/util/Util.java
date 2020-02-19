package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User:S-
 * Date:2020/1/4-16:24
 * Version: 1.0
 **/

public class Util {
    static final String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static DateFormat DATE_FORMAT = new SimpleDateFormat(DATA_PATTERN);
    private static final String[] SIZE_NAME = {"B", "KB", "MB", "GB"};

    public static String parseSize(Long size) {

        int n = 0;
        while (size >= 1024) {
            size = size / 1024;
            n++;
            if (n == 3) {
                break;
            }
        }
        return size + SIZE_NAME[n];
    }


    public static String parseDate(Long lastModified) {
        return DATE_FORMAT.format(new Date(lastModified));
    }
}
