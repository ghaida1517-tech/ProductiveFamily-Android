package com.haneen.productivefamily;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {


    Button regToLoginBtn, regBtn;
    ImageView regLogoImage;
    TextView regLogoText, regLoganText;
    TextInputLayout regUsername, regPassword, regEmail,regPhoneNO,regName,regType;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        regToLoginBtn = findViewById(R.id.call_login_screen);
        regLogoImage = findViewById(R.id.signup_image);
        regLogoText = findViewById(R.id.signup_logo_desc);
        regLoganText = findViewById(R.id.signup_desc);
        regUsername = findViewById(R.id.signup_username);
        regPassword = findViewById(R.id.signup_password);
        regEmail = findViewById(R.id.signup_email);
        regName = findViewById(R.id.signup_name);
        regPhoneNO = findViewById(R.id.signup_phoneNumber);
        regBtn = findViewById(R.id.signup_btn);
        progressBar= findViewById(R.id.signup_progress_bar);

        String[] type = new String[] {"Productive Family","Customer","Driver"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.user_type_drop_down_list,
                type
        );

        AutoCompleteTextView autoCompleteTextView= findViewById(R.id.filed_type);
        autoCompleteTextView.setAdapter(adapter);

        regType = findViewById(R.id.signup_user_type);

        regToLoginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SignUp.this, Login.class);
            Pair[] pairs = new Pair[10];
            pairs[0] = new Pair<View, String>(regLogoImage, "logo_image");
            pairs[1] = new Pair<View, String>(regLogoText, "logo_text");
            pairs[2] = new Pair<View, String>(regLoganText, "logo_desc");
            pairs[3] = new Pair<View, String>(regUsername, "username_tran");
            pairs[4] = new Pair<View, String>(regEmail, "username_tran");
            pairs[5] = new Pair<View, String>(regPhoneNO, "username_tran");
            pairs[6] = new Pair<View, String>(regName, "username_tran");
            pairs[7] = new Pair<View, String>(regPassword, "password_tran");
            pairs[8] = new Pair<View, String>(regBtn, "button_tran");
            pairs[9] = new Pair<View, String>(regToLoginBtn, "login_signup_tran");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(intent, options.toBundle());
            finish();
        });

    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
//        String noWhiteSpace = "\\A\\w{4,20}\\z";
        String noWhiteSpace = "^[^\\s]+$";
        String specialCharacters = "^[^\\s#!%?$*+؟]+$";
//        String noWhiteSpace = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[^\\s#?%!؟]{6,12}";
//        String noWhiteSpace = "[^-\\s]";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        }
        else if (!val.matches(specialCharacters)) {
            regUsername.setError("Special characters are not allowed");
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNO.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneNO.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNO.setError(null);
            regPhoneNO.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
//                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateType() {
        String pf_user = "Productive Family";
        String customer_user = "Customer";
        String driver_user = "Driver";
        String val = regType.getEditText().getText().toString();

        if (val.isEmpty()) {
            regType.setError("Field cannot be empty");
            return false;
        }
        else if (!(val.matches(pf_user) | val.matches(customer_user) | val.matches(driver_user))) {
            regType.setError("Invalid user type");
            return false;
        }
        else {
            regType.setError(null);
            regType.setErrorEnabled(false);
            return true;
        }
    }


    //This function will execute when user click on Register Button
    public void registerUser(View view) {
        // Performing Validation by calling validation functions
        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()  | !validateType() ) {
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }

    }

}