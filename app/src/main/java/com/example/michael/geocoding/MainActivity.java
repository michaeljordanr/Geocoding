package com.example.michael.geocoding;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    private GeocodingFragment geocodingFragment;
    private RevGeocogingFragment revGeocogingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geocodingFragment = new GeocodingFragment();
        revGeocogingFragment = new RevGeocogingFragment();

        if(GeocoderHelper.isGeocoderAvaiable()){
            Toast.makeText(this, "Geocoder presente", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Geocoder ausente", Toast.LENGTH_LONG).show();
        }
    }

    public void doGeocoding(View view){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_placeholder, geocodingFragment, "geoCodingFragment")
                .commit();
    }

    public void doReverseGeocoding(View view){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_placeholder, revGeocogingFragment, "revGeoCodingFragment")
                .commit();
    }

}
