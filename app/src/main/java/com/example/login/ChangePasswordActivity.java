package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText username, password, repassword;
    Button btnChangePass;
    DBUsers myDBUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        username = findViewById(R.id.usernamechangePass);
        password = findViewById(R.id.passwordChangePass);
        repassword = findViewById(R.id.repasswordChangePass);

        btnChangePass = findViewById(R.id.btnChangePass);;

        myDBUsers = new DBUsers(this);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(ChangePasswordActivity.this, "Wypełnij wszystkie pola.", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(repass)) {
                        Boolean result = myDBUsers.checkUsername(user);
                        if(result == true){
                             Boolean regResult = myDBUsers.changePassword(user, pass);
                            if(regResult == true){
                                Toast.makeText(ChangePasswordActivity.this, "Hasło zostało zmienione.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Zmiana hasła nie powiodła się.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Nieprawidłowy login.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Hasła nie są zgodne.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}