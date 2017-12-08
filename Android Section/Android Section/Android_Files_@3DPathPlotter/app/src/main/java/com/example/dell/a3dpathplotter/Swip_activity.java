package com.example.dell.a3dpathplotter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Swip_activity extends AppCompatActivity {

    Button skip,next;
    ViewPager vp1;
    LinearLayout dotslayout;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swip_activity);
        ActionBar action = getSupportActionBar();
        action.hide();
        SharedPreferences pref = getSharedPreferences("TIME",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("FLAG","FALSE");     // next time this screen will not show
        edit.apply();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        FragmentManager fm = getSupportFragmentManager();
        vp1 = (ViewPager) findViewById(R.id.view_pager);
        dotslayout = (LinearLayout) findViewById(R.id.layoutDots);
        skip = (Button) findViewById(R.id.btn_skip);
        next = (Button) findViewById(R.id.btn_next);
        MyAdapter aa = new MyAdapter(getApplicationContext(),fm);
        vp1.setAdapter(aa);
        addBottomDots(0);
        vp1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position)
            {
                addBottomDots(position);
                if(position==3)
                {
                    skip.setVisibility(View.GONE);
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(),SignUP_Activity.class);
                            startActivity(i);
                            finish();
                        }
                    });

                }
                else
                {
                    skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),SignUP_Activity.class);
                startActivity(i);
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp1.setCurrentItem(vp1.getCurrentItem()+1);
            }
        });
    }

    private void addBottomDots(int position)
    {
        TextView[] dots;
        dots = new TextView[4];
        int[] colorActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.array_dot_inactive);
        dotslayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i] = new TextView(getApplicationContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotslayout.addView(dots[i]);
        }
        if(dots.length>0)
        {
            dots[position].setTextColor(colorActive[position]);
        }
    }
}

