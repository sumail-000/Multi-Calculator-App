package com.innovativebits.multicalculator;

public class DataTransferRateUnitConverter {

    // Convert data transfer rate units to a base unit (bits per second)
    public static double convert(double value, String fromUnit, String toUnit) {
        // Step 1: Convert the value to bits per second (bps)
        double valueInBps = toBitsPerSecond(value, fromUnit);

        // Step 2: Convert the value in bps to the target unit
        return fromBitsPerSecond(valueInBps, toUnit);
    }

    // Convert the given unit to bits per second (bps)
    private static double toBitsPerSecond(double value, String fromUnit) {
        switch (fromUnit) {
            case "bps":
                return value;
            case "Kbps":
                return value * 1_000;
            case "Mbps":
                return value * 1_000_000;
            case "Gbps":
                return value * 1_000_000_000;
            case "Tbps":
                return value * 1_000_000_000_000L;
            case "Bps":
                return value * 8;
            case "KBps":
                return value * 8_000;
            case "MBps":
                return value * 8_000_000;
            case "GBps":
                return value * 8_000_000_000L;
            case "TBps":
                return value * 8_000_000_000_000L;
            default:
                throw new IllegalArgumentException("Invalid from unit: " + fromUnit);
        }
    }

    // Convert bits per second (bps) to the target unit
    private static double fromBitsPerSecond(double value, String toUnit) {
        switch (toUnit) {
            case "bps":
                return value;
            case "Kbps":
                return value / 1_000;
            case "Mbps":
                return value / 1_000_000;
            case "Gbps":
                return value / 1_000_000_000;
            case "Tbps":
                return value / 1_000_000_000_000L;
            case "Bps":
                return value / 8;
            case "KBps":
                return value / 8_000;
            case "MBps":
                return value / 8_000_000;
            case "GBps":
                return value / 8_000_000_000L;
            case "TBps":
                return value / 8_000_000_000_000L;
            default:
                throw new IllegalArgumentException("Invalid to unit: " + toUnit);
        }
    }
}
