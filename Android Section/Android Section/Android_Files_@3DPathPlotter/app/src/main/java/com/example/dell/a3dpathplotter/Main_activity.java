package com.example.dell.a3dpathplotter;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main_activity extends AppCompatActivity implements LocationListener
{
    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 2000;

    Button refresh,logout;
    TextView tvLatitude, tvLongitude, tvTime,tvuser,tvdate;
    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);


        //String currentDate = Common.getYyyymmdd(new java.util.Date());

        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvuser = (TextView) findViewById(R.id.tvuser);
        tvdate = (TextView) findViewById(R.id.tvdate);
        refresh = (Button) findViewById(R.id.refresh);
        logout = (Button) findViewById(R.id.signout);
        refresh.setVisibility(View.GONE);
        SharedPreferences pref1 = getSharedPreferences("user",MODE_PRIVATE);

        String uname = pref1.getString("uname","n");
        if(uname.equals("n"))
        {
            Intent i = new Intent(getApplicationContext(),login_activity.class);
            startActivity(i);
            //finish();
            finish();
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
                SharedPreferences pref1 = getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor edit = pref1.edit();
                edit.clear();
                edit.apply();
                Intent i = new Intent(getApplicationContext(),login_activity.class);
                startActivity(i);
                //finish();
                finish();
            }
        });


        if (!isGPS && !isNetwork) {
            //Log.d(TAG, "Connection off");
            showSettingsAlert();
            getLastLocation();

            //getLocation();
        } else {
            //  Log.d(TAG, "Connection on");
            // check permissions
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (permissionsToRequest.size() > 0) {
//                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
//                            ALL_PERMISSIONS_RESULT);
//                    Log.d(TAG, "Permission requests");
//                    canGetLocation = false;
//                }
//            }

            // get location
            getLocation();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.d(TAG, "onLocationChanged");


        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                //Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    //Log.d(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);


                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    //Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            } else {
                //  Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            if(location==null)
            {
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText("ERROR : NO LAST LOCATION");
                refresh.setVisibility(View.VISIBLE);
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
//                        }
                    }
                });
            }
            //Toast.makeText(getApplicationContext(),"NO LastLocation",Toast.LENGTH_LONG).show();
            // getLocation();
            //Log.d(TAG, provider);
            //Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }





    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        alertDialog.show();
    }

//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(SecondActivity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }

    private void updateUI(Location loc) {
        //Log.d(TAG, "updateUI");

//        if(uname.equals("n"))
//        {
//            Intent i = new Intent(getApplicationContext(),login_activity.class);
//            startActivity(i);
//            //finish();
//            finish();
//        }
       // try {
           // if (UsersManager.have_User(getApplicationContext(), uname, pwd)) {

         //   }
        //}
        //catch (Exception ex)
        //{}
        SharedPreferences pref1 = getSharedPreferences("user", MODE_PRIVATE);
        String uname = pref1.getString("uname", "n");
        String currentDate = Common.getYyyymmdd(new java.util.Date());
        tvuser.setText(uname);
        //String currentDate1 = Common.getYyyymmdd(new java.util.Date());
        tvdate.setText(currentDate);
        tvLatitude.setText(Double.toString(loc.getLatitude()));
        tvLongitude.setText(Double.toString(loc.getLongitude()));
        tvTime.setText(DateFormat.getTimeInstance().format(loc.getTime()));

        try {
            StautsManager row = new StautsManager();
            row.setTime(DateFormat.getTimeInstance().format(loc.getTime()));
            row.setLongi(Double.toString(loc.getLongitude()));
            row.setDate(currentDate);
            row.setUname(uname);
            row.setLati(Double.toString(loc.getLatitude()));
            row.insertRecord(getApplicationContext());
        }
        catch (Exception ex)
        {
            //Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

}
