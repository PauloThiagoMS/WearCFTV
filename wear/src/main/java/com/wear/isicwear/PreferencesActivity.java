package com.wear.isicwear;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Spinner;
import android.text.Editable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class PreferencesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editText;
    String user;
    String pass;
    String ip;
    String port;


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
        setContentView(R.layout.activity_preferences);
        checkWifiStatus();
        SharedPreferences sharedPref = getSharedPreferences("com.wear.isicwear", MODE_PRIVATE);
        String[] settings_keys = getResources().getStringArray(R.array.settings_array);
        ip   = sharedPref.getString(settings_keys[0], "192.168.0.2");
        port = sharedPref.getString(settings_keys[1], "554");
        user = sharedPref.getString(settings_keys[2], "root");
        pass = sharedPref.getString(settings_keys[3], "toor");

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                settings_keys);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        editText = findViewById(R.id.editTextText);
        editText.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                int i = spinner.getSelectedItemPosition();
                switch(i){
                    case 0:
                        ip = String.valueOf(editable);
                        break;
                    case 1:
                        port = String.valueOf(editable);
                        break;
                    case 2:
                        user = String.valueOf(editable);
                        break;
                    case 3:
                        pass= String.valueOf(editable);
                        break;
                    default:
                }
            }
        });

//        ImageButton wifi = findViewById(R.id.imageButtonwifi);
//
//        wifi.setOnClickListener(view -> {
//            Log.i("wifi-cftv", "wifi settings called");
//            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//            });

        ImageButton save_bt = findViewById(R.id.imageButtonSave);
        save_bt.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.putString(settings_keys[0], ip);
            editor.putString(settings_keys[1], port);
            editor.putString(settings_keys[2], user);
            editor.putString(settings_keys[3], pass);
            editor.apply();
            editor.commit();
            onBackPressed();
        });

        ImageButton reset_bt = findViewById(R.id.imageButtonReset);
        reset_bt.setOnClickListener(view -> {
            ip   = "192.168.0.2";
            port = "554";
            user = "root";
            pass = "toor";
            int i = spinner.getSelectedItemPosition();
            switch(i){
                case 0:
                    editText.setText(ip);
                    break;
                case 1:
                    editText.setText(port);
                    break;
                case 2:
                    editText.setText(user);
                    break;
                case 3:
                    editText.setText(pass);
                    break;
                default:
            }
        });

        ImageButton about_bt = findViewById(R.id.imageButtonAbout);
        about_bt.setOnClickListener(view -> {

        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(i){
            case 0:
                editText.setText(ip);
                break;
            case 1:
                editText.setText(port);
                break;
            case 2:
                editText.setText(user);
                break;
            case 3:
                editText.setText(pass);
                break;
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}