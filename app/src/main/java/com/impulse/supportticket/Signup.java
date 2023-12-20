package com.impulse.supportticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.impulse.supportticket.ui.SignIn;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    AppCompatEditText regEmail, regPassword, regConfPassword;
    CardView register_btn;
    String email, password, confPassword;
    FirebaseAuth firebaseAuth;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{6,}" );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView login = (TextView) findViewById(R.id.login);
        regEmail = findViewById(R.id.et_regEmail);
        regPassword = findViewById(R.id.et_regPassword);
        regConfPassword = findViewById(R.id.et_regConfPassword);
        register_btn = findViewById(R.id.register_btn);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(), SignIn.class);
                startActivity(i);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();
            }
        });

    }

    private void registration() {
        email = regEmail.getText().toString().trim();
        password = regPassword.getText().toString().trim();
        confPassword = regConfPassword.getText().toString().trim();

        if(email.isEmpty()) {
            regEmail.requestFocus();
            regEmail.setError("Filed can't be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            regEmail.requestFocus();
            regEmail.setError("please enter correct email");
        } else if (password.isEmpty()) {
            regPassword.requestFocus();
            regPassword.setError("Filed can't be empty");
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            regPassword.requestFocus();
            regPassword.setError("Enter strong password");
        }
        else if (confPassword.isEmpty()) {
            regConfPassword.requestFocus();
            regConfPassword.setError("Filed can't be empty");
        } else if (confPassword == password) {
            regConfPassword.requestFocus();
            regConfPassword.setError("Enter correct password");
        } else{
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Signup.this, "Register successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signup.this, SignIn.class));
                    }else{
                        Toast.makeText(Signup.this, "Error in Register", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}