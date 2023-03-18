package com.example.wecare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    TextView LoginTxt;
    Button signUpBtn;
    EditText emailEdTxt, passwordEdTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        LoginTxt = findViewById(R.id.txtLogin);
        signUpBtn = findViewById(R.id.signUp);
        emailEdTxt = findViewById(R.id.emailRegister);
        passwordEdTxt = findViewById(R.id.passwordRegister);

        Intent intent = new Intent(getApplicationContext(), Authentication.class);
        Intent toLogin = new Intent(getApplicationContext(), Authentication.class);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        };
        LoginTxt.setOnClickListener(onClickListener);

        String email = emailEdTxt.getText().toString();
        String password = passwordEdTxt.getText().toString();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(registerUser(email, password)){
                    startActivity(toLogin);
                }
                //Otherwise no action
            }
        });
    }

    public boolean registerUser(String email, String password){
        Database db = new Database(getApplicationContext());

        if(db.registerUser(email, password)){
            Toast.makeText(this, "User Registerd successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show();
        return false;
    }

}