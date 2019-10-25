package com.example.hellcife_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {

    EditText edEmail;
    EditText edPassword;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
                edEmail = (EditText) findViewById(R.id.edit_email);
                edPassword = (EditText) findViewById(R.id.edit_password);
                intent = new Intent(this, MapsActivity.class);

    }


    public void clickRegistrar(View view) {
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg = task.isSuccessful() ?
                                "SIGN UP OK!":
                                "SIGN UP ERROR!";
                        Toast.makeText(SignUpActivity.this, msg,
                                Toast.LENGTH_SHORT).show();

                        if(task.isSuccessful()){
                            startActivity(intent);
                        }

                    }
                });
    }
}



