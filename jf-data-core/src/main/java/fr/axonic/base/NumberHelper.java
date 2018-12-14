package fr.axonic.base;

public class NumberHelper {

    public static double doubleValue(Number number) {
        double result = 0;
        if (number != null) {
            if (number instanceof Integer) {
                result = number.intValue();
            } else if (number instanceof Double) {
                result = number.doubleValue();
            } else if (number instanceof Long) {
                result = number.longValue();
            } else {
                throw new IllegalArgumentException("Unsupported Number type");
            }
        } else {
            throw new IllegalArgumentException("");
        }
        return result;
    }

    public static boolean equals(Number number1, Number number2) {

        boolean result = false;

        if (number1 == number2) {
            result = true;
        } else if (number1 == null || number2 == null) {
            result = false;
        } else if (number1.getClass().equals(number2.getClass())) {
            result = number1.equals(number2);
        } else {
            result = doubleValue(number1) == doubleValue(number2);
        }

        return result;

    }

}
