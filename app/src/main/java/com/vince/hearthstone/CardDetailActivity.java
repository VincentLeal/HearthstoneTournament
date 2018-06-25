package com.vince.hearthstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.vince.hearthstone.model.Card;

public class CardDetailActivity extends AppCompatActivity {
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

        //card = (Card) getIntent().getParcelableExtra("card");

        //Toast.makeText(this, "card = " + card.getName(), Toast.LENGTH_SHORT).show();
    }
}
