package app.simov.taximetroimos.activities.client;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import app.simov.taximetroimos.R;

import app.simov.taximetroimos.includes.MyToolbar;



public class MapClientActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_client);
        MyToolbar.show(this, "Cliente", false);

    }


}
