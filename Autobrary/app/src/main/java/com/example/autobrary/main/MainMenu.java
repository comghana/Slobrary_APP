package com.example.autobrary.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.autobrary.R;
import com.example.autobrary.auth.LoginActivity;

public class MainMenu extends AppCompatActivity {
    Button btnLog;
    Fragment frag1, frag2, frag3;
    FrameLayout fram;

    FragmentManager fragman;
    FragmentTransaction tran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //메뉴 바 색 지정

        fragman = getSupportFragmentManager();
        tran = fragman.beginTransaction();

        btnLog = findViewById(R.id.btnLog);

        fram = findViewById(R.id.view);

        //tran.add(R.id.view, frag1);
        tran.commit();
        Button loginBt = (Button) findViewById(R.id.btnLog);
        loginBt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginAct = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginAct);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflate = this.getMenuInflater();
        mInflate.inflate(R.menu.menu, menu);
        return true;
    }


}
