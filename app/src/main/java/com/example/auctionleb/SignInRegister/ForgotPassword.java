package com.example.auctionleb.SignInRegister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testdatabaseimport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    Button sendLink;
    EditText yourEmail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth= FirebaseAuth.getInstance();
        sendLink=findViewById(R.id.btn_sendlink);
        yourEmail=findViewById(R.id.your_email);

        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail= yourEmail.getText().toString();

                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(ForgotPassword.this, "Please Write Your Valid Email Address.", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this, "Please Check Your Inbox.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPassword.this, Login.class));
                            }
                            else{
                                String message = task.getException().getMessage();
                                Toast.makeText(ForgotPassword.this, "An Error Has Occured, Please Try Again."+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
