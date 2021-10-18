package com.example.cardgameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> deck = createShuffledDeck();
        HashMap<String, Integer> hashDeck = createHashDeck();

        SharedPreferences deckCount = this.getSharedPreferences("deckCount", MODE_PRIVATE);
        SharedPreferences oppDeckCount = this.getSharedPreferences("oppDeckCount", MODE_PRIVATE);

        final Button startGame = findViewById(R.id.start);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start(deck, hashDeck, deckCount, oppDeckCount);
            }
        });

        final Button rules = findViewById(R.id.rules);
        rules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Rules.class));
            }
        });
        /*

        while(true) {
            if (deckCount.getInt("deckCount", 0) == 33) {
                boolean userWon = true;
            } else if (oppDeckCount.getInt("oppDeckCount", 0) == 53) {
                boolean userWon = false;
            }
        }

         */

    }

    public ArrayList<String> createShuffledDeck() {
        ArrayList<String> deck = new ArrayList<>();
        deck.add("AoH");
        for (int i = 2; i < 11; i++) { deck.add(i + "oH"); }
        deck.add("JoH");
        deck.add("QoH");
        deck.add("KoH");
        deck.add("AoD");
        for (int i = 2; i < 11; i++) { deck.add(i + "oD"); }
        deck.add("JoD");
        deck.add("QoD");
        deck.add("KoD");
        deck.add("AoS");
        for (int i = 2; i < 11; i++) { deck.add(i + "oS"); }
        deck.add("JoS");
        deck.add("QoS");
        deck.add("KoS");
        deck.add("AoC");
        for (int i = 2; i < 11; i++) { deck.add(i + "oC"); }
        deck.add("JoC");
        deck.add("QoC");
        deck.add("KoC");
        Collections.shuffle(deck);
        return deck;
    }

    public HashMap<String, Integer> createHashDeck() {
        HashMap<String, Integer> deck = new HashMap<String, Integer>();
        deck.put("AoH", 1);
        for (int i = 2; i < 11; i++) { deck.put(i + "oH", i); }
        deck.put("JoH", 11);
        deck.put("QoH", 12);
        deck.put("KoH", 13);
        deck.put("AoD", 1);
        for (int i = 2; i < 11; i++) { deck.put(i + "oD", i); }
        deck.put("JoD", 11);
        deck.put("QoD", 12);
        deck.put("KoD", 13);
        deck.put("AoS", 1);
        for (int i = 2; i < 11; i++) { deck.put(i + "oS", i); }
        deck.put("JoS", 11);
        deck.put("QoS", 12);
        deck.put("KoS", 13);
        deck.put("AoC", 1);
        for (int i = 2; i < 11; i++) { deck.put(i + "oC", i); }
        deck.put("JoC", 11);
        deck.put("QoC", 12);
        deck.put("KoC", 13);
        return deck;
    }

    public boolean canPlace(int value1, int value2, int cardValue, String card) {
        if (cardValue - 1 == value1 || cardValue + 1 == value1 || (cardValue == 1 && value1 == 13)) {
            TextView cardDisplay1 = findViewById(R.id.cardDisplay1);
            cardDisplay1.setText(card);
            SharedPreferences dpref = this.getSharedPreferences("display1Value", MODE_PRIVATE);
            SharedPreferences.Editor d1Editor = dpref.edit();
            d1Editor.putInt("display1Value", cardValue);
            d1Editor.commit();
            return true;
        } else if (cardValue - 1 == value2 || cardValue + 1 == value2 || (cardValue == 1 && value2 == 13)) {
            TextView cardDisplay2 = findViewById(R.id.cardDisplay2);
            cardDisplay2.setText(card);
            SharedPreferences d2pref = this.getSharedPreferences("display2Value", MODE_PRIVATE);
            SharedPreferences.Editor d2Editor = d2pref.edit();
            d2Editor.putInt("display2Value", cardValue);
            d2Editor.commit();
            return true;
        }
        return false;
    }

    public void start(ArrayList<String> deck, HashMap<String, Integer> hashDeck,
                      SharedPreferences deckCount, SharedPreferences oppDeckCount) {

        SharedPreferences.Editor deckEditor = deckCount.edit();
        deckEditor.putInt("deckCount", 12);
        deckEditor.commit();

        SharedPreferences.Editor oppDeckEditor = oppDeckCount.edit();
        oppDeckEditor.putInt("oppDeckCount", 32);
        oppDeckEditor.commit();

        TextView cardDisplay1 = findViewById(R.id.cardDisplay1);
        cardDisplay1.setText(deck.get(10));
        SharedPreferences dpref = this.getSharedPreferences("display1Value", MODE_PRIVATE);
        SharedPreferences.Editor d1Editor = dpref.edit();
        d1Editor.putInt("display1Value", hashDeck.get(deck.get(10)));
        d1Editor.commit();

        TextView cardDisplay2 = findViewById(R.id.cardDisplay2);
        cardDisplay2.setText(deck.get(11));
        SharedPreferences d2pref = this.getSharedPreferences("display2Value", MODE_PRIVATE);
        SharedPreferences.Editor d2Editor = d2pref.edit();
        d2Editor.putInt("display2Value", hashDeck.get(deck.get(11)));
        d2Editor.commit();

        final Button card1 = findViewById(R.id.card1);
        card1.setText(deck.get(0));
        SharedPreferences pref = this.getSharedPreferences("card1Value", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("card1Value", hashDeck.get(deck.get(0)));
        editor.commit();
        card1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        pref.getInt("card1Value", 0), deck.get(0))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    card1.setText(newCard);
                    editor.putInt("card1Value", hashDeck.get(newCard));
                    editor.commit();
                }

            }
        });

        final Button card2 = findViewById(R.id.card2);
        card2.setText(deck.get(1));
        SharedPreferences pref2 = this.getSharedPreferences("card2Value", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = pref2.edit();
        editor2.putInt("card2Value", hashDeck.get(deck.get(1)));
        editor2.commit();
        card2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        pref2.getInt("card2Value", 0), deck.get(1))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    card2.setText(newCard);
                    editor2.putInt("card2Value", hashDeck.get(newCard));
                    editor2.commit();
                }
            }
        });

        final Button card3 = findViewById(R.id.card3);
        card3.setText(deck.get(2));
        SharedPreferences pref3 = this.getSharedPreferences("card3Value", MODE_PRIVATE);
        SharedPreferences.Editor editor3 = pref3.edit();
        editor3.putInt("card3Value", hashDeck.get(deck.get(2)));
        editor3.commit();
        card3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        pref3.getInt("card3Value", 0), deck.get(2))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    card3.setText(newCard);
                    editor3.putInt("card3Value", hashDeck.get(newCard));
                    editor3.commit();
                }
            }
        });

        final Button card4 = findViewById(R.id.card4);
        card4.setText(deck.get(3));
        SharedPreferences pref4 = this.getSharedPreferences("card4Value", MODE_PRIVATE);
        SharedPreferences.Editor editor4 = pref4.edit();
        editor4.putInt("card4Value", hashDeck.get(deck.get(3)));
        editor4.commit();
        card4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        pref4.getInt("card4Value", 0), deck.get(3))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    card4.setText(newCard);
                    editor4.putInt("card4Value", hashDeck.get(newCard));
                    editor4.commit();
                }
            }
        });

        final Button card5 = findViewById(R.id.card5);
        card5.setText(deck.get(4));
        SharedPreferences pref5 = this.getSharedPreferences("card5Value", MODE_PRIVATE);
        SharedPreferences.Editor editor5 = pref5.edit();
        editor5.putInt("card5Value", hashDeck.get(deck.get(4)));
        editor5.commit();
        card5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        pref5.getInt("card5Value", 0), deck.get(4))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    card5.setText(newCard);
                    editor5.putInt("card5Value", hashDeck.get(newCard));
                    editor5.commit();
                }
            }
        });

        final Button oppCard1 = findViewById(R.id.oppCard1);
        oppCard1.setText(deck.get(5));
        SharedPreferences oppPref = this.getSharedPreferences("oppCard1Value", MODE_PRIVATE);
        SharedPreferences.Editor oppEditor = oppPref.edit();
        oppEditor.putInt("oppCard1Value", hashDeck.get(deck.get(5)));
        oppEditor.commit();
        oppCard1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        oppPref.getInt("oppCard1Value", 0), deck.get(5))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    oppCard1.setText(newCard);
                    oppEditor.putInt("oppCard1Value", hashDeck.get(newCard));
                    oppEditor.commit();
                }

            }
        });

        final Button oppCard2 = findViewById(R.id.oppCard2);
        oppCard2.setText(deck.get(6));
        SharedPreferences oppPref2 = this.getSharedPreferences("oppCard2Value", MODE_PRIVATE);
        SharedPreferences.Editor oppEditor2 = oppPref2.edit();
        oppEditor2.putInt("oppCard2Value", hashDeck.get(deck.get(6)));
        oppEditor2.commit();
        oppCard2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        oppPref2.getInt("oppCard2Value", 0), deck.get(6))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    oppCard2.setText(newCard);
                    oppEditor2.putInt("oppCard2Value", hashDeck.get(newCard));
                    oppEditor2.commit();
                }
            }
        });

        final Button oppCard3 = findViewById(R.id.oppCard3);
        oppCard3.setText(deck.get(7));
        SharedPreferences oppPref3 = this.getSharedPreferences("oppCard3Value", MODE_PRIVATE);
        SharedPreferences.Editor oppEditor3 = oppPref3.edit();
        oppEditor3.putInt("oppCard3Value", hashDeck.get(deck.get(7)));
        oppEditor3.commit();
        oppCard3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        oppPref3.getInt("oppCard3Value", 0), deck.get(7))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    oppCard3.setText(newCard);
                    oppEditor3.putInt("oppCard3Value", hashDeck.get(newCard));
                    oppEditor3.commit();
                }
            }
        });

        final Button oppCard4 = findViewById(R.id.oppCard4);
        oppCard4.setText(deck.get(8));
        SharedPreferences oppPref4 = this.getSharedPreferences("oppCard4Value", MODE_PRIVATE);
        SharedPreferences.Editor oppEditor4 = oppPref4.edit();
        oppEditor4.putInt("oppCard4Value", hashDeck.get(deck.get(8)));
        oppEditor4.commit();
        oppCard4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        oppPref4.getInt("oppCard4Value", 0), deck.get(8))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    oppCard4.setText(newCard);
                    oppEditor4.putInt("oppCard4Value", hashDeck.get(newCard));
                    oppEditor4.commit();
                }
            }
        });

        final Button oppCard5 = findViewById(R.id.oppCard5);
        oppCard5.setText(deck.get(9));
        SharedPreferences oppPref5 = this.getSharedPreferences("oppCard5Value", MODE_PRIVATE);
        SharedPreferences.Editor oppEditor5 = oppPref5.edit();
        oppEditor5.putInt("oppCard5Value", hashDeck.get(deck.get(9)));
        oppEditor5.commit();
        oppCard5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(canPlace(dpref.getInt("display1Value", 0),
                        d2pref.getInt("display2Value", 0),
                        oppPref5.getInt("oppCard5Value", 0), deck.get(9))) {
                    deckEditor.putInt("deckCount", deckCount.getInt("deckCount", 0) + 1);
                    deckEditor.commit();
                    String newCard = deck.get(deckCount.getInt("deckCount", 0));
                    oppCard5.setText(newCard);
                    oppEditor5.putInt("oppCard5Value", hashDeck.get(newCard));
                    oppEditor5.commit();
                }
            }
        });

        final Button newCard2 = findViewById(R.id.newCard2);
        newCard2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = deckCount.getInt("deckCount", 0);
                cardDisplay1.setText(deck.get(count + 1));
                d1Editor.putInt("display1Value", hashDeck.get(count + 1));
                d1Editor.commit();
                cardDisplay2.setText(deck.get(count + 2));
                d2Editor.putInt("display2Value", hashDeck.get(count + 2));
                d2Editor.commit();
            }
        });

    }
}