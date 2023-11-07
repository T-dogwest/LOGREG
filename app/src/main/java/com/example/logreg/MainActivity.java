package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etxtUsernameOrEmail, etxtPassword;
    private Button btnLogin, btnToRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameOrEmail = etxtUsernameOrEmail.getText().toString();
                String password = etxtPassword.getText().toString();

                if (dbHelper.checkUserByUsername(usernameOrEmail, password)) {
                    Toast.makeText(MainActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                    login(usernameOrEmail, password);
                } else if (dbHelper.checkUserByEmail(usernameOrEmail, password)) {
                    Toast.makeText(MainActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                    login(usernameOrEmail, password);
                } else {
                    Toast.makeText(MainActivity.this, "Sikertelen bejelentkezés!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void login(String usernameOrEmail, String password) {
        Cursor cursor = dbHelper.getTableElementByUsernameOrEmail(usernameOrEmail, password);
        cursor.moveToFirst();

        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", cursor.getInt(0));
        editor.apply();

        startActivity(new Intent(MainActivity.this, LoggedInActivity.class));
        finish();
    }

    private void init() {
        etxtUsernameOrEmail = findViewById(R.id.etxtUsernameOrEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);
        dbHelper = new DBHelper(this);
    }

}