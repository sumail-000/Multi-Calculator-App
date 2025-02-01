package com.innovativebits.multicalculator;

public class LengthUnitConverter {

    // Convert length units to a base unit (meters)
    public static double convert(double value, String fromUnit, String toUnit) {
        // Step 1: Convert the value to meters (base unit)
        double valueInMeters = toMeters(value, fromUnit);

        // Step 2: Convert the value in meters to the target unit
        return fromMeters(valueInMeters, toUnit);
    }

    // Convert the given unit to meters
    private static double toMeters(double value, String fromUnit) {
        switch (fromUnit) {
            case "Kilometer":
                return value * 1000;
            case "Meter":
                return value;
            case "Centimeter":
                return value / 100;
            case "Millimeter":
                return value / 1000;
            case "Micrometer":
                return value / 1_000_000;
            case "Nanometer":
                return value / 1_000_000_000;
            case "Mile":
                return value * 1609.34;
            case "Yard":
                return value * 0.9144;
            case "Foot":
                return value * 0.3048;
            case "Inch":
                return value * 0.0254;
            default:
                throw new IllegalArgumentException("Invalid from unit: " + fromUnit);
        }
    }

    // Convert meters to the target unit
    private static double fromMeters(double value, String toUnit) {
        switch (toUnit) {
            case "Kilometer":
                return value / 1000;
            case "Meter":
                return value;
            case "Centimeter":
                return value * 100;
            case "Millimeter":
                return value * 1000;
            case "Micrometer":
                return value * 1_000_000;
            case "Nanometer":
                return value * 1_000_000_000;
            case "Mile":
                return value / 1609.34;
            case "Yard":
                return value / 0.9144;
            case "Foot":
                return value / 0.3048;
            case "Inch":
                return value / 0.0254;
            default:
                throw new IllegalArgumentException("Invalid to unit: " + toUnit);
        }
    }
}
