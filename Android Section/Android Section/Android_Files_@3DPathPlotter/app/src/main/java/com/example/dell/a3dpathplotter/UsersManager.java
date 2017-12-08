package com.example.dell.a3dpathplotter;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 15-09-2017.
 */

public class UsersManager
{
    private String uname;
    private String pwd;
    private String pno;

    public UsersManager(){}


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public boolean insertRecord(Context context) throws Exception
    {
        String qry = "INSERT INTO users VALUES('"+uname+"' , '"+pwd+"' , '"+pno+"')";
        if(DataManager.executeUpdate(context,Common.getUrl(),qry))
            return true;
        else
            return false;
    }

    public boolean updateRecord(Context context) throws Exception
    {
        String qry = "UPDATE users set pwd='"+pwd+"' , pno = '"+pno+"' WHERE uname = '"+uname+"'";
        if(DataManager.executeUpdate(context,Common.getUrl(),qry))
            return true;
        else
            return false;
    }

    public boolean deleteRecord(Context context) throws Exception
    {
        String qry = "DELETE FROM users WHERE uname = '"+uname+"' ";
        if(DataManager.executeUpdate(context,Common.getUrl(),qry))
            return true;
        else
            return false;
    }


    public static List<UsersManager> getRecords(Context context,String qry) throws Exception
    {
        List<UsersManager> list = new ArrayList<>();
        JSONArray arr = DataManager.executeQuery(context,Common.getUrl(),qry);
        if(arr!=null || arr.length()!=0)
        {
            for(int i=0;i<arr.length();i++)
            {
                JSONObject obj = arr.getJSONObject(i);
                UsersManager row = new UsersManager();
                row.setUname(obj.getString("uname"));
                row.setPwd(obj.getString("pwd"));
                row.setPno(obj.getString("pno"));
                list.add(row);
            }
            return list;
        }
        else
            return null;
    }

    public static boolean validate_User(Context context,String user) throws Exception
    {
        List<UsersManager> list = UsersManager.getRecords(context,"SELECT * FROM users WHERE uname = '"+user+"'");
        if(list==null || list.size()==0)
        {
            // true say user is not there
            return true;
        }
        else
            // false say user exists in true
            return false;
    }


    public static boolean have_User(Context context,String uname,String pwd) throws Exception
    {
        List<UsersManager> list = UsersManager.getRecords(context,"SELECT * FROM users WHERE uname = '"+uname+"' and pwd='"+pwd+"'");
        if(list==null || list.size() == 0)
        {
            // not exist;
            return false;
        }
        else
        {
            // user found
            return true;
        }
    }
}
