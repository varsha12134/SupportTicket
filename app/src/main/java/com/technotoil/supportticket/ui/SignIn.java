package com.technotoil.supportticket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.technotoil.supportticket.R;
import com.technotoil.supportticket.db.RealTimeDB;
import com.technotoil.supportticket.db.RoomDB;
import com.technotoil.supportticket.viewModel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {

    AppCompatEditText useremail, userPassword, contact;
    String  password ,currentDateAndTime, cont, name;
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    RoomDB roomDB;
    private boolean mVerificationInProgress = false;
    private boolean otpsent = false;

    FirebaseAuth firebaseAuth;
    DatabaseReference datareference;
    TextView tv_otp;
    ImageView iv_google, iv_facebook, iv_email;
     String verificationId;
    GoogleSignInClient googleSignInClient;
    UserViewModel userViewModel;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{6,}" );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        CardView register = (CardView) findViewById(R.id.register);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        };


//        DatabaseReference reference= RealTimeDB.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        datareference = RealTimeDB.getInstance().getReference("User");

//        userViewModel = new ViewModelProvider(SignIn.this).get(UserViewModel.class);
        useremail = findViewById(R.id.et_username);
        iv_google = findViewById(R.id.iv_google);
        iv_facebook = findViewById(R.id.iv_facebook);
        iv_email = findViewById(R.id.iv_email);
        userPassword = findViewById(R.id.et_user_password);
//        tv_otp = findViewById(R.id.tv_otp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd LLL yyyy hh:mm a", Locale.getDefault());
        currentDateAndTime = sdf.format(new Date());

//        tv_otp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               if( contact.getText().toString().isEmpty()){
//                   contact.setError("Please enter contact no.");
//               }else{
//                   String phone = "+91" + contact.getText().toString();
//                   sendVerificationCode(phone);
//               }
//            }
//        });
        //Google Authentication with firebase

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("483333942464-odklel5q96encp16fs4qe15qp9u9ogo1.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        iv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 100);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataValidation();
//               if( userPassword.getText().toString().isEmpty()){
//                   userPassword.setError("Enter otp");
//               }else{
//                   verifyCode(userPassword.getText().toString());
//               }
//

//                      userViewModel.InsertDataInRoom(new UserModel(name, password, currentDateAndTime));
//                    firebaseAuth.getInstance().createUserWithEmailAndPassword(useremail.getText().toString(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                         @Override
//                         public void onComplete(@NonNull Task<AuthResult> task) {
//                             if (task.isSuccessful()) {
//                                 Toast.makeText(SignIn.this, "Login Success", Toast.LENGTH_SHORT).show();
//                                  firebaseAuth.getCurrentUser();
//                                 Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                                 startActivity(i);
//                             } else {
//                                 Toast.makeText(SignIn.this, "login Again", Toast.LENGTH_SHORT).show();
//                             }
//                         }
//                     });
                }
        });
    }
    private void sendVerificationCode(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(SignIn.this, "send", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(SignIn.this, "failed", Toast.LENGTH_SHORT).show();
                                Log.e("contactFail","contactFail"+e);

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationId = s;
                                otpsent =true;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            startActivity(new Intent(SignIn.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
//        if (mVerificationInProgress) {
//            sendVerificationCode(contact.getText().toString());
//        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }



    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(SignIn.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void dataValidation() {
        name = useremail.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        if(name.isEmpty()) {
            useremail.requestFocus();
            useremail.setError("Filed can't be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(name).matches()) {
            useremail.requestFocus();
            useremail.setError("please enter correct contact no");
        } else if (password.isEmpty()) {
            userPassword.requestFocus();
            userPassword.setError("Filed can't be empty");
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            userPassword.requestFocus();
            userPassword.setError("Enter strong password");
        } else{
//            firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        Toast.makeText(SignIn.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(SignIn.this, "Register User", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                if (signInAccountTask.isSuccessful()) {
                    try {
                        GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                        if (googleSignInAccount != null) {
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(SignIn.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                        Toast.makeText(SignIn.this, "Firebase authentication successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignIn.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    } catch (ApiException e) {
                        throw new RuntimeException(e);
                    }

                }
            }else{
                Toast.makeText(this, "not working", Toast.LENGTH_SHORT).show();
            }
        }

    }
}