package com.wear.isicwear;

import androidx.appcompat.app.AppCompatActivity;
import org.videolan.libvlc.util.VLCVideoLayout;
import androidx.core.content.ContextCompat;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import org.videolan.libvlc.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import androidx.annotation.NonNull;
import android.net.NetworkRequest;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import android.content.Context;
import android.widget.Toast;
import java.util.ArrayList;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import android.net.Uri;


public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";
    private ConnectivityManager connectivityManager;
    WifiManager wifiManager;

    private VLCVideoLayout videoLayout;
    private MediaPlayer mediaPlayer;
    private LibVLC libVlc;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        final ArrayList<String> args = new ArrayList<>();
        args.add("--vout=android-display");
        libVlc = new LibVLC(this, args);
        mediaPlayer = new MediaPlayer(libVlc);
        videoLayout = findViewById(R.id.videoLayout);
        connectivityManager = getSystemService(ConnectivityManager.class);
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        checkAndRequestPermissions();
        requestAnyKnownWifi();
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Requesting");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                    MY_PERMISSIONS_REQUEST_WIFI_STATE);
        }
    }

    private void requestAnyKnownWifi() {
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
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        SharedPreferences sharedPref = getSharedPreferences("com.wear.isicwear", MODE_PRIVATE);
        String[] settings_keys = getResources().getStringArray(R.array.settings_array);
        String ip = sharedPref.getString(settings_keys[0], "192.168.0.2");
        String port = sharedPref.getString(settings_keys[1], "554");
        String user = sharedPref.getString(settings_keys[2], "root");
        String pass = sharedPref.getString(settings_keys[3], "toor");
        int channel = getIntent().getIntExtra("can", 1);
        String url = "rtsp://" +
                user + ":" +
                pass + "@" +
                ip + ":" +
                port + "/cam/realmonitor?channel=" +
                channel + "&subtype=0";
        Log.i(TAG, "URL: " + url);
        Media media = new Media(libVlc, Uri.parse(url));
        media.setHWDecoderEnabled(true, false);
        media.addOption(":network-caching=600");
        media.addOption(":fullscreen");
        videoLayout.requestFocus();
        mediaPlayer.attachViews(videoLayout, null, false, false);
        mediaPlayer.setMedia(media);
        media.release();
        mediaPlayer.play();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
