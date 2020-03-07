package com.example.specialistfinder2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerLoginActivity extends AppCompatActivity {
    private EditText cEmail,cPassword; //EditText declaration
    private Button cLogin, cRegistration; //Button declaration

    private FirebaseAuth mAuth; //declaration of firebase
    private FirebaseAuth.AuthStateListener firebaseAuthListener; //State Listener of FirebaseAuth
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        mAuth = FirebaseAuth.getInstance(); //Retrieves state of firebase
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//This stores the state of the currently logged in user
                if(user != null){
                    //Check to see if user is logged in
                    Intent intent = new Intent(CustomerLoginActivity.this, MapActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        };
        cEmail = (EditText) findViewById(R.id.email);
        cPassword = (EditText) findViewById(R.id.password);

        cLogin = (Button) findViewById(R.id.login);
        cRegistration = (Button) findViewById(R.id.registration);

        cRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = cEmail.getText().toString();//Get strings the user writes
                final String password = cPassword.getText().toString();//Get strings the user writes

                //Use mAuth to create an event
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CustomerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Check if creating user was successful
                        if(!task.isSuccessful()){
                            Toast.makeText(CustomerLoginActivity.this, "Sign Up Error!", Toast.LENGTH_SHORT).show();
                        }else{
                            String user_id = mAuth.getCurrentUser().getUid();
                            //We get a Database from Firebase
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id);
                            current_user_db.setValue(true);
                        }
                    }
                });
                cLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String email = cEmail.getText().toString();
                        final String password = cPassword.getText().toString();
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(CustomerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(CustomerLoginActivity.this, "Sign In Error!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
 @Override
 protected void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}




