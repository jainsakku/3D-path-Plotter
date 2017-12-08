package com.example.dell.a3dpathplotter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Intro_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_);
        ActionBar action = getSupportActionBar();
        action.hide();
        Thread loading = new Thread()
        {
            public void run()
            {
                try {
                    sleep(5000);
                    SharedPreferences pref = getSharedPreferences("TIME",MODE_PRIVATE);
                    String show = pref.getString("FLAG","TRUE");
                    if(show.equals("TRUE")) {
                        Intent i = new Intent(Intro_Activity.this, Swip_activity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        SharedPreferences pref1 = getSharedPreferences("user",MODE_PRIVATE);
                        String uname = pref1.getString("uname","n");
                        if(uname.equals("n"))
                        {
                            Intent i = new Intent(Intro_Activity.this, login_activity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Intent i = new Intent(Intro_Activity.this, Main_activity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        };
        loading.start();
    }
}
