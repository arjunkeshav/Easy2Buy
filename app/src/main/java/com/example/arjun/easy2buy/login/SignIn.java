package com.example.arjun.easy2buy.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.example.arjun.easy2buy.admin.AdminDashboardActivity;
import com.example.arjun.easy2buy.newadmin.AdminHomeActivity;
import com.example.arjun.easy2buy.user.UserDashboardActivity;

import com.example.arjun.easy2buy.user.UserHomeActivity;
import com.example.arjun.easy2buy.vendor.VendorDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class SignIn {
    private FirebaseAuth mAuth;
    private Context context;
    private String typename;
    private String uid;





    SignIn(SignInActivity signInActivity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.context= signInActivity;
    }

    public void check_user(String email, String password, final SignInActivity mainActivity) {



        final ProgressDialog mDialog = new ProgressDialog(context);
        mDialog.setMessage("Please Wait..."+email);
        mDialog.setTitle("Loading");
        mDialog.show();
        Bundle basket = new Bundle();
        basket.putString("message", email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mainActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d("signup", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid =user.getUid();

                           // userId = user.getUid();
                            mDialog.dismiss();
                            verify(user,mainActivity);
                           // updateUi(user);

                           // startHomeActivity();

                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w("signup", "signInWithEmail:failure", task.getException());
                           // Context c=mainActivity;
                            Toast.makeText(context, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            //updateUi(null);
                        }
                    }
                });
    }
    private void verify(final FirebaseUser user, final Activity mainActivity){

        if(user!=null){

            final String userId = user.getUid();

          //  Toast.makeText(context,userId,Toast.LENGTH_SHORT).show();


            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("user");
             mRef.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //for (DataSnapshot ds : dataSnapshot.getChildren()) {

                       // Log.v("tmz",""+ ds.getKey()); //displays the key for the node
                      typename = (String) dataSnapshot.child("userType").getValue();

                   // }
                    assert typename != null;
                    switch (typename) {
                        case "user": {

                            Intent intent = new Intent(context, UserHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("uid", uid);
                            context.startActivity(intent);
                            mainActivity.finish();



                            break;
                        }
                        case "admin": {
                            Intent intent = new Intent(context, AdminHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("uid", uid);
                            context.startActivity(intent);
                            mainActivity .finish();
                            break;
                        }
                        case "vendor": {
                            Intent intent = new Intent(context, VendorDashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("uid", uid);
                            context.startActivity(intent);
                            mainActivity.finish();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
//    private void updateUi(FirebaseUser user) {
//        if (user != null) {
//            try{
//                // Name, email address, and profile photo Url
//                String email = user.getEmail();
//                String token= String.valueOf(user.getIdToken(true));
//                String name = user.getDisplayName();
//                Uri photoUrl = user.getPhotoUrl();
//                String uid = user.getUid();
//                Log.d("User","email  " +email+   " id: "+uid + "token : "+token);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//
//            // Check if user's email is verified
//           // boolean emailVerified = user.isEmailVerified();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getToken() instead.
//
//        }
//    }
//    private void startHomeActivity()
//    {
//        Intent intent = new Intent(context, UserDashboardActivity.class);
//        context.startActivity(intent);
//
//    }
//

}
