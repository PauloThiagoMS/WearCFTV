package com.wear.isicwear;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
    ImageButton b01, b02, b03, b04, b05, b06, b07, b08, b09;
    private static final String TAG = "MainActivity";
    private ConnectivityManager connectivityManager;
    WifiManager wifiManager;
    private static final int MY_PERMISSIONS_REQUEST_WIFI_STATE = 1;
    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            Log.i(TAG, "Connecting");
            connectivityManager.bindProcessToNetwork(network);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            Log.i(TAG, "Unconnected");
            connectivityManager.bindProcessToNetwork(null);
        }
    };

    private boolean check_network(){
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !(String.valueOf(networkInfo).contains("WIFI[], state: CONNECTED/CONNECTED"))){
            requestAnyKnownWifi();
            return false;
        }
        Log.i(TAG, "Connected");
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectivityManager = getSystemService(ConnectivityManager.class);
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        checkAndRequestPermissions();

        b01 = findViewById(R.id.imageButton01);
        b01.setOnClickListener(view -> {
            if (check_network()){
                Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
                IntentPlayer.putExtra("can", 1);
                startActivities(new Intent[]{IntentPlayer});
            }
        });
        b02 = findViewById(R.id.imageButton02);
        b02.setOnClickListener(view -> {
            if (check_network()){
                Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
                IntentPlayer.putExtra("can", 2);
                startActivities(new Intent[]{IntentPlayer});
            }
        });
        b03 = findViewById(R.id.imageButton03);
        b03.setOnClickListener(view -> {
            if (check_network()){
                Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
                IntentPlayer.putExtra("can", 3);
                startActivities(new Intent[]{IntentPlayer});
            }
        });
        b04 = findViewById(R.id.imageButton04);
        b04.setOnClickListener(view -> {
            if (check_network()){
                Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
                IntentPlayer.putExtra("can", 4);
                startActivities(new Intent[]{IntentPlayer});
            }
        });
        b05 = findViewById(R.id.imageButton05);
        b05.setOnClickListener(view -> {
            if (check_network()){
                Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
                IntentPlayer.putExtra("can", 5);
                startActivities(new Intent[]{IntentPlayer});
            }
        });
        b06 = findViewById(R.id.imageButton06);
        b06.setOnClickListener(view -> {
            if (check_network()){
                Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
                IntentPlayer.putExtra("can", 6);
                startActivities(new Intent[]{IntentPlayer});
            }
        });
        b07 = findViewById(R.id.imageButton07);
        b07.setOnClickListener(view -> {
            if (check_network()){
                Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
                IntentPlayer.putExtra("can", 7);
                startActivities(new Intent[]{IntentPlayer});
            }
        });
        b08 = findViewById(R.id.imageButton08);
        b08.setOnClickListener(view -> {
            if (check_network()){
                Intent IntentPlayer = new Intent(MainActivity.this, PlayerActivity.class);
                IntentPlayer.putExtra("can", 8);
                startActivities(new Intent[]{IntentPlayer});
            }
        });
        b09 = findViewById(R.id.imageButton09);
        b09.setOnClickListener(view -> {
            Intent IntentPref = new Intent(MainActivity.this, PreferencesActivity.class);
            startActivities(new Intent[]{IntentPref});
        });
    }

    public static class WifiHelper {
        public static void enableWifi(Context context) {
            Log.i(TAG, "enableWifi");
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null && !wifiManager.isWifiEnabled()) {
                Log.i(TAG, "Try enableWifi");
                // TODO Não está ativando!!!
                wifiManager.setWifiEnabled(true);
            }
        }
    }

    private void checkAndRequestPermissions() {
        Log.i(TAG, "checkAndRequestPermissions");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Requesting");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                    MY_PERMISSIONS_REQUEST_WIFI_STATE);
        }
    }

    private void requestAnyKnownWifi() {
        Toast.makeText(this, "Connecting WiFi...", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "requestAnyKnownWifi");
        if (wifiManager != null && !wifiManager.isWifiEnabled()) {
            Log.i(TAG, "Enabling WiFi");
            WifiHelper.enableWifi(this);
        }
        NetworkRequest.Builder requestBuilder = new NetworkRequest.Builder();
        requestBuilder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        connectivityManager.requestNetwork(requestBuilder.build(), networkCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionsResult");
        if (requestCode == MY_PERMISSIONS_REQUEST_WIFI_STATE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestAnyKnownWifi();
            } else {
                Toast.makeText(this, "Wi-Fi Den.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "fail");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
