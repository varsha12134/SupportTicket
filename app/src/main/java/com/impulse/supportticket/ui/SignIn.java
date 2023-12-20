package com.impulse.supportticket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.impulse.supportticket.R;
import com.impulse.supportticket.db.RealTimeDB;
import com.impulse.supportticket.db.RoomDB;
import com.impulse.supportticket.viewModel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {

    AppCompatEditText userName, userPassword;
    String name, password ,currentDateAndTime;
    RoomDB roomDB;
    FirebaseAuth firebaseAuth;
    DatabaseReference datareference;
    UserViewModel userViewModel;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{6,}" );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        CardView register = (CardView) findViewById(R.id.register);


//        DatabaseReference reference= RealTimeDB.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        datareference = RealTimeDB.getInstance().getReference("User");

//        userViewModel = new ViewModelProvider(SignIn.this).get(UserViewModel.class);
        userName = findViewById(R.id.et_username);
        userPassword = findViewById(R.id.et_user_password);
        SimpleDateFormat sdf = new SimpleDateFormat("dd LLL yyyy hh:mm a", Locale.getDefault());
        currentDateAndTime = sdf.format(new Date());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataValidation();
                startActivity(new Intent(SignIn.this, MainActivity.class));

//                      userViewModel.InsertDataInRoom(new UserModel(name, password, currentDateAndTime));
/*
                    firebaseAuth.getInstance().createUserWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {
                                 Toast.makeText(SignIn.this, "Login Success", Toast.LENGTH_SHORT).show();
                                  firebaseAuth.getCurrentUser();
                                 Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                 startActivity(i);
                             } else {
                                 Toast.makeText(SignIn.this, "login Again", Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
*/
                }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity( (new Intent(SignIn.this, MainActivity.class)));
            finish();
        }
    }

    public void dataValidation() {
        name = userName.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        if(name.isEmpty()) {
            userName.requestFocus();
            userName.setError("Filed can't be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(name).matches()) {
            userName.requestFocus();
            userName.setError("please enter correct email");
        } else if (password.isEmpty()) {
            userPassword.requestFocus();
            userPassword.setError("Filed can't be empty");
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            userPassword.requestFocus();
            userPassword.setError("Enter strong password");
        } else{
            firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignIn.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SignIn.this, "Register User", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}