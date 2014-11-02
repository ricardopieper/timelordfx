package utils;

public class FileUtils {

    public static String getFileExtension(String filepath) {
        String extension = "";

        int i = filepath.lastIndexOf('.');
        if (i > 0) {
            extension = filepath.substring(i + 1);
        }
        return extension;
    }

}
