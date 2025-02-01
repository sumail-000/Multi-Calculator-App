package com.innovativebits.multicalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome); // Set the layout for the welcome screen

        // Find the VideoView and set the animation video
        VideoView videoView = findViewById(R.id.logoAnimation);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo_animation);
        videoView.setVideoURI(videoUri);
        videoView.start(); // Start video playback

        // Delay for 6 seconds, then navigate to MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish WelcomeActivity to prevent returning to it
        }, 6000); // 6-second delay
    }
}
