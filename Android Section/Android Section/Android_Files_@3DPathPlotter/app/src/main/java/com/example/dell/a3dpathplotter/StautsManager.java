package com.example.dell.a3dpathplotter;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 12-10-2017.
 */

public class StautsManager
{
      private String uname;
      private String date;
      private String time;
      private String lati;
      private String longi;

      public StautsManager(){}


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public boolean insertRecord(Context context) throws Exception
    {
        String qry = "INSERT INTO stauts VALUES('"+uname+"' , '"+date+"' , '"+time+"' , '"+lati+"' , '"+longi+"')";
        if(DataManager.executeUpdate(context,Common.getUrl(),qry))
            return true;
        else
            return false;
    }


    public boolean deleteRecord(Context context) throws Exception
    {
        String qry = "DELETE FROM stauts WHERE uname = '"+uname+"' ";
        if(DataManager.executeUpdate(context,Common.getUrl(),qry))
            return true;
        else
            return false;
    }

    public static List<StautsManager> getRecords(Context context, String qry) throws Exception
    {
        List<StautsManager> list = new ArrayList<>();
        JSONArray arr = DataManager.executeQuery(context,Common.getUrl(),qry);
        if(arr!=null || arr.length()!=0)
        {
            for(int i=0;i<arr.length();i++)
            {
                JSONObject obj = arr.getJSONObject(i);
                StautsManager row = new StautsManager();
                row.setUname(obj.getString("uname"));
                row.setDate(obj.getString("date"));
                row.setTime(obj.getString("time"));
                row.setLati(obj.getString("lati"));
                row.setLongi(obj.getString("longi"));
                list.add(row);
            }
            return list;
        }
        else
            return null;
    }
}
