package com.vince.hearthstone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.AppCenter; import com.microsoft.appcenter.analytics.Analytics; import com.microsoft.appcenter.crashes.Crashes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageButton cardsIB;
    private ImageButton resetIB;
    private ImageButton viewIB;
    private Context context;
    private File FILE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        cardsIB = findViewById(R.id.CardsButton);
        resetIB = findViewById(R.id.ResetButton);
        viewIB = findViewById(R.id.ViewButton);


        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 13);

        cardsIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CardsActivity.class);
                startActivity(intent);
            }
        });

        resetIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FILE = new File(Environment.getExternalStorageDirectory().getPath() + "/android/data/" + getPackageName() + "/" + "Deck.txt");

                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                        && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){

                    FILE.getParentFile().mkdirs();
                    try {
                        FILE.createNewFile();
                        FileOutputStream outputStream = new FileOutputStream(FILE);
                        outputStream.write("".getBytes());

                        outputStream.close();
                        Toast.makeText(context, "Deck remit à 0", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
        });

        viewIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeckViewActivity.class);
                startActivity(intent);
            }
        });



        AppCenter.start(getApplication(), "9c9cce42-cb7d-4326-9ecc-38cc97bf5a91",
                Analytics.class, Crashes.class);
    }
}
