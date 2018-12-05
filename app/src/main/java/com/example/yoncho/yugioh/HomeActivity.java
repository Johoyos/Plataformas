package com.example.yoncho.yugioh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView name, username;
    private Button btn_logout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        btn_logout = findViewById(R.id.btn_logout);

        Intent intent = getIntent();
//        String extraName = intent.getStringExtra("name");
//        String extraUsername = intent.getStringExtra("username");
//
//        name.setText(extraName);
//        username.setText(extraUsername);



        btn_logout.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
