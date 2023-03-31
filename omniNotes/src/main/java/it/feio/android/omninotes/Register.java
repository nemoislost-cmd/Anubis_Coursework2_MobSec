package it.feio.android.omninotes;

import static it.feio.android.omninotes.utils.Constants.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextConfirmPassword;
    CardView cardOne, cardTwo, cardThree, cardFour;
    TextView textView,boxColor;
    CardView btnRegister;
    boolean atLeast8 = false, hasUpper = false, hasNum = false, hasSym = false, signupClickable = false;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        editTextConfirmPassword=findViewById(R.id.confirmpassword);
        cardOne = findViewById(R.id.cardOne);
        cardTwo = findViewById(R.id.cardTwo);
        cardThree = findViewById(R.id.cardThree);
        cardFour = findViewById(R.id.cardFour);
        btnRegister = findViewById(R.id.btn_register);
        boxColor = findViewById(R.id.boxColor);
        progressBar = findViewById(R.id.progressBar);
        textView=findViewById(R.id.loginNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString(), password = editTextPassword.getText().toString(),  confirmPassword = editTextConfirmPassword.getText().toString();
                checkifEmpty(email, password, confirmPassword);
                if(!password.equals(confirmPassword)){
                    Toast.makeText(Register.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (atLeast8 && hasUpper && hasNum && hasSym == true)
                {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        //Sending verification link
                                        FirebaseUser fuser = mAuth.getCurrentUser();
                                        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(Register.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG,"onFailure: Email not sent "+e.getMessage());
                                            }
                                        });


//                                    Toast.makeText(Register.this, "Account registered!",
//                                            Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(getApplicationContext(),Login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Register.this,"Weak password. Please create a stronger password.",Toast.LENGTH_SHORT).show();
                    return;

                }

            }
        });
        inputUpdate();
    }//End of oncreate

    private void checkifEmpty(String email, String password, String confirmPassword) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(Register.this,"Enter Confirm Password",Toast.LENGTH_SHORT).show();
            return;
        }
    }
    @SuppressLint("ResourceType")
    private void checkCurrentData(String email) {
        if ((atLeast8 && hasUpper && hasNum && hasSym && email.length() > 0)&&(editTextConfirmPassword.length()>0)) {
            signupClickable = true;
            boxColor.setTextColor(Color.WHITE);
            btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            signupClickable = false;
            btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
    }

    @SuppressLint("ResourceType")
    private void passwordCheck() {
        String email = editTextEmail.getText().toString(), password = editTextPassword.getText().toString(),  confirmPassword = editTextConfirmPassword.getText().toString();
        //Check if password contains at least 8characters
        if (password.length() >= 8) {
            atLeast8 = true;
            cardOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            atLeast8 = false;
            cardOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        //Check if password contains at least has a capital letter
        if (password.matches("(.*[A-Z].*)")) {
            hasUpper = true;
            cardTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasUpper = false;
            cardTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        //Check if password contains at least a number
        if (password.matches("(.*[0-9].*)")) {
            hasNum = true;
            cardThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasNum = false;
            cardThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        //Check if password contains at least a special character
        if (password.matches("^(?=.*[@#$%^&+=!.]).*$")) {
            hasSym = true;
            cardFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasSym = false;
            cardFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        checkCurrentData(email);
    }

    private void inputUpdate(){
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}