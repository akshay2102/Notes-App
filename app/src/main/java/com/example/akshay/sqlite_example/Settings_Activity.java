package com.example.akshay.sqlite_example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class Settings_Activity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Switch swRead;
    TextView tvMax;
    SeekBar sbMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        preferences = getSharedPreferences(Prefs.NOTES_SETTINGS,MODE_PRIVATE);//accessing final variable defined in prefs.java
        //mode is private means it will be visible throughout in our project
        sbMax = findViewById(R.id.sbMax);
        swRead = findViewById(R.id.swRead);
        tvMax = findViewById(R.id.tvMax);

        Boolean readOnlyMode = preferences.getBoolean(Prefs.READ_ONLY,false);
        swRead.setChecked(readOnlyMode);
        int maxCount = preferences.getInt(Prefs.MAX_NOTES,10);
        sbMax.setProgress(maxCount);
        tvMax.setText("Maximum Notes Count : "+ maxCount);

        sbMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvMax.setText("Maximum Notes Count : "+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void btnsave_click(View v){
        editor = preferences.edit();
        editor.putInt(Prefs.MAX_NOTES,sbMax.getProgress());
        editor.putBoolean(Prefs.READ_ONLY,swRead.isChecked());
        editor.apply();
        //Intent i = new Intent();
        //i.putExtra("max",sbMax.getProgress());
        finish();
    }
}
