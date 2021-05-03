package com.example.matchgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.matchgame.MESSAGE";

    private static Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        Integer[] items = new Integer[]{13, 16, 21, 29, 31, 36, 43, 46, 55};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    public void startGameButtonClick(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast toast = Toast.makeText(this, "Выберите, кто будет делать первый ход", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 600);
            toast.show();
        } else {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("RadioButtonId", radioGroup.getCheckedRadioButtonId());
            String numOfMatches = spinner.getSelectedItem().toString();
            intent.putExtra(EXTRA_MESSAGE, numOfMatches);
            startActivity(intent);
        }

    }

    public void rulesCreditsButtonClick(View view){
        Button button = (Button) view;
        Intent intent = new Intent(this, RulesCreditsActivity.class);
        if (button.getText().equals("Об авторе")) {
           intent.putExtra(EXTRA_MESSAGE, "credits");
        }
        if (button.getText().equals("Правила")) {
            intent.putExtra(EXTRA_MESSAGE, "rules");
        }
        startActivity(intent);
    }

    public void updateNumOfMatches(View view) {
        TextView textView = (TextView) view;
        textView.setText(String.format("Вы можете взять от 1 до %d спичек за ход", getNumOfMatchesByTurn()));
    }

    public static int getNumOfMatchesByTurn(){
        int numOfMatches = Integer.parseInt(spinner.getSelectedItem().toString());
        if (numOfMatches == 13 || numOfMatches == 16){
            return 2;
        }
        if (numOfMatches == 21 || numOfMatches == 29){
            return 3;
        }
        if (numOfMatches == 31){
            return 5;
        }
        if (numOfMatches == 36 || numOfMatches == 43){
            return 6;
        }
        if (numOfMatches == 46){
            return 4;
        }
        if (numOfMatches == 55){
            return 8;
        }

        return 0;
    }
}