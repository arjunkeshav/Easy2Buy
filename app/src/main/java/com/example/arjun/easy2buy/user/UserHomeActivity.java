package com.example.arjun.easy2buy.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arjun.easy2buy.R;
import com.roughike.bottombar.BottomBar;

public class UserHomeActivity extends AppCompatActivity {
    BottomBar bottomBar;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        bottomBar = findViewById(R.id.bottomBar);


        for (int i = 0; i < bottomBar.getTabCount(); i++) {
            bottomBar.getTabAtPosition(i).setGravity(Gravity.CENTER_VERTICAL);
        }
        editText =findViewById(R.id.editTextSearch);

        textView = findViewById(R.id.txtSearch);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomeActivity.this,UserSearchActivity.class);
                startActivity(intent);
            }
        });

    }
}
