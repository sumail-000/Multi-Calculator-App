package com.innovativebits.multicalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main); // Set the layout with the CardViews

        // Initialize CardViews by linking them to their respective IDs in activity_main.xml
        // Declare CardView variables for each calculator
        CardView simpleCalculatorCard = findViewById(R.id.simpleCalculatorCard);
        CardView binaryConverterCard = findViewById(R.id.binaryConverterCard);
        CardView unitConverterCard = findViewById(R.id.unitConverterCard);

        // Set click listeners for each CardView to navigate to the respective activity
        simpleCalculatorCard.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SimpleCalculatorActivity.class);
            startActivity(intent); // Start SimpleCalculatorActivity when this CardView is clicked
        });

        binaryConverterCard.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BinaryConverterActivity.class);
            startActivity(intent); // Start BinaryConverterActivity when this CardView is clicked
        });

        unitConverterCard.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UnitConverterActivity.class);
            startActivity(intent); // Start UnitConverterActivity when this CardView is clicked
        });
    }
}
