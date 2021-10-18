package com.example.cardgameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Rules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        final Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Rules.this, MainActivity.class));
            }
        });

        TextView rules = findViewById(R.id.howtoplay);
        rules.setText("\n \n \n How to Play: Each player is given 20 cards. Only 5 cards can be in your hand at a time. " +
                "There will be two cards in the middle. From your hand, you need to place cards in ascending or" +
                " descending order. The order of cards is Ace 1 2 3 4 5 6 7 8 9 10 Jack Queen King Ace. So for " +
                "example, if there was a 4 of spades in either of the two piles in the middle and you had a 5 " +
                "of hearts, then you could place it in the middle. After, you can pick up another card from your" +
                "pile until you have 5 again. If there comes a time when neither player has a card to place, then" +
                " new cards from the sides will be placed on top. To win, you must place all your cards.");
    }
}