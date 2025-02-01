package com.innovativebits.multicalculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.text.method.TransformationMethod;
import android.graphics.Rect;

public class SimpleCalculatorActivity extends AppCompatActivity {

    private TextView displayTextView;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_simple_calculator);

        // Initialize display views
        displayTextView = findViewById(R.id.displayTextView);
        resultTextView = findViewById(R.id.resultTextView);

        // Set up button click listeners
        setButtonListeners();
    }

    private void setButtonListeners() {
        // Number buttons including decimal
        int[] numberButtons = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonDot
        };

        // Handle number and decimal inputs
        View.OnClickListener numberClickListener = v -> {
            Button button = (Button) v;
            String currentText = displayTextView.getText().toString();

            // Handle decimal point
            if (button.getId() == R.id.buttonDot) {
                // Allow decimal only if not already present
                if (!currentText.contains(".")) {
                    displayTextView.setText(currentText + ".");
                }
            } else {
                String newNumber = button.getText().toString();

                // Handle first number entry or after an operator
                if (currentText.equals("0") || currentText.equals("-")) {
                    displayTextView.setText(currentText.equals("-") ? "-" + newNumber : newNumber);
                } else {
                    displayTextView.setText(currentText + newNumber);
                }
            }

            updateRealTimeResult();
        };

        // Set number button click listeners
        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(numberClickListener);
        }

        // Sign toggle button
        findViewById(R.id.buttonSign).setOnClickListener(v -> {
            String currentText = displayTextView.getText().toString();
            if (currentText.startsWith("-")) {
                displayTextView.setText(currentText.substring(1));
            } else {
                displayTextView.setText("-" + currentText);
            }
            updateRealTimeResult();
        });

        // Operator buttons
        setupOperatorButton(R.id.buttonAdd, "+");
        setupOperatorButton(R.id.buttonSubtract, "-");
        setupOperatorButton(R.id.buttonMultiply, "×");
        setupOperatorButton(R.id.buttonDivide, "÷");
        setupOperatorButton(R.id.buttonPercent, "%");
        setupOperatorButton(R.id.buttonPower, "^");

        // Equals button
        findViewById(R.id.buttonEquals).setOnClickListener(v -> calculateFinalResult());

        // Clear button
        findViewById(R.id.buttonClear).setOnClickListener(v -> {
            displayTextView.setText("0");
            resultTextView.setText("");
        });

        // Delete button
        findViewById(R.id.buttonDelete).setOnClickListener(v -> {
            String currentText = displayTextView.getText().toString();

            if (currentText.length() > 1) {
                displayTextView.setText(currentText.substring(0, currentText.length() - 1));
            } else {
                displayTextView.setText("0");
            }
            updateRealTimeResult();
        });

        findViewById(R.id.buttonSquareRoot).setOnClickListener(v -> {
            String currentDisplay = displayTextView.getText().toString();

            // If display is "0", add sqrt symbol without showing 0
            if (currentDisplay.equals("0")) {
                displayTextView.setText(currentDisplay + "√");
                // Use a custom text transformation to hide the leading zero
                displayTextView.setTransformationMethod(new TransformationMethod() {
                    @Override
                    public CharSequence getTransformation(CharSequence source, View view) {
                        return source.toString().replaceFirst("^0", "");
                    }

                    @Override
                    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocused) {
                        // Required method, but we don't need to do anything here
                    }
                });
            }
            // Append √ only if the last character is not already √ for other cases
            else if (!currentDisplay.endsWith("√")) {
                displayTextView.setText(currentDisplay + "√");
            }
        });

        findViewById(R.id.buttonFactorial).setOnClickListener(v -> {
            double value = Double.parseDouble(displayTextView.getText().toString());
            displayTextView.setText(String.valueOf(calculateFactorial(value)));
            updateRealTimeResult();
        });
    }

    private void setupOperatorButton(int buttonId, String operator) {
        findViewById(buttonId).setOnClickListener(v -> {
            String currentDisplay = displayTextView.getText().toString();

            // Special handling for minus sign at the start of an expression
            if (currentDisplay.equals("0") && operator.equals("-")) {
                displayTextView.setText("-");
                return;
            }

            // For other cases, append the operator if not already ending with an operator
            if (!isLastCharOperator(currentDisplay)) {
                displayTextView.setText(currentDisplay + operator);
                updateRealTimeResult();
            }
        });
    }

    private boolean isLastCharOperator(String expression) {
        if (expression.isEmpty()) return false;

        String lastChar = expression.substring(expression.length() - 1);
        return "+-×÷%^".contains(lastChar);
    }

    private void updateRealTimeResult() {
        String fullExpression = displayTextView.getText().toString();

        try {
            String infixExpression = fullExpression
                    .replace("×", "*")
                    .replace("÷", "/");

            double result = evaluateExpression(infixExpression);

            // Check if the result is a whole number
            if (result == (long) result) {
                resultTextView.setText(String.valueOf((long) result));
            } else {
                resultTextView.setText(String.valueOf(result));
            }
        } catch (Exception e) {
            resultTextView.setText("Error");
        }
    }

    private void calculateFinalResult() {
        String currentDisplay = displayTextView.getText().toString();

        try {
            String infixExpression = currentDisplay
                    .replace("×", "*")
                    .replace("÷", "/");

            double result = evaluateExpression(infixExpression);

            // Display final result
            // Check if the result is a whole number
            if (result == (long) result) {
                displayTextView.setText(String.valueOf((long) result));
            } else {
                displayTextView.setText(String.valueOf(result));
            }

            // Clear result text view
            resultTextView.setText("");
        } catch (Exception e) {
            displayTextView.setText("Error");
        }
    }
    // Advanced expression evaluation methods
    private double evaluateExpression(String expression) {
        return evaluatePostfix(infixToPostfix(expression));
    }

    private List<String> infixToPostfix(String infix) {
        List<String> postfix = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        String[] tokens = tokenizeExpression(infix);

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (isNumber(token)) {
                postfix.add(token);
            } else {
                if (token.equals("√")) {
                    // Treat √ as unary operator
                    operators.push(token);
                } else if (token.equals("-") && (i == 0 || "+-×÷%^".contains(tokens[i - 1]))) {
                    token = "~"; // Replace unary minus with special token
                }

                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    postfix.add(operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            postfix.add(operators.pop());
        }

        return postfix;
    }

    private String[] tokenizeExpression(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if ("+-*/^%√".indexOf(c) != -1) {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else {
                currentToken.append(c);
            }
        }

        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens.toArray(new String[0]);
    }

    private double evaluatePostfix(List<String> postfix) {
        Stack<Double> values = new Stack<>();

        for (String token : postfix) {
            if (isNumber(token)) {
                values.push(Double.parseDouble(token));
            } else if (token.equals("~")) {
                // Unary minus operator
                double value = values.pop();
                values.push(-value);
            } else if (token.equals("√")) {
                // Unary square root operator
                double value = values.pop();
                values.push(Math.sqrt(value));
            } else {
                // Binary operators
                double b = values.pop();
                double a = values.pop();
                values.push(applyOperator(a, b, token));
            }
        }

        return values.pop();
    }


    private double applyOperator(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            case "^": return Math.pow(a, b);
            case "%": return a % b;
            default: throw new IllegalArgumentException("Invalid operator");
        }
    }

    private int precedence(String op) {
        switch (op) {
            case "~": return 5; // Unary minus
            case "√": return 5; // Square root
            case "^": return 4; // Exponentiation
            case "*":
            case "/":
            case "%": return 3;
            case "+":
            case "-": return 2;
            default: return 1;
        }
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Factorial calculation method
    private double calculateFactorial(double n) {
        if (n == 0 || n == 1) return 1;
        return n * calculateFactorial(n - 1);
    }
}