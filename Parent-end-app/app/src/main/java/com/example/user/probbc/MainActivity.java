package com.example.user.probbc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainActivity extends Activity{


    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Thread timerThread=new Thread(){
            public void run(){
                try {
                    sleep(1100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if(!sharedPreferences.getBoolean("LoggedIn",false)) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        startActivity(new Intent(MainActivity.this, SelectChildActivity.class));
                    }

                }
            }
        };

timerThread.start();
    }


}
