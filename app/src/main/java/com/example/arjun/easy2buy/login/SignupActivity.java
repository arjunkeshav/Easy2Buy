package com.example.arjun.easy2buy.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class SignUpActivity extends AppCompatActivity
{
    private EditText mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mPhoneNumber;
    private CheckBox checkBoxRememberMe;
    FirebaseAuth firebaseAuth;
    private static long back_pressed;
    String userType;
    private static final boolean varified = true;
    private static final boolean notvarified = false;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();

        spinner = findViewById(R.id.spinner);

        // Spinner click listener
        spinner = findViewById(R.id.spinner);


        // Initializing a String Array
        String[] category = new String[]{
                "Select Type...",
                "user",
                "vendor"
        };

        final List<String> categoryList = new ArrayList<>(Arrays.asList(category));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,categoryList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // productCatog = (String) parent.getItemAtPosition(position);
                userType = parent.getItemAtPosition(position).toString();
                // If user change the default selection
                // First item is disable and it is used for hint
                if(userType == "Vendor"){
                    mUsernameView.setHint("Enter Shop Name");

                }
                else {
                    mUsernameView.setHint("Enter User Name");
                }
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + userType, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mUsernameView= findViewById(R.id.editTextUsername);
        mEmailView = findViewById(R.id.editTextEmail);
        mPhoneNumber =findViewById(R.id.editTextPhoneNo);

        mPasswordView = findViewById(R.id.editTextPassword);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.login || id == EditorInfo.IME_NULL)
                {
                    checkEmail();
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = findViewById(R.id.signup);
        mEmailSignInButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkEmail();
            }
        });
        TextView textView = findViewById(R.id.textViewLogin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });


        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        //Here we will validate saved preferences
//        if (!new PrefManager(this).isUserLoggedOut())
//        {
//            //user's email and password both are saved in preferences
//
//        }
    }

    // checking email is already exist in firebase or not

    public void checkEmail()
    {
        firebaseAuth.fetchSignInMethodsForEmail(mEmailView.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check = !Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getSignInMethods()).isEmpty();

                if(!check){
                    //return true that email is not found
                    attemptLogin();

                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"Email Already Exist",Toast.LENGTH_SHORT).show();

                    View focusView = mEmailView;
                    focusView.requestFocus();

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin()
    {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();


        boolean cancel = false;
        View focusView = null;
        if ( userType == "select type"){
            Toast.makeText(this,"please select a type",Toast.LENGTH_SHORT).show();


    }
    else {
            // Check for a valid password, if the user entered one.
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }
            if (TextUtils.isEmpty(username)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            }
            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }
            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // save data in local shared preferences
                if (checkBoxRememberMe.isChecked()) {
                    new PrefManager(this).saveLoginDetails(email, password);
                }

                saveLoginDetails(username, email, password,phoneNumber);


            }
        }
    }



    private void saveLoginDetails(String username,String email, String password,String phoneNumber)
    {    try
    {
        new SignUp(SignUpActivity.this,userType).createUser_fdb(username,email,password,phoneNumber,SignUpActivity.this);
    }
    catch (Exception e){
        e.printStackTrace();

    }
    //finally {

    //}



    }

    private boolean isEmailValid(String email)
    {
        //TODO: Replace this with your own logic
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password)
    {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }
}