package com.android.projectgc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.projectgc.R;


public class SignUpDetailsActivity extends AppCompatActivity {

    private EditText phoneET,nameET,mailET;
    private Button signupBT;
    private String number="",name="",mail="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);

        phoneET=findViewById(R.id.phoneET);
        nameET=findViewById(R.id.nameET);
        mailET=findViewById(R.id.mailET);
        signupBT=findViewById(R.id.signupBT);



        LinearLayout linlog=findViewById(R.id.linlog);

        linlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUpDetailsActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });


        signupBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=nameET.getText().toString().trim();
                mail=mailET.getText().toString().trim();
                number=phoneET.getText().toString().trim();



                if(name.length()>3 && number.length()==10)
                {
                    Intent i = new Intent(SignUpDetailsActivity.this, OTPActivity.class);
                    i.putExtra("number", number);
                    i.putExtra("mail",mail);
                    i.putExtra("name",name);
                    startActivity(i);

                }
                else
                {
                    Toast.makeText(SignUpDetailsActivity.this, "enter valid details", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}
