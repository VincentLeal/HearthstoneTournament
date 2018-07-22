package com.vince.hearthstone;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vince.hearthstone.data.mainapi.IHearthstone;
import com.vince.hearthstone.model.Card;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

public class CardDetailStringActivity extends AppCompatActivity {
    private Card card;
    private String cardString;
    private Gson gson = new Gson();
    private ImageView cardImage;
    private Button addButton;
    private Context context;
    private File FILE;
    private int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        cardImage = findViewById(R.id.cardImage);
        context = this;
        cardString = getIntent().getStringExtra("card");


        AndroidNetworking.get(IHearthstone.ENDPOINT + "cards/search/" + cardString)
                .addHeaders("X-Mashape-Key", "3OWe7GlebSmshyR98Uex57ijtJTLp1TcmYIjsnjbMaHNwlaA0B")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            card = gson.fromJson(response.get(0).toString(), Card.class);
                            Log.d("card =>", card.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (card.getImg() != null){
                            Picasso.with(context).load(card.getImg()).placeholder(R.drawable.cardback_legend)
                                    .error(R.drawable.cardback_legend)
                                    .into(cardImage);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        finish();
                    }
                });
    }
}
