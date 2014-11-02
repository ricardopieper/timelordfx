package utils;

import java.util.List;

public class Lists {

    public static <T> T getLast(List<T> ls) {

        if (ls.size() > 0) {
            return ls.get(ls.size() - 1);
        }
        return null;

    }

}
