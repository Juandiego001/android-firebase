package com.example.app_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity {

    private EditText eCorreo, eContrasena;
    private Button btnRegistrarse, btnIniciar;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContrasena = (EditText) findViewById(R.id.eContrasena);
        firebaseAuth = FirebaseAuth.getInstance();
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnIniciar = findViewById(R.id.btnIniciar);
        progressDialog = new ProgressDialog(this);

    }

    public void registrarse(View v) {
        String correo = eCorreo.getText().toString();
        String contrasena = eContrasena.getText().toString();

        if (correo.equals("") || contrasena.equals("") ) {
            Toast.makeText(this, "Debe ingresar un correo y una contraseña.", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Registrando usuario en línea...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "¡El usuario se ha registrado con éxito!", Toast.LENGTH_LONG).show();
                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this, "Error. El usuario ya existe.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Ocurrió un error al intentar registrar el usuario", Toast.LENGTH_LONG).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
        }
    }

    public void iniciarSesion(View v) {
        String correo = eCorreo.getText().toString();
        String contrasena = eContrasena.getText().toString();

        if (correo.equals("") || contrasena.equals("") ) {
            Toast.makeText(this, "Debe ingresar un correo y una contraseña.", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Iniciando sesión...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String user = correo.substring(0, correo.indexOf("@"));
                                Toast.makeText(MainActivity.this, "Bienvenid@ %s".format(user), Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                                intent.putExtra("WelcomeUser", user);
                                startActivity(intent);

                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this, "Error. El usuario ya existe.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Ocurrió un error al intentar registrar el usuario", Toast.LENGTH_LONG).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
        }
    }
}