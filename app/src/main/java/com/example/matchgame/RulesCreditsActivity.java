package com.example.matchgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RulesCreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        Intent intent = getIntent();
        TextView textView = findViewById(R.id.rulesCreditsText);
        if (intent.getStringExtra(MainActivity.EXTRA_MESSAGE).equals("credits")) {
            textView.setText(getString(R.string.credits));
        }

        if (intent.getStringExtra(MainActivity.EXTRA_MESSAGE).equals("rules")) {
            textView.setText(getString(R.string.rules));
        }
    }
}