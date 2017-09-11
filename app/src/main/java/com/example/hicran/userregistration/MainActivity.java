package com.example.hicran.userregistration;

import android.app.ProgressDialog;
import android.content.Intent;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity  implements View.OnClickListener{


    private Button buttonSignup;

    private EditText editTextEmail;
    private  EditText editTextPassword;
    private TextView textViewSignin;


    private ProgressDialog ProgressDialog;

    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        ProgressDialog = new ProgressDialog(this);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        editTextEmail  = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void registerUser () {

        String email= editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            // email is  boşsa
            Toast.makeText(this,"Mail adresinizi yazınız",Toast.LENGTH_LONG).show();
            return;


        }
        if (TextUtils.isEmpty(password)){
            // password is boşsa
            Toast.makeText(this,"şifrenizi yazınız",Toast.LENGTH_LONG).show();
            return;

        }
        // if validations are ok
        // we will first show a progressbar

        ProgressDialog.setMessage("Giriş yapılıyor...");
        ProgressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this," Register Error",Toast.LENGTH_LONG).show();
                        }
                        ProgressDialog.dismiss();

                    }
                });

    }

    @Override
    public void onClick(View view) {


        if (view == buttonSignup) {
            registerUser();
        }
        if  (view == textViewSignin) {

            //LOGİN.CLASS OLUCAK
            startActivity(new Intent(this,LoginActivity.class));

        }

                
    }
}
