package com.example.kayoko.myapplication;

/**
 * Created by kayoko on 2016/02/26.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.jar.Manifest;

public class LocationActivity extends Activity implements LocationListener {

    private LocationManager locationManager;
    private GoogleApiClient client;

    private TextView textView;
    private String text = "sartn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text_view);

        text += "onCreate()n";
        textView.setText(text);

        // LocationManager インスタンス生成
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Log.d("LocationActivity", "gpsEnabled");
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            // GPSを設定するように促す
            enableLocationSettings();
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    protected void onResume() {

        text += "onResume()n";
        textView.setText(text);

        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.d("LocationActivity","locationManager.requestLocationUpdates");
            // バックグラウンドから戻ってしまうと例外が発生する場合がある
            try {
                // minTime = 1000msec, minDistance = 50m
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, this);
            } catch (Exception e) {
                e.printStackTrace();

                Toast toast = Toast.makeText(this, "例外が発生、位置情報のPermissionを許可していますか？", Toast.LENGTH_SHORT);
                toast.show();

                //MainActivityに戻す
                finish();
            }}


        } else {
            text += "locationManager=nulln";
            textView.setText(text);
        }

        super.onResume();
    }

    @Override
    protected void onPause() {

        if (locationManager != null) {
            Log.d("LocationActivity","locationManager.removeUpdates");
            // update を止める
            //locationManager.removeUpdates(this);
            locationManager.removeUpdates(this);
        } else {
            text += "onPause()n";
            textView.setText(text);
        }

        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        text += "----------n";
        text += "Latitude=" + String.valueOf(location.getLatitude()) + "n";
        text += "Longitude=" + String.valueOf(location.getLongitude()) + "n";

        // Get the estimated accuracy of this location, in meters.
        // We define accuracy as the radius of 68% confidence. In other words,
        // if you draw a circle centered at this location's latitude and longitude,
        // and with a radius equal to the accuracy, then there is a 68% probability
        // that the true location is inside the circle.
        text += "Accuracy=" + String.valueOf(location.getAccuracy()) + "n";

        text += "Altitude=" + String.valueOf(location.getAltitude()) + "n";
        text += "Time=" + String.valueOf(location.getTime()) + "n";
        text += "Speed=" + String.valueOf(location.getSpeed()) + "n";

        // Get the bearing, in degrees.
        // Bearing is the horizontal direction of travel of this device,
        // and is not related to the device orientation.
        // It is guaranteed to be in the range (0.0, 360.0] if the device has a bearing.
        text += "Bearing=" + String.valueOf(location.getBearing()) + "n";
        text += "----------n";

        textView.setText(text);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                text += "LocationProvider.AVAILABLEn";
                textView.setText(text);

                break;
            case LocationProvider.OUT_OF_SERVICE:
                text += "LocationProvider.OUT_OF_SERVICEn";
                textView.setText(text);
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                text += "LocationProvider.TEMPORARILY_UNAVAILABLEn";
                textView.setText(text);
                break;
        }
    }

    private void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.testgps/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);


    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.testgps/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}