package com.example.matchgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    private Map<Integer, ImageView> listOfMatches = new HashMap<>();
    private ConstraintLayout constraintLayout;
    private int numOfMatches;
    private int currentPlayerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        startGame(new View(this));
    }

    public void endTurn(View view) {

        int numOfCheckedMatches = 0;

        for (Map.Entry<Integer, ImageView> entry : listOfMatches.entrySet()) {
            if (entry.getValue().getTag() == "Chosen"){
                numOfCheckedMatches++;
            }
        }

        if (numOfCheckedMatches <= MainActivity.getNumOfMatchesByTurn() && numOfCheckedMatches > 0) {
            for (final Map.Entry<Integer, ImageView> entry : listOfMatches.entrySet()) {
                if (entry.getValue().getTag() == "Chosen") {
                    entry.getValue().animate().alpha(0).setDuration(1000).withEndAction(new Runnable() {
                        public void run() {
                            entry.getValue().setTag("Deleted");
                            constraintLayout.removeView(entry.getValue());
                            if(isGameEnded()) {
                                findViewById(R.id.startGameButton).setVisibility(View.VISIBLE);
                                findViewById(R.id.endTurnButton).setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
            numOfMatches -= numOfCheckedMatches;
            TextView textView = findViewById(R.id.textView);
            textView.setText(String.valueOf(numOfMatches));

            if(isGameEnded()) {
                Toast toast = Toast.makeText(this, "Вы проиграли :(", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else if (currentPlayerId == 2131165326)
                        computerTurn(numOfCheckedMatches);
                   else computerTurn(0);
        }
        else if (numOfCheckedMatches == 0){
            Toast toast = Toast.makeText(this, "Выберите хотябы одну спичку", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 600);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, String.format("Вы можете убрать не более %d спичек", MainActivity.getNumOfMatchesByTurn()), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 600);
            toast.show();
        }
    }

    public void computerTurn(int match){

        int currentNumOfMatchesCompTurn = 0;
        final int numOfMatchesCompTurn;
        findViewById(R.id.endTurnButton).setVisibility(View.INVISIBLE);
        if (match == 0)
            numOfMatchesCompTurn = Math.min((int) (Math.random() * MainActivity.getNumOfMatchesByTurn() + 1), numOfMatches);
        else
            numOfMatchesCompTurn = Math.min(MainActivity.getNumOfMatchesByTurn() + 1 - match, numOfMatches);

        Log.i("numOfMatchesCompTurn", String.valueOf(numOfMatchesCompTurn));
        numOfMatches -= numOfMatchesCompTurn;

        for (final Map.Entry<Integer, ImageView> entry : listOfMatches.entrySet()) {
            if (entry.getValue().getTag() != "Chosen" && entry.getValue().getTag() != "Deleted" && currentNumOfMatchesCompTurn < numOfMatchesCompTurn){

                entry.getValue().animate().setStartDelay(2000).alpha(0).setDuration(1000).withEndAction(new Runnable() {
                    public void run() {
                        entry.getValue().setTag("Deleted");
                        constraintLayout.removeView(entry.getValue());

                        TextView textView = findViewById(R.id.textView);
                        textView.setText(String.valueOf(numOfMatches));
                        findViewById(R.id.endTurnButton).setVisibility(View.VISIBLE);

                        if(isGameEnded()) {
                            findViewById(R.id.startGameButton).setVisibility(View.VISIBLE);
                            findViewById(R.id.endTurnButton).setVisibility(View.INVISIBLE);
                        }
                    }
                });
                currentNumOfMatchesCompTurn++;
            }
        }

        if(isGameEnded()) {
            Toast toast = Toast.makeText(this, "Вы выиграли :)", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private boolean isGameEnded(){
        return numOfMatches == 0;
    }

    public void startGame(View view) {
        Intent intent = getIntent();
        //2131165324 - Я; 2131165325 - Смартфон
        currentPlayerId = intent.getIntExtra("RadioButtonId", 0);

        Log.i("currentPlayerId", String.valueOf(currentPlayerId));
        numOfMatches = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra(MainActivity.EXTRA_MESSAGE)));
        int inRow = numOfMatches / 5;
        int row = 1;
        int nowInRow = 0;
        int space = 900 / inRow;

        TextView textView = findViewById(R.id.textView);
        textView.setText(String.valueOf(numOfMatches));

        findViewById(R.id.startGameButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.endTurnButton).setVisibility(View.VISIBLE);

        constraintLayout = findViewById(R.id.layout);

        for (int i = 0; i < numOfMatches; i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.match);
            imageView.setX(nowInRow*space + 20);
            imageView.setY(row);
            nowInRow++;
            if (nowInRow == inRow) {
                nowInRow = 0;
                row += 200;
            }

            imageView.setTag(i);
            imageView.setId(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView imageView = (ImageView) view;
                    if (imageView.getTag() != "Chosen"){
                        listOfMatches.get(imageView.getId()).setAlpha(0.5f);
                        listOfMatches.get(imageView.getId()).setTag("Chosen");
                    } else {
                        listOfMatches.get(imageView.getId()).setAlpha(1f);
                        listOfMatches.get(imageView.getId()).setTag(imageView.getId());
                    }
                }
            });

            constraintLayout.addView(imageView);
            listOfMatches.put(imageView.getId(), imageView);
        }

        if (currentPlayerId == 2131165327)
            computerTurn(0);
    }

}




