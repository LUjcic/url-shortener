package hr.lujcic.urlshortener.utils;

import java.util.Random;

/**
 * Used in generating random values that might be needed
 */
public class RandomUtils {

    /**
     * Generates a string between given character limits of given length
     * @param leftLimit left limit of character code
     * @param rightLimit right limit of character code
     * @param length length of generated string
     * @return generated string
     */
    public static String generateString(int leftLimit, int rightLimit, int length) {
        Random random = new Random();
        if (leftLimit > rightLimit || length < 1) throw new IllegalArgumentException();

        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
