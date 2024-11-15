package global.kajisaab.common.utils;

public class SequentialNumberGenerator {
    private static int counter = 1; // Start from 1

    public static String getNextNumber() {
        if (counter > 999) {
            throw new IllegalStateException("Counter has exceeded the maximum value of 999");
        }

        // Format the number as a three-digit string with leading zeros
        String formattedNumber = String.format("%03d", counter);
        counter++; // Increment the counter
        return formattedNumber;
    }

}
