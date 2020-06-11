package com.android.projectgc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.android.projectgc.MainActivity;
import com.android.projectgc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {

    private EditText phoneET;
    private Button signinBT;
    public String number="";

    @Override
    protected void onStart() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent i=new Intent(LoginActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LinearLayout linsig=findViewById(R.id.linsig);

        linsig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this, SignUpDetailsActivity.class);
               // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });


        signinBT=findViewById(R.id.signinBT);
        phoneET=findViewById(R.id.phoneET);


       signinBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        number=phoneET.getText().toString().trim();

        if(number.length()==10) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(number);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Intent i = new Intent(LoginActivity.this, OTPActivity.class);
                            i.putExtra("number", number);
                            startActivity(i);
                            System.out.println(document.getData());
                        } else {
                            Toast.makeText(LoginActivity.this, "Create an Account to Login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        System.out.println(task.getException());
                    }
                }
            });




        }else {
            Toast.makeText(LoginActivity.this, "enter a valid mobile number", Toast.LENGTH_SHORT).show();
        }

            }
        });


    }
}
