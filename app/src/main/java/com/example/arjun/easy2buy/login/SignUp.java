package com.example.arjun.easy2buy.login;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import model.User;


import com.example.arjun.easy2buy.user.UserDashboardActivity;
import com.example.arjun.easy2buy.vendor.VendorDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp {
    private FirebaseAuth mAuth;
    private Context context;
    private String userType;
    private String uid;
    SignUp(SignUpActivity signUpActivity, String userType) {
        this.mAuth = FirebaseAuth.getInstance();
        this.context = signUpActivity;
        this.userType = userType;

    }



    public void createUser_fdb(final String username,final String email, final String password, final SignUpActivity signUpActivity) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(signUpActivity, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SignUp", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        uid= user.getUid();


                        addUser(user,username,email,password,userType);
                        switchUser(userType,signUpActivity);
                        updateUi(user);
                    } else {


                        // If sign in fails, display a message to the user.
                        Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUi(null);
                    }
                }
            });
    }

    private void updateUi(FirebaseUser user) {
        if (user != null) {
//            try{
//            // Name, email address, and profile photo Url
//            String email = user.getEmail();
//            String token= String.valueOf(user.getIdToken(true));
//            String name = user.getDisplayName();
//            Uri photoUrl = user.getPhotoUrl();
//            String uid = user.getUid();
//                Log.d("User","email  " +email+   " id: "+uid + "token : "+token);
//            }catch (Exception e){
//                e.printStackTrace();
//            }


            // Check if user's email is verified
           // boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.

        }
    }
private void switchUser(String userType,SignUpActivity signUpActivity){

    switch (userType) {

        case "vendor": {
            Intent intent = new Intent(context, VendorDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("uid", uid);
            context.startActivity(intent);
            signUpActivity.finish();

            break;
        }
        case "user": {
            Intent intent = new Intent(context, UserDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("uid", uid);
            context.startActivity(intent);
            signUpActivity.finish();
            break;
        }
    }
}

    private void addUser(FirebaseUser user,String username,String email,String password,String userType){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("user");

// Creating new user node, which returns the unique key value
// new user node would be /users/$userid/
        //String userId = mRef.push().getKey();
        String userId = user.getUid();



// creating user object
       User dbuser = new User(username,email,password,userId,userType);

// pushing user to 'users' node using the userId
        mRef.child(Objects.requireNonNull(userId)).setValue(dbuser);




    }




}
