package com.innovativebits.multicalculator;

public class TimeUnitConverter {

    // Convert time units to a base unit (seconds)
    public static double convert(double value, String fromUnit, String toUnit) {
        // Step 1: Convert the value to seconds (base unit)
        double valueInSeconds = toSeconds(value, fromUnit);

        // Step 2: Convert the value in seconds to the target unit
        return fromSeconds(valueInSeconds, toUnit);
    }

    // Convert the given unit to seconds
    private static double toSeconds(double value, String fromUnit) {
        switch (fromUnit) {
            case "Second":
                return value;
            case "Millisecond":
                return value / 1000;
            case "Microsecond":
                return value / 1_000_000;
            case "Nanosecond":
                return value / 1_000_000_000;
            case "Minute":
                return value * 60;
            case "Hour":
                return value * 3600;
            case "Day":
                return value * 86400;
            case "Week":
                return value * 604800;
            case "Month":
                return value * 2_629_746; // Approximate month length in seconds
            case "Year":
                return value * 31_556_952; // Approximate year length in seconds
            default:
                throw new IllegalArgumentException("Invalid from unit: " + fromUnit);
        }
    }

    // Convert seconds to the target unit
    private static double fromSeconds(double value, String toUnit) {
        switch (toUnit) {
            case "Second":
                return value;
            case "Millisecond":
                return value * 1000;
            case "Microsecond":
                return value * 1_000_000;
            case "Nanosecond":
                return value * 1_000_000_000;
            case "Minute":
                return value / 60;
            case "Hour":
                return value / 3600;
            case "Day":
                return value / 86400;
            case "Week":
                return value / 604800;
            case "Month":
                return value / 2_629_746; // Approximate month length in seconds
            case "Year":
                return value / 31_556_952; // Approximate year length in seconds
            default:
                throw new IllegalArgumentException("Invalid to unit: " + toUnit);
        }
    }
}
