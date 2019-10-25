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

public class SignIn extends AppCompatActivity {

    EditText edEmail;
    EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edEmail = (EditText) findViewById(R.id.edit_email);
        edPassword = (EditText) findViewById(R.id.edit_password);
    }


    public void clickLog(View view){
             final Intent intent = new Intent(this, MapsActivity.class);
            String login = edEmail.getText().toString();
            String passwd = edPassword.getText().toString();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(login, passwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override

                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String msg = task.isSuccessful() ? "SIGN IN OK!":
                                    "SIGN IN ERROR!";
                            Toast.makeText(SignIn.this, msg,
                                    Toast.LENGTH_SHORT).show();

                            if(task.isSuccessful()){
                                startActivity(intent);
                            }
                        }


                    });
    }




    public void clickRegistrar(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
