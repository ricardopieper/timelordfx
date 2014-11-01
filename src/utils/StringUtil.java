package utils;

public class StringUtil {

    public static boolean isEmpty(Object s) {
        if (s == null) {
            return false;
        }
        if (s instanceof String) {
            String str = (String) s;

            return str.isEmpty();
        }else{
            return false;
        }

    }
}
