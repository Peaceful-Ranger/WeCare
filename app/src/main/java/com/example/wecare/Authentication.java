package com.example.wecare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Authentication extends AppCompatActivity {
    TextView registerTxt;
    Button signInBtn;
    EditText emailEdTxt, passwordEdTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        registerTxt = findViewById(R.id.txtRegister);
        signInBtn = findViewById(R.id.signIn);
        emailEdTxt = findViewById(R.id.emailLogin);
        passwordEdTxt = findViewById(R.id.loginPassword);


        String email = emailEdTxt.getText().toString();
        String password = passwordEdTxt.getText().toString();

        Intent intent = new Intent(this, SignUpActivity.class);
        Intent toSos = new Intent(this, SosActivity.class);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        };
        registerTxt.setOnClickListener(onClickListener);


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUser(email, password)){
                    startActivity(toSos);
                    Toast.makeText(Authentication.this, "User logged in successfully!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //OtherWise no action
                Toast.makeText(Authentication.this, "No user found! Try creating a account", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean checkUser(String email, String password){
        Database db = new Database(getApplicationContext());

        return db.checkUserInfo(email, password);

    }

}