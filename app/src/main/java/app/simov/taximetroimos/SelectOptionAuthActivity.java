package app.simov.taximetroimos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;


public class SelectOptionAuthActivity extends AppCompatActivity {


    Toolbar  mToolbar;

    Button mButtonGoToLogin;
    Button mButtonGoToRegister;
    SharedPreferences mPref;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seleccionar Opción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mButtonGoToLogin    = findViewById(R.id.btnGoToLogin);
        mButtonGoToRegister = findViewById(R.id.btnGoToRegister);

        mButtonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
        mButtonGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
    }

    public void goToLogin() {
        Intent intent = new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToRegister() {
        String typeUser = mPref.getString("user", "");
        if (typeUser.equals("client")) {
            //Innte registro cliente
            Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        else {
            //Innte registro conductor
            Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}