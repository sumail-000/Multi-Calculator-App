package com.innovativebits.multicalculator;

public class MassUnitConverter {

    // Convert mass units to a base unit (grams)
    public static double convert(double value, String fromUnit, String toUnit) {
        // Step 1: Convert the value to grams (base unit)
        double valueInGrams = toGrams(value, fromUnit);

        // Step 2: Convert the value in grams to the target unit
        return fromGrams(valueInGrams, toUnit);
    }

    // Convert the given unit to grams
    private static double toGrams(double value, String fromUnit) {
        switch (fromUnit) {
            case "Gram":
                return value;
            case "Kilogram":
                return value * 1000;
            case "Milligram":
                return value / 1000;
            case "Microgram":
                return value / 1_000_000;
            case "Metric Ton":
                return value * 1_000_000;
            case "Long Ton":
                return value * 1_016_046.91;
            case "Short Ton":
                return value * 907_184.74;
            case "Pound":
                return value * 453.59237;
            case "Ounce":
                return value * 28.3495231;
            case "Carrat":
                return value * 0.2;
            case "Atomic Mass Unit":
                return value * 1.66053906660e-24;
            default:
                throw new IllegalArgumentException("Invalid from unit: " + fromUnit);
        }
    }

    // Convert grams to the target unit
    private static double fromGrams(double value, String toUnit) {
        switch (toUnit) {
            case "Gram":
                return value;
            case "Kilogram":
                return value / 1000;
            case "Milligram":
                return value * 1000;
            case "Microgram":
                return value * 1_000_000;
            case "Metric Ton":
                return value / 1_000_000;
            case "Long Ton":
                return value / 1_016_046.91;
            case "Short Ton":
                return value / 907_184.74;
            case "Pound":
                return value / 453.59237;
            case "Ounce":
                return value / 28.3495231;
            case "Carrat":
                return value / 0.2;
            case "Atomic Mass Unit":
                return value / 1.66053906660e-24;
            default:
                throw new IllegalArgumentException("Invalid to unit: " + toUnit);
        }
    }
}
