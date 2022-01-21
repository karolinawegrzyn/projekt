package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin;
    DBUsers myDBUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);

        btnLogin = findViewById(R.id.btnLogin);

        myDBUsers = new DBUsers(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Wypełnij wsystkie pola.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean result = myDBUsers.checkUsernamePassword(user, pass);
                    if(result == true){
                        Toast.makeText(LoginActivity.this, "Zalogowano.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Nieprawidłowy login lub hasło.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}