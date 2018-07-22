package com.vince.hearthstone;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microsoft.appcenter.analytics.Analytics;
import com.squareup.picasso.Picasso;
import com.vince.hearthstone.model.Card;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CardDetailActivity extends AppCompatActivity {
    private Card card;
    private Gson gson = new Gson();
    private ImageView cardImage;
    private Button addButton;
    private Context context;
    private File FILE;
    private int value;
    private ArrayList<String> cardsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        cardImage = findViewById(R.id.cardImage);
        addButton = findViewById(R.id.addButton);
        context = this;
        cardsList = new ArrayList<>();
        card =  gson.fromJson(getIntent().getStringExtra("card"), Card.class);

        Toast.makeText(this, "Type = " + (card.getType()), Toast.LENGTH_SHORT).show();
        if (card.getImg() != null){
            Picasso.with(this).load(card.getImg()).placeholder(R.drawable.cardback_legend)
                    .error(R.drawable.cardback_legend)
                    .into(cardImage);
        }

        Analytics.trackEvent(card.getName());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try {
                    FILE = new File(Environment.getExternalStorageDirectory().getPath() + "/android/data/" + getPackageName() + "/" + "Deck.txt");

                    StringBuilder stringBuilder = new StringBuilder();

                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        FileInputStream inputStream = new FileInputStream(FILE);


                        while ((value = inputStream.read()) != -1) {
                            stringBuilder.append((char) value);
                        }

                        if (stringBuilder.length() != 0){
                            stringBuilder.append(";");
                        }
                        //Toast.makeText(context, "test : " + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                        cardsList.clear();
                        for(String cardString : stringBuilder.toString().split(";")){
                            cardsList.add(cardString);
                        }
                    }

                    value = 0;
                    for(String cardString : cardsList){
                        if (cardString.equals(card.getName())){
                            value ++;
                        }
                    }

                    if (value >=2){
                        Toast.makeText(context, "Limite de 2 carte max par Deck", Toast.LENGTH_LONG).show();
                        return;
                    } else if (cardsList.size() == 30){
                        Toast.makeText(context, "Un deck est limité à 30 cartes", Toast.LENGTH_LONG).show();
                        return;
                    } else{
                        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                                && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){
                            value = 0;
                            FILE.getParentFile().mkdirs();
                            FILE.createNewFile();
                            FileOutputStream outputStream = new FileOutputStream(FILE);
                            outputStream.write("".getBytes());
                            outputStream.write((stringBuilder + card.getName()).getBytes());

                            outputStream.close();
                            Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
