import org.apache.commons.lang3.RandomStringUtils;

public final class Utils {


    private Utils() {
    }

    public static String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String randomStation() {
        return RandomStringUtils.randomNumeric(1,23);
    }



}
