package com.wear.isicwear;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageButton;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton b01, b02, b03, b04, b05, b06, b07, b08, b09;
    void checkWifiStatus(){
        Log.i("wifi-cftv", "wifi check status");
        Toast.makeText(getApplicationContext(),"Checking Wi-fi Status", Toast.LENGTH_SHORT).show();

        /*
        * pseudocodigo
        * se wifi conectado
        *   pass
        * senao
        *   open wifi settings
        * */
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.i("wifi-cftv", String.valueOf(networkInfo));
        if(networkInfo!=null) {
            if (String.valueOf(networkInfo).contains("WIFI[], state: CONNECTED/CONNECTED")){
                Log.i("wifi-cftv", "Wi-fi Conected");
                Toast.makeText(getApplicationContext(),"Wi-fi Conected", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.i("wifi-cftv", "Wi-fi Disconected - Starting wifi settings");
                Toast.makeText(getApplicationContext(),"Wi-fi Disconected - Starting wifi settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }
        else{
            Log.i("wifi-cftv", "Wi-fi Disconected - Starting wifi settings");
            Log.i("wifi-cftv", "Wi-fi Off");

            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkWifiStatus();
        b01 = findViewById(R.id.imageButton01);
        b01.setOnClickListener(view -> {
            Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            IntentPlayer.putExtra("can", 1);
            startActivities(new Intent[]{IntentPlayer});
        });
        b02 = findViewById(R.id.imageButton02);
        b02.setOnClickListener(view -> {
            Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            IntentPlayer.putExtra("can", 2);
            startActivities(new Intent[]{IntentPlayer});
        });
        b03 = findViewById(R.id.imageButton03);
        b03.setOnClickListener(view -> {
            Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            IntentPlayer.putExtra("can", 3);
            startActivities(new Intent[]{IntentPlayer});
        });
        b04 = findViewById(R.id.imageButton04);
        b04.setOnClickListener(view -> {
            Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            IntentPlayer.putExtra("can", 4);
            startActivities(new Intent[]{IntentPlayer});
        });
        b05 = findViewById(R.id.imageButton05);
        b05.setOnClickListener(view -> {
            Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            IntentPlayer.putExtra("can", 5);
            startActivities(new Intent[]{IntentPlayer});
        });
        b06 = findViewById(R.id.imageButton06);
        b06.setOnClickListener(view -> {
            Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            IntentPlayer.putExtra("can", 6);
            startActivities(new Intent[]{IntentPlayer});
        });
        b07 = findViewById(R.id.imageButton07);
        b07.setOnClickListener(view -> {
            Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            IntentPlayer.putExtra("can", 7);
            startActivities(new Intent[]{IntentPlayer});
        });
        b08 = findViewById(R.id.imageButton08);
        b08.setOnClickListener(view -> {
            Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            IntentPlayer.putExtra("can", 8);
            startActivities(new Intent[]{IntentPlayer});
        });
        b09 = findViewById(R.id.imageButton09);
        b09.setOnClickListener(view -> {
            Intent IntentPlayer = new Intent(MainActivity.this, PreferencesActivity.class);
            startActivities(new Intent[]{IntentPlayer});
        });

    }
}