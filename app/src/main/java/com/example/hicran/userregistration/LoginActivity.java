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

import static android.app.ProgressDialog.*;

@SuppressWarnings("deprecation")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private ProgressDialog ProgressDialog;

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;


    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);

        ProgressDialog =  new ProgressDialog(this);

        buttonSignin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    private void userLogin(){

        String email =editTextEmail.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            // email is  boşsa
            Toast.makeText(this,"Mail adresinizi yazınız",Toast.LENGTH_SHORT).show();
            return;


        }
        if (TextUtils.isEmpty(password)){
            // password is boşsa
            Toast.makeText(this,"şifrenizi yazınız",Toast.LENGTH_SHORT).show();

        }


        ProgressDialog.setMessage("Giriş yapılıyor...");
        ProgressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        ProgressDialog.dismiss();

                        if(task.isSuccessful()) {
                            // start to profil activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignin) {
            userLogin();
        }
        if(view == textViewSignUp) {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
