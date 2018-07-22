package com.vince.hearthstone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.vince.hearthstone.data.mainapi.Adapter;
import com.vince.hearthstone.data.mainapi.IHearthstone;
import com.vince.hearthstone.model.Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CardsActivity extends AppCompatActivity implements Adapter.ItemClickListener {

    private JsonParser parser = new JsonParser();
    private RecyclerView recyclerView;
    private ArrayList<Card> cardsList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private Adapter mAdapter;
    private Context context = this;
    private Gson gson = new Gson();
    private VideoView videoView;
    private String urlVideo;
    //https://www.tutlane.com/tutorial/android/android-loading-spinner-with-examples FOR LOADING

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        recyclerView = findViewById(R.id.cardList);
        recyclerView.setVisibility(View.INVISIBLE);

        videoView = findViewById(R.id.videoLoader);
        urlVideo = "android.resource://" + getPackageName() + "/" + R.raw.giphy;
        videoView.setVideoPath(urlVideo);

        videoView.start();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AndroidNetworking.get(IHearthstone.ENDPOINT + "cards")
                //.addPathParameter("pageNumber", "0")
                .addHeaders("X-Mashape-Key", "3OWe7GlebSmshyR98Uex57ijtJTLp1TcmYIjsnjbMaHNwlaA0B")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray nameResponse = response.names();

                            JSONArray responseArray = response.toJSONArray(nameResponse);
                            JSONArray arrayIn = new JSONArray();
                            for( int i = 0; i<responseArray.length();i++){
                                arrayIn = responseArray.getJSONArray(i);
                                for (int j = 0; j<arrayIn.length(); j++){
                                    String mJsonString = arrayIn.getString(j);
                                    JsonElement mJson = parser.parse(mJsonString);
                                    Card object = gson.fromJson(mJson, Card.class);
                                    if(!object.getType().equals("Enchantment") && !object.getType().equals("Hero") && !object.getType().equals("Hero Power")){
                                        cardsList.add(object);
                                    }
                                }
                            }

                            setAdapter(context);
                            videoView.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            Log.d("names => " , nameResponse.toString());

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
        //Toast.makeText(this, "You clicked " + mAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CardDetailActivity.class);
        intent.putExtra("card", gson.toJson(cardsList.get(position)));
        startActivity(intent);
    }

    public void setAdapter(Context context){
        mAdapter = new Adapter(context,cardsList);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }
}
