package com.example.dell.a3dpathplotter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SignUP_Activity extends AppCompatActivity
{
    EditText mophone,pswd,pswd1,usrusr;
    TextView lin,sup;
    ProgressDialog progressDialog;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);
        ActionBar action = getSupportActionBar();
        action.hide();

        sup = (TextView) findViewById(R.id.sup);
        lin = (TextView) findViewById(R.id.lin);
        usrusr = (EditText) findViewById(R.id.usrusr);
        pswd = (EditText) findViewById(R.id.pswrdd);
        pswd1 = (EditText) findViewById(R.id.pswrdd1);
        mophone = (EditText) findViewById(R.id.mobphone);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        mophone.setTypeface(custom_font);
        sup.setTypeface(custom_font1);
        pswd.setTypeface(custom_font);
        pswd1.setTypeface(custom_font);
        lin.setTypeface(custom_font);
        usrusr.setTypeface(custom_font);

        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = usrusr.getText().toString().trim();
                String pwd = pswd.getText().toString().trim();
                String pwd1 = pswd1.getText().toString().trim();
                String pno = mophone.getText().toString().trim();

                if(uname.length()==0)
                {
                    //simpleProgressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUP_Activity.this,"User Name Cannot be Remain Empty",Toast.LENGTH_SHORT).show();
                    //progressBar.dismiss();
                    return;
                }
                else
                {
                    if (pwd.length() == 0 || pwd.length() < 6)
                    {
                        //progressBar.dismiss();
                        Toast.makeText(SignUP_Activity.this, "Password Should Be Minimum of Length 6", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if (!pwd.equals(pwd1))
                        {

                            Toast.makeText(SignUP_Activity.this, "Password Not Matches", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            if(pno.length()!=10)
                            {

                                Toast.makeText(SignUP_Activity.this,"Enter A Valid Phone Number",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                }

                try {
                    if (UsersManager.validate_User(getApplicationContext(), uname))
                    {
                        SharedPreferences pref1 = getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref1.edit();
                        edit.putString("uname",uname);
                        edit.putString("pwd",pwd);
                        edit.apply();
                        UsersManager row = new UsersManager();
                        row.setUname(uname);
                        row.setPwd(pwd);
                        row.setPno(pno);
                        row.insertRecord(getApplicationContext());

                        Intent i = new Intent(SignUP_Activity.this,Main_activity.class);
                        startActivity(i);
                        mophone.setText("");
                        pswd.setText("");
                        pswd1.setText("");
                        usrusr.setText("");
                        Toast.makeText(getApplicationContext(),"SUCCESSFULL",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(),"User Exist from this user name",Toast.LENGTH_SHORT).show();
                        mophone.setText("");
                        pswd.setText("");
                        pswd1.setText("");
                        usrusr.setText("");
                    }
                }
                catch (Exception ex)
                {

                    Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });


        lin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"OPEN LOGIN",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SignUP_Activity.this,login_activity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
