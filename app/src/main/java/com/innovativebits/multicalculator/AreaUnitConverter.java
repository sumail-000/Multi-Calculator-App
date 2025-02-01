package com.innovativebits.multicalculator;

public class AreaUnitConverter {

    // Convert area units to a base unit (square meters)
    public static double convert(double value, String fromUnit, String toUnit) {
        // Step 1: Convert the value to square meters (base unit)
        double valueInSquareMeters = toSquareMeters(value, fromUnit);

        // Step 2: Convert the value in square meters to the target unit
        return fromSquareMeters(valueInSquareMeters, toUnit);
    }

    // Convert the given unit to square meters
    private static double toSquareMeters(double value, String fromUnit) {
        switch (fromUnit) {
            case "Square Kilometer":
                return value * 1_000_000;
            case "Square Meter":
                return value;
            case "Square Centimeter":
                return value / 10_000;
            case "Square Millimeter":
                return value / 1_000_000;
            case "Hectare":
                return value * 10_000;
            case "Acre":
                return value * 4046.86;
            case "Square Mile":
                return value * 2_589_988;
            case "Square Yard":
                return value * 0.836127;
            case "Square Foot":
                return value * 0.092903;
            case "Square Inch":
                return value * 0.00064516;
            default:
                throw new IllegalArgumentException("Invalid from unit: " + fromUnit);
        }
    }

    // Convert square meters to the target unit
    private static double fromSquareMeters(double value, String toUnit) {
        switch (toUnit) {
            case "Square Kilometer":
                return value / 1_000_000;
            case "Square Meter":
                return value;
            case "Square Centimeter":
                return value * 10_000;
            case "Square Millimeter":
                return value * 1_000_000;
            case "Hectare":
                return value / 10_000;
            case "Acre":
                return value / 4046.86;
            case "Square Mile":
                return value / 2_589_988;
            case "Square Yard":
                return value / 0.836127;
            case "Square Foot":
                return value / 0.092903;
            case "Square Inch":
                return value / 0.00064516;
            default:
                throw new IllegalArgumentException("Invalid to unit: " + toUnit);
        }
    }
}
