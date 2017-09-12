package com.example.hicran.userregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;

    private DatabaseReference databaseReference;

    private EditText editTextName;
    private Button buttonSave;
    private Button buttonLogout;
    private Spinner spinnerKanGrup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toast.makeText(ProfileActivity.this,"ProfileActivity Access",Toast.LENGTH_SHORT).show();

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()== null){
            startActivity(new Intent(this,LoginActivity.class));
        }

        else if (firebaseAuth.getCurrentUser()!= null){
            //startActivity(new Intent(this,ProfileActivity.class));
            //Zaten ProfileActivity desin.ProfileActivity'e sonsuz döngü yaratmış oluyorsun !!!
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextName = (EditText) findViewById(R.id.editTextName);
        spinnerKanGrup = (Spinner) findViewById(R.id.spinnerKanGrup);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Hoşgeldin " + user.getEmail());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);


    }



    private void saveUserInformation() {

        String name = editTextName.getText().toString().trim();

        String kan = spinnerKanGrup.getItemAtPosition(spinnerKanGrup.getSelectedItemPosition()).toString();


        UserInformation userInformation = new UserInformation(name,kan);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this,"Bilgiler Kaydediliyor...",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,ProfileActivity.class));
    }

    @Override
    public void onClick(View view) {

        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        if (view == buttonSave) {
            saveUserInformation();
        }
    }
}
