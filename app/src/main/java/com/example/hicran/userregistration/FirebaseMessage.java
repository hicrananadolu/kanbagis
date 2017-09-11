package com.example.hicran.userregistration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class FirebaseMessage extends AppCompatActivity {

    private static final String TAG = "FirebaseMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_message);

        Button btnShowToken = (Button) findViewById(R.id.button_show_token);
        btnShowToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the token
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Token:" + token);
                Toast.makeText(FirebaseMessage.this , token , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
