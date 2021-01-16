package app.simov.taximetroimos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        String seledtedUser =  mPref.getString("user","");
        Toast.makeText(this,"thisSeleccionaste : "+seledtedUser, Toast.LENGTH_LONG).show();

    }
}