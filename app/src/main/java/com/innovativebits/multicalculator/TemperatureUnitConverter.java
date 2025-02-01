package com.innovativebits.multicalculator;

public class TemperatureUnitConverter {

    // Convert temperature from one unit to another
    public static double convert(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) {
            return value; // If the units are the same, no conversion is needed
        }

        // Step 1: Convert from the input unit to Celsius (base unit)
        double valueInCelsius = toCelsius(value, fromUnit);

        // Step 2: Convert from Celsius to the target unit
        return fromCelsius(valueInCelsius, toUnit);
    }

    // Convert the given temperature to Celsius
    private static double toCelsius(double value, String fromUnit) {
        switch (fromUnit) {
            case "Celsius":
                return value;
            case "Fahrenheit":
                return (value - 32) * 5 / 9;
            case "Kelvin":
                return value - 273.15;
            default:
                throw new IllegalArgumentException("Invalid from unit: " + fromUnit);
        }
    }

    // Convert Celsius to the target temperature unit
    private static double fromCelsius(double value, String toUnit) {
        switch (toUnit) {
            case "Celsius":
                return value;
            case "Fahrenheit":
                return value * 9 / 5 + 32;
            case "Kelvin":
                return value + 273.15;
            default:
                throw new IllegalArgumentException("Invalid to unit: " + toUnit);
        }
    }
}
