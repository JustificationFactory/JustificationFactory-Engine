package fr.axonic.util;

public class StringHelper {

    public static String repeatString(String string, int numberOfRepetitions) {
        if (string != null || numberOfRepetitions >= 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < numberOfRepetitions; i++) {
                builder.append(string);
            }
            return builder.toString();
        } else {
            throw new IllegalArgumentException();
        }
    }

}
