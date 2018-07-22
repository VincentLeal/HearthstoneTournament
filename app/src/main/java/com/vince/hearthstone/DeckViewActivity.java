package com.vince.hearthstone;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vince.hearthstone.data.mainapi.Adapter;
import com.vince.hearthstone.data.mainapi.AdapterString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DeckViewActivity extends AppCompatActivity implements AdapterString.ItemClickListener {

    private RecyclerView recyclerView;
    private AdapterString mAdapter;
    private Context context = this;
    private File FILE;
    private int value;
    private ArrayList<String> cardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_view);

        recyclerView = findViewById(R.id.cardList);
        cardsList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        try {
            FILE = new File(Environment.getExternalStorageDirectory().getPath() + "/android/data/" + getPackageName() + "/" + "Deck.txt");

            StringBuilder stringBuilder = new StringBuilder();

            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                FileInputStream inputStream = new FileInputStream(FILE);


                while ((value = inputStream.read()) != -1) {
                    stringBuilder.append((char) value);
                }

                if (stringBuilder.length() != 0) {
                    stringBuilder.append(";");
                }
                //Toast.makeText(context, "test : " + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                cardsList.clear();
                for (String cardString : stringBuilder.toString().split(";")) {
                    cardsList.add(cardString);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        setAdapter(context);
    }
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + mAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CardDetailStringActivity.class);
        intent.putExtra("card", cardsList.get(position));
        startActivity(intent);
        startActivity(intent);
    }

    public void setAdapter(Context context){
        mAdapter = new AdapterString(context,cardsList);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }
}
