package com.example.smileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvSignin;
    private EditText txtName, txtEmail, txtPassword, txtRepeatPassword;
    private Button btnRegister;
    private ProgressBar pgbRegister;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String userId;

    private final String NOTNULL = "Can't be empty";
    private final String INCORRECT = "Password incorrect";
    private final String REGISTRATION_SUCCESS = "Registration successful !!!";
    private final String REGISTRATION_FAIL = "Registration failed :(";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regis);

        txtName = findViewById(R.id.name);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.pass);
        tvSignin = findViewById(R.id.tvSignIn);
        txtRepeatPassword = findViewById(R.id.rePass);
        btnRegister = findViewById(R.id.btnRegis);

        pgbRegister = findViewById(R.id.pgbRegister);


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvSignin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSignIn:{
                Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                RegisterActivity.this.startActivity(intent);
                break;
            }
            case R.id.btnRegis:{
                registerUser();
                break;
            }

        }

    }

    private void registerUser() {
        String name = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String repeatPassword = txtRepeatPassword.getText().toString().trim();

        if(name.isEmpty()){
            txtName.setError(NOTNULL);
            txtName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            txtEmail.setError(NOTNULL);
            txtEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            txtPassword.setError(NOTNULL);
            txtPassword.requestFocus();
            return;
        }
        if(repeatPassword.isEmpty()){
            txtRepeatPassword.setError(NOTNULL);
            txtRepeatPassword.requestFocus();
            return;
        }
        if(!password.equals(repeatPassword)){
            txtRepeatPassword.setError(INCORRECT);
            txtRepeatPassword.requestFocus();
            return;
        }

        pgbRegister.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            userId = firebaseAuth.getCurrentUser().getUid();
                            User user = new User(name, email,0, 0, 0);

                            db.collection("Users")
                                    .document(userId)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            pgbRegister.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, REGISTRATION_SUCCESS, Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                                            RegisterActivity.this.startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pgbRegister.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, REGISTRATION_FAIL, Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
