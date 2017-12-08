package com.example.dell.a3dpathplotter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Dell on 02-11-2017.
 */

public class MyAdapter extends FragmentPagerAdapter
{
    Context context;
    public MyAdapter(Context cont, FragmentManager fm)
    {
        super(fm);
        this.context = cont;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f1 = null;
        switch(position)
        {
            case 0 : f1 = new sf1();
                break;
            case 1 : f1 = new sf2();
                break;
            case 2 : f1 = new sf3();
                break;
            case 3 : f1 = new sf4();
                break;
        }
        return f1;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
