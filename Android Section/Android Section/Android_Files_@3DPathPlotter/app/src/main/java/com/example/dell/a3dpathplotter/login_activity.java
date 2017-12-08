package com.example.dell.a3dpathplotter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login_activity extends AppCompatActivity
{
    EditText pswd,usrusr;
    TextView sup,lin;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        lin = (TextView) findViewById(R.id.lin);
        usrusr = (EditText) findViewById(R.id.usrusr);
        pswd = (EditText) findViewById(R.id.pswrdd);
        sup = (TextView) findViewById(R.id.sup);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        lin.setTypeface(custom_font1);
        ActionBar action = getSupportActionBar();
        action.hide();
        sup.setTypeface(custom_font);
        usrusr.setTypeface(custom_font);
        pswd.setTypeface(custom_font);
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String pwd = pswd.getText().toString();
                String uname = usrusr.getText().toString();
                if(uname.length()==0)
                {
                    Toast.makeText(login_activity.this,"User Name Cannot be Remain Empty",Toast.LENGTH_SHORT).show();
                    //progressBar.dismiss();
                    return;
                }
                else
                {
                    if(pswd.length()==0)
                    {
                        Toast.makeText(login_activity.this,"Password Cannot be Remain Empty",Toast.LENGTH_SHORT).show();
                        //progressBar.dismiss();
                        return;
                    }
                }
                try {
                    if (UsersManager.have_User(getApplicationContext(), uname, pwd))
                    {
                        SharedPreferences pref1 = getSharedPreferences("user",MODE_PRIVATE);
                        final SharedPreferences.Editor edit = pref1.edit();
                        edit.putString("uname",uname);
                        edit.putString("pwd",pwd);
                        edit.apply();
                        Intent i = new Intent(login_activity.this,Main_activity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"User Exist from this user name",Toast.LENGTH_SHORT).show();
//                        mophone.setText("");
                        pswd.setText("");
                        //pswd1.setText("");
                        usrusr.setText("");
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(login_activity.this, SignUP_Activity.class);
                startActivity(it);
                finish();
            }
        });
    }
}