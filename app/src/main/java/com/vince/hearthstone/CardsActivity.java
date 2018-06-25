package com.vince.hearthstone;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.vince.hearthstone.data.mainapi.Adapter;
import com.vince.hearthstone.model.Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity implements Adapter.ItemClickListener {

    private RecyclerView recyclerView;
    private ArrayList<Card> cardsList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private Adapter mAdapter;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        recyclerView = findViewById(R.id.cardList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AndroidNetworking.get("https://omgvamp-hearthstone-v1.p.mashape.com/cards")
                //.addPathParameter("pageNumber", "0")
                .addHeaders("X-Mashape-Key", "3OWe7GlebSmshyR98Uex57ijtJTLp1TcmYIjsnjbMaHNwlaA0B")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray basic = response.getJSONArray("Basic");
                            for (int i = 0; i < basic.length(); i++) {
                                String mJsonString = basic.getString(i);
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = parser.parse(mJsonString);
                                Gson gson = new Gson();
                                Card object = gson.fromJson(mJson, Card.class);
                                cardsList.add(object);
                            }
                            setAdapter(context);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("Error =>", error.getErrorDetail());
                        finish();
                    }
                });







    }


    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(this, "You clicked " + mAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CardDetailActivity.class);
        //intent.putExtra("card", cardsList.get(position));
        startActivity(intent);
    }

    public void setAdapter(Context context){
        mAdapter = new Adapter(context,cardsList);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }
}
