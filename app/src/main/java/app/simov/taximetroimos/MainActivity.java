package app.simov.taximetroimos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mButtonIAmClient;
    Button mButtonIAmDriver;
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();

        mButtonIAmClient = findViewById(R.id.btnIAmClient);
        mButtonIAmDriver = findViewById(R.id.btnIAmDriver);


        mButtonIAmClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mandamos parametro boton Cliente
                editor.putString("user", "Client");
                editor.apply();
                goToSelectedAuth();
            }
        });

        mButtonIAmDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mandamos parametro boton Conductor
                editor.putString("user", "Driver");
                editor.apply();
                goToSelectedAuth();
            }
        });
    }

    private void goToSelectedAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }


}