package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity {

    private TextView txtLoggedInfo;
    private Button btnLoggedBack;
    private DBHelper dbHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        init();

        btnLoggedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoggedInActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void init() {
        txtLoggedInfo = findViewById(R.id.txtLoggedInfo);
        btnLoggedBack = findViewById(R.id.btnLoggedBack);
        dbHelper = new DBHelper(this);

        sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        int id = sharedPreferences.getInt("id", -1);

        if (id >= 0) {
            Cursor cursor = dbHelper.getTableElementById(id);
            cursor.moveToFirst();
            txtLoggedInfo.setText("Üdvözöllek, " + cursor.getString(4) + "!");
        }

    }
}