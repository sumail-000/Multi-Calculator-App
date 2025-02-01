package com.innovativebits.multicalculator;

public class DigitalStorageUnitConverter {

    // Convert digital storage units to a base unit (bytes)
    public static double convert(double value, String fromUnit, String toUnit) {
        // Step 1: Convert the value to bytes (base unit)
        double valueInBytes = toBytes(value, fromUnit);

        // Step 2: Convert the value in bytes to the target unit
        return fromBytes(valueInBytes, toUnit);
    }

    // Convert the given unit to bytes
    private static double toBytes(double value, String fromUnit) {
        switch (fromUnit) {
            case "Byte":
                return value;
            case "Kilobyte":
                return value * 1024;
            case "Megabyte":
                return value * 1024 * 1024;
            case "Gigabyte":
                return value * 1024 * 1024 * 1024;
            case "Terabyte":
                return value * 1024L * 1024 * 1024 * 1024;
            case "Petabyte":
                return value * 1024L * 1024 * 1024 * 1024 * 1024;
            case "Bit":
                return value / 8;
            case "Kilobit":
                return value * 1024 / 8;
            case "Megabit":
                return value * 1024 * 1024 / 8;
            case "Gigabit":
                return value * 1024 * 1024 * 1024 / 8;
            case "Terabit":
                return value * 1024L * 1024 * 1024 * 1024 / 8;
            case "Petabit":
                return value * 1024L * 1024 * 1024 * 1024 * 1024 / 8;
            default:
                throw new IllegalArgumentException("Invalid from unit: " + fromUnit);
        }
    }

    // Convert bytes to the target unit
    private static double fromBytes(double value, String toUnit) {
        switch (toUnit) {
            case "Byte":
                return value;
            case "Kilobyte":
                return value / 1024;
            case "Megabyte":
                return value / (1024 * 1024);
            case "Gigabyte":
                return value / (1024 * 1024 * 1024);
            case "Terabyte":
                return value / (1024L * 1024 * 1024 * 1024);
            case "Petabyte":
                return value / (1024L * 1024 * 1024 * 1024 * 1024);
            case "Bit":
                return value * 8;
            case "Kilobit":
                return value * 8 / 1024;
            case "Megabit":
                return value * 8 / (1024 * 1024);
            case "Gigabit":
                return value * 8 / (1024 * 1024 * 1024);
            case "Terabit":
                return value * 8 / (1024L * 1024 * 1024 * 1024);
            case "Petabit":
                return value * 8 / (1024L * 1024 * 1024 * 1024 * 1024);
            default:
                throw new IllegalArgumentException("Invalid to unit: " + toUnit);
        }
    }
}
