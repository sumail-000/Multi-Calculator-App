package com.innovativebits.multicalculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BinaryConverterActivity extends AppCompatActivity {

    private EditText inputValue;
    private AutoCompleteTextView inputTypeSpinner, outputTypeSpinner;
    private EditText resultTextView;
    private Button convertButton, resetButton, swapButton, copyButton;
    private String[] numberSystems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_binary_converter);

        initializeViews();
        setupSpinners();
        setupButtons();
        setupInputValidation();
    }

    private void initializeViews() {
        inputValue = findViewById(R.id.inputValue);
        inputTypeSpinner = findViewById(R.id.inputTypeSpinner);
        outputTypeSpinner = findViewById(R.id.outputTypeSpinner);
        resultTextView = findViewById(R.id.resultTextView);
        convertButton = findViewById(R.id.convertButton);
        resetButton = findViewById(R.id.resetButton);
        swapButton = findViewById(R.id.swapButton);
        copyButton = findViewById(R.id.copyButton);

        // Limited to Binary, Octal, Decimal, and Hexadecimal
        numberSystems = new String[]{
                "2 (binary)", "8 (octal)", "10 (decimal)", "16 (hex)"
        };
    }

    private void setupSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                numberSystems
        );

        inputTypeSpinner.setAdapter(adapter);
        outputTypeSpinner.setAdapter(adapter);

        // Set default selections
        inputTypeSpinner.setText(numberSystems[2], false);  // 10 (decimal)
        outputTypeSpinner.setText(numberSystems[3], false); // 16 (hex)
    }

    private void setupButtons() {
        convertButton.setOnClickListener(v -> convertNumber());
        resetButton.setOnClickListener(v -> resetFields());
        swapButton.setOnClickListener(v -> swapBases());
        copyButton.setOnClickListener(v -> copyResult());
    }

    private void setupInputValidation() {
        inputValue.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = start; i < end; i++) {
                            char c = source.charAt(i);
                            if (isValidChar(c, getBase(inputTypeSpinner.getText().toString()))) {
                                builder.append(c);
                            }
                        }
                        return builder.toString();
                    }
                }
        });

        inputTypeSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                inputValue.setText(""); // Clear input when base changes
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    private boolean isValidChar(char c, int base) {
        if (base == 2) {  // Binary
            return c == '0' || c == '1';
        } else if (base == 8) {  // Octal
            return Character.isDigit(c) && Character.digit(c, 10) < 8;
        } else if (base == 10) {  // Decimal
            return Character.isDigit(c);
        } else if (base == 16) {  // Hexadecimal
            return Character.isDigit(c) ||
                    (Character.toUpperCase(c) >= 'A' && Character.toUpperCase(c) <= 'F');
        }
        return false;
    }

    private void convertNumber() {
        String input = inputValue.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int fromBase = getBase(inputTypeSpinner.getText().toString());
            int toBase = getBase(outputTypeSpinner.getText().toString());

            // Convert to decimal first
            int decimalValue = Integer.parseInt(input, fromBase);

            // Then convert to target base
            String result = Integer.toString(decimalValue, toBase).toUpperCase();
            resultTextView.setText(result);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input for selected base", Toast.LENGTH_SHORT).show();
            resultTextView.setText("");
        }
    }

    private void resetFields() {
        inputValue.setText("");
        resultTextView.setText("");
        inputTypeSpinner.setText(numberSystems[2], false); // Reset to decimal
        outputTypeSpinner.setText(numberSystems[3], false); // Reset to hex
    }

    private void swapBases() {
        String fromBase = inputTypeSpinner.getText().toString();
        String toBase = outputTypeSpinner.getText().toString();

        inputTypeSpinner.setText(toBase, false);  // false prevents filtering
        outputTypeSpinner.setText(fromBase, false);

        // Clear the input and result
        inputValue.setText("");
        resultTextView.setText("");
    }

    private void copyResult() {
        String result = resultTextView.getText().toString();
        if (!result.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Converted Result", result);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    private int getBase(String baseString) {
        return Integer.parseInt(baseString.split("\\s+")[0]);
    }
}