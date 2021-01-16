package app.simov.taximetroimos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.simov.taximetroimos.models.User;

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences mPref;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    Button mButtonRegister;
    TextInputEditText mTexInputEmail;
    TextInputEditText mTexInputNombre;
    TextInputEditText mTexInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mButtonRegister = findViewById(R.id.btnRegister);
        mTexInputEmail = findViewById(R.id.textInputEmail);
        mTexInputNombre = findViewById(R.id.textInputName);
        mTexInputPassword = findViewById(R.id.textInputPassword);

        //Instanciamos autenticacion.
        mAuth = FirebaseAuth.getInstance();
        //Intanciamos DataBases
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        String name = mTexInputNombre.getText().toString();
        String email = mTexInputEmail.getText().toString();
        String password = mTexInputPassword.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if (password.length()>= 6){
                //Ejecutando la logica de registro en Base de datos.
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    //Validamos si la tarea fue exitosa.
                        if (task.isSuccessful()){
                            //Ejecutamos metodo para Guardar Usuario.
                            saveUser(name,email);
                        }else{
                            Toast.makeText(RegisterActivity.this,"No se pudo registrar el usuario",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }else{
                Toast.makeText(RegisterActivity.this,"La contraseña debe contener almenos 6 'CARACTERES'",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(RegisterActivity.this,"Tienes que llenar todos los campos",Toast.LENGTH_LONG).show();
        }
    }

    private void saveUser(String name, String email) {
        String seledtedUser =  mPref.getString("user","");
        if (seledtedUser.equals("Client")){
            //Instanciamos un nuevo Usuario.
            User user = new User();
            user.setEmail(email);
            user.setNombre(name);


            //Creamos no users y conductores su no existe si no solo creamos valor
            mDatabase.child("Users").child("Clients").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
            mDatabase.child("Users").child("Drivers").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    }
}