package com.example.user.secondhandtradingplatform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import activity.Main;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button reg;
    EditText uname, pwd, email, location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reg = (Button) findViewById(R.id.regBtn);
        uname = (EditText) findViewById(R.id.r_uname);
        pwd = (EditText) findViewById(R.id.r_pwd);
        email = (EditText) findViewById(R.id.r_email);
        location = (EditText) findViewById(R.id.r_location);

        reg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regBtn:
                System.out.println("clicked");
                Toast.makeText(getApplicationContext(), "Register button is selected!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(this, Main.class);
        startActivity(myIntent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent myIntent = new Intent(this, Main.class);
                startActivity(myIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
