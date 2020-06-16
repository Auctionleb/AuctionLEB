package com.example.auctionleb.SignInRegister;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.auctionleb.MainActivity;
import com.example.auctionleb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn,mbtn_login;
    TextView mAlert, mBalance;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.et_name);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mPhone = findViewById(R.id.et_phone);
        mRegisterBtn = findViewById(R.id.btn_register);
        mbtn_login = findViewById(R.id.btn_login);
        mAlert = findViewById(R.id.Alert);
        mBalance = findViewById(R.id.Balance);
        mAuth = FirebaseAuth.getInstance();


        mBalance.setText("0");
        /**
         * just for the alert that 0$ is the available balance for all new users
         */
        findViewById(R.id.btn_register).setOnClickListener(this);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setCancelable(true);
                builder.setTitle("Before Proceeding To The App");
                builder.setMessage("Your Current Balance Is Set To 0$ By Default." +
                        "In Order to Buy Items, Please Visit The Profile Tab And Press 'Add Funds' ");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlert.setVisibility(View.VISIBLE);
                    }
                });
                builder.show();
                registerUser();
            }

        });

        mbtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }


    /**
     * Register a New user and add his data to database
     */
    private void registerUser() {
        final String name = mFullName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        final String phone = mPhone.getText().toString().trim();
        final String balance = mBalance.getText().toString().trim();


        if (name.isEmpty()) {
            mFullName.setError(getString(R.string.input_error_name));
            mFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            mEmail.setError(getString(R.string.input_error_email));
            mEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError(getString(R.string.input_error_email_invalid));
            mEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mPassword.setError(getString(R.string.input_error_password));
            mPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            mPassword.setError(getString(R.string.input_error_password_length));
            mPassword.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            mPhone.setError(getString(R.string.input_error_phone));
            mPhone.requestFocus();
            return;
        }

        if (phone.length() != 8) {
            mPhone.setError(getString(R.string.input_error_phone_invalid));
            mPhone.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(name, email, phone, balance);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Toast.makeText(Register.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                    } else {
                                        //display a failure message
                                        Toast.makeText(Register.this, "Register Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                registerUser();
                break;
        }
    }
}
