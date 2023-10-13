package com.wear.isicwear;

import androidx.appcompat.app.AppCompatActivity;
import org.videolan.libvlc.util.VLCVideoLayout;
import android.content.SharedPreferences;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import java.util.ArrayList;
import android.os.Bundle;
import android.net.Uri;


public class PlayerActivity extends AppCompatActivity {
    private VLCVideoLayout videoLayout;
    private MediaPlayer mediaPlayer;
    private LibVLC libVlc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        final ArrayList<String> args = new ArrayList<>();
        args.add("--vout=android-display");
        args.add("-vvv");
        libVlc = new LibVLC(this, args);
        mediaPlayer = new MediaPlayer(libVlc);
        videoLayout = findViewById(R.id.videoLayout);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("com.wear.isicwear", MODE_PRIVATE);
        String[] settings_keys = getResources().getStringArray(R.array.settings_array);
        String ip   = sharedPref.getString(settings_keys[0], "192.168.0.2");
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
        Media media = new Media(libVlc, Uri.parse(url));
        media.setHWDecoderEnabled(true, false);
        media.addOption(":network-caching=600");
        videoLayout.requestFocus();
        mediaPlayer.attachViews(videoLayout, null, false, false);
        mediaPlayer.setMedia(media);
        media.release();
        mediaPlayer.play();
    }
}