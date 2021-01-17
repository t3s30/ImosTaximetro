package app.simov.taximetroimos.activities.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import app.simov.taximetroimos.R;
import app.simov.taximetroimos.includes.MyToolbar;
import app.simov.taximetroimos.models.Client;
import app.simov.taximetroimos.providers.AuthProvider;
import app.simov.taximetroimos.providers.ClientProvider;
import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences mPref;
    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;

    Button mButtonRegister;
    TextInputEditText mTexInputEmail;
    TextInputEditText mTexInputNombre;
    TextInputEditText mTexInputPassword;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MyToolbar.show(this,"Registro Usuario",true);

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();

        mButtonRegister = findViewById(R.id.btnRegister);
        mTexInputEmail = findViewById(R.id.textInputEmail);
        mTexInputNombre = findViewById(R.id.textInputName);
        mTexInputPassword = findViewById(R.id.textInputPassword);



        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
            }
        });

    }

    private void  clickRegister() {

        String name = mTexInputNombre.getText().toString();
        String email = mTexInputEmail.getText().toString();
        String password = mTexInputPassword.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if (password.length()>= 6){
                mDialog.show();
               register(name, email, password);

            }else{
                Toast.makeText(RegisterActivity.this,"La contraseña debe contener almenos 6 'CARACTERES'",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(RegisterActivity.this,"Tienes que llenar todos los campos",Toast.LENGTH_LONG).show();
        }
    }

    void register(final String name, final String email, String password) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if (task.isSuccessful()) {
                    //Obtenemos uid de Firebase y lo pasamos como parametro
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Client client = new Client(id, name, email);
                    create(client);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Client client) {
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                  //  Toast.makeText(RegisterActivity.this, "El registro se realizo correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MapClientActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   /* private void saveUser(String id,String name, String email) {
        String seledtedUser =  mPref.getString("user","");
        if (seledtedUser.equals("Client")){
            //Instanciamos un nuevo Usuario.
            User user = new User();
            user.setEmail(email);
            user.setNombre(name);


            //Creamos no users y conductores su no existe si no solo creamos valor
            mDatabase.child("Users").child("Clients").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Se agrego Usuario Correctamente", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(RegisterActivity.this,"Fallo el registro", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }else if (seledtedUser.equals("Driver")){
            //Instanciamos un nuevo Usuario.
            User user = new User();
            user.setEmail(email);
            user.setNombre(name);

            //Creamos no users y conductores su no existe si no solo creamos valor
            mDatabase.child("Users").child("Drivers").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Se agrego Usuario Correctamente", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(RegisterActivity.this,"Fallo el registro", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }*/
}