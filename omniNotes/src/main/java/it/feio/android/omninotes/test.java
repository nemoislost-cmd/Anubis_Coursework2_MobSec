//package it.feio.android.omninotes;
//
//import static it.feio.android.omninotes.utils.Constants.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//import java.util.HashMap;
//import java.util.regex.Pattern;
//
//public class test extends AppCompatActivity {
//
//    TextInputEditText editTextEmail, editTextPassword, editTextConfirmPassword;
//    private CardView frameOne, frameTwo, frameThree, frameFour;
//    private TextView tvColor;
//    private CardView btnRegister;
//    private boolean isAtLeast8 = false, hasUppercase = false, hasNumber = false, hasSymbol = false, isRegistrationClickable = false;
//
//    //    Button buttonReg;
//    FirebaseAuth mAuth;
//    ProgressBar progressBar;
//    TextView textView;
//
//    @SuppressLint("ResourceType")
//    private void passwordCheck() {
//        String password;
//        password = String.valueOf(editTextPassword.getText());
//        if (password.length() >= 8) {
//            isAtLeast8 = true;
//            frameOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent1)));
//        } else {
//            isAtLeast8 = false;
//            frameOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
//        }
//        if (password.matches("(.*[A-Z].*)")) {
//            hasUppercase = true;
//            frameTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent1)));
//        } else {
//            hasUppercase = false;
//            frameTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
//        }
//        if (password.matches("(.*[0-9].*)")) {
//            hasNumber = true;
//            frameThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent1)));
//        } else {
//            hasNumber = false;
//            frameThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
//        }
//        if (password.matches("^(?=.*[_.()]).*$")) {
//            hasSymbol = true;
//            frameFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent1)));
//        } else {
//            hasSymbol = false;
//            frameFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
//        }
//    }
//
//    private static final Pattern PASSWORD_PATTERN =
//            Pattern.compile("^" +
//                    "(?=.*[@#$%^&+=!.])" +     // at least 1 special character
//                    "(?=.*[0-9])" +          // at least 1 number
//                    "(?=\\S+$)" +            // no white spaces
//                    ".{8,}" +                // at least 8 characters
//                    "$");
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        mAuth=FirebaseAuth.getInstance();
//        editTextEmail=findViewById(R.id.email);
//        editTextPassword=findViewById(R.id.password);
//        editTextConfirmPassword=findViewById(R.id.confirmpassword);
//        frameOne = findViewById(R.id.frameOne);
//        frameTwo = findViewById(R.id.frameTwo);
//        frameThree = findViewById(R.id.frameThree);
//        frameFour = findViewById(R.id.frameFour);
//        btnRegister = findViewById(R.id.btn_register);
//        tvColor = findViewById(R.id.tvColor);
////        buttonReg=findViewById(R.id.btn_register);
//        progressBar = findViewById(R.id.progressBar);
//        textView=findViewById(R.id.loginNow);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onClick(View view) {
//                progressBar.setVisibility(View.VISIBLE);
//
//                String email, password, confirmPassword;
//                email = String.valueOf(editTextEmail.getText());
//                password = String.valueOf(editTextPassword.getText());
//                confirmPassword = String.valueOf(editTextConfirmPassword.getText());
//
//                if(TextUtils.isEmpty(email)){
//                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if(TextUtils.isEmpty(password)){
//                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if(TextUtils.isEmpty(confirmPassword)){
//                    Toast.makeText(Register.this,"Enter Confirm Password",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                //!!!!!!!!!!!!!!!!!!!Start of password validation!!!!!!!!!!!!!!!!!!!!!!!!!
//                if (isAtLeast8 && hasUppercase && hasNumber && hasSymbol && email.length() > 0) {
//                    isRegistrationClickable = true;
//                    tvColor.setTextColor(Color.WHITE);
//                    btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
//                } else {
//                    isRegistrationClickable = false;
//                    btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
//                }
//                //!!!!!!!!!!!!!!!!!!!End of password validation!!!!!!!!!!!!!!!!!!!!!!!!!!
//                if(!password.equals(confirmPassword)){
//                    Toast.makeText(Register.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);
//                                if (task.isSuccessful()) {
//                                    //Sending verification link
//                                    FirebaseUser fuser = mAuth.getCurrentUser();
//                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(Register.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d(TAG,"onFailure: Email not sent "+e.getMessage());
//                                        }
//                                    });
//
//
////                                    Toast.makeText(Register.this, "Account registered!",
////                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(),OTPcheck.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(Register.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });
//    }
//    private void inputChange(){
//        editTextPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                passwordCheck();
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }
//}