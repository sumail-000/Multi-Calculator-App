package com.innovativebits.multicalculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;

import com.innovativebits.multicalculator.R;

public class UnitConverterActivity extends AppCompatActivity {

    private AutoCompleteTextView unitTypeSpinner, fromUnitSpinner, toUnitSpinner;
    private EditText fromValue;
    private TextView convertedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_unit_converter);

        // Initialize views
        unitTypeSpinner = findViewById(R.id.unitTypeSpinner);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        fromValue = findViewById(R.id.fromValue);
        convertedValue = findViewById(R.id.convertedValue);

        // Set up unit type adapter
        ArrayAdapter<CharSequence> unitTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.unit_types, R.layout.dropdown_menu_popup_item);
        unitTypeSpinner.setAdapter(unitTypeAdapter);

        // Make AutoCompleteTextView non-editable
        unitTypeSpinner.setKeyListener(null);
        fromUnitSpinner.setKeyListener(null);
        toUnitSpinner.setKeyListener(null);

        // Set listener for unit type selection
        unitTypeSpinner.setOnItemClickListener((parent, view, position, id) -> {
            String selectedType = parent.getItemAtPosition(position).toString();
            updateUnitSpinners(selectedType);
        });

        // Set listener for conversions
        fromValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                convertUnits();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        fromUnitSpinner.setOnItemClickListener((parent, view, position, id) -> {
            convertUnits();
        });

        toUnitSpinner.setOnItemClickListener((parent, view, position, id) -> {
            convertUnits();
        });
    }

    private void updateUnitSpinners(String unitType) {
        int unitArrayId;
        switch (unitType) {
            case "Length":
                unitArrayId = R.array.length_units;
                break;
            case "Area":
                unitArrayId = R.array.area_units;
                break;
            case "Data Transfer Rate":
                unitArrayId = R.array.data_transfer_rate_units;
                break;
            case "Digital Storage":
                unitArrayId = R.array.digital_storage_units;
                break;
            case "Mass":
                unitArrayId = R.array.mass_units;
                break;
            case "Temperature":
                unitArrayId = R.array.temperature_units;
                break;
            case "Time":
                unitArrayId = R.array.time_units;
                break;
            default:
                unitArrayId = R.array.length_units;
                break;
        }

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(this,
                unitArrayId, R.layout.dropdown_menu_popup_item);
        fromUnitSpinner.setAdapter(unitAdapter);
        toUnitSpinner.setAdapter(unitAdapter);

        // Reset selections
        fromUnitSpinner.setText(unitAdapter.getItem(0).toString(), false);
        toUnitSpinner.setText(unitAdapter.getItem(0).toString(), false);
    }

    private void convertUnits() {
        try {
            if (fromValue.getText().toString().isEmpty()) {
                convertedValue.setText("0");
                return;
            }

            double inputValue = Double.parseDouble(fromValue.getText().toString());
            String fromUnit = fromUnitSpinner.getText().toString();
            String toUnit = toUnitSpinner.getText().toString();
            String unitType = unitTypeSpinner.getText().toString();

            double result;
            switch (unitType) {
                case "Length":
                    result = LengthUnitConverter.convert(inputValue, fromUnit, toUnit);
                    break;
                case "Area":
                    result = AreaUnitConverter.convert(inputValue, fromUnit, toUnit);
                    break;
                case "Temperature":
                    result = TemperatureUnitConverter.convert(inputValue, fromUnit, toUnit);
                    break;
                case "Mass":
                    result = MassUnitConverter.convert(inputValue, fromUnit, toUnit);
                    break;
                case "Time":
                    result = TimeUnitConverter.convert(inputValue, fromUnit, toUnit);
                    break;
                case "Digital Storage":
                    result = DigitalStorageUnitConverter.convert(inputValue, fromUnit, toUnit);
                    break;
                case "Data Transfer Rate":
                    result = DataTransferRateUnitConverter.convert(inputValue, fromUnit, toUnit);
                    break;
                default:
                    convertedValue.setText("Unsupported unit type");
                    return;
            }

            // Format the result to a reasonable number of decimal places
            String formattedResult;
            if (result == Math.floor(result)) {
                formattedResult = String.format("%.0f", result); // No decimal places if result is a whole number
            } else {
                formattedResult = String.format("%.4f", result); // Keep four decimal places for non-whole numbers
            }

            convertedValue.setText(formattedResult);
        } catch (NumberFormatException e) {
            convertedValue.setText("Invalid input");
        } catch (IllegalArgumentException e) {
            convertedValue.setText(e.getMessage());
        }
    }
}