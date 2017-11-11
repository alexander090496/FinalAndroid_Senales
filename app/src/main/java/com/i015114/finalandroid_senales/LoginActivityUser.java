package com.i015114.finalandroid_senales;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityUser extends AppCompatActivity {

    FirebaseAuth auth;
    EditText editTextuserlogin;
    EditText editTextpassword;
    Button buttonlogin;
    ProgressDialog progressDialog;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        progressDialog = new ProgressDialog(this);
        editTextuserlogin = (EditText) findViewById(R.id.id_user_name);
        editTextpassword = (EditText) findViewById(R.id.id_user_password);
        buttonlogin = (Button) findViewById(R.id.id_btn_login);
        auth = FirebaseAuth.getInstance();



/*
        buttonlogin.setOnClickListener(new View.OnClickListener(){

            public void onclik(View v){
                doLogin();

            }
        });
        */

        authStateListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Toast.makeText(LoginActivityUser.this, "ok" + firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivityUser.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }


        };

    }

    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    public void register(View view) {

        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }


    public void doLogin(View view) {

        String email = editTextuserlogin.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progressDialog.setMessage("loading, plase wait");
            progressDialog.show();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivityUser.this, "Login sucesful", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(LoginActivityUser.this, "Error No login", Toast.LENGTH_SHORT).show();

                        }
                    });


        }
    }
}