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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import activity.Main;
import user.User;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button reg;
    EditText uname, pwd, email, location;
    RadioButton male,female;
    RadioGroup rgroup;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize EditText and Button
        reg = (Button) findViewById(R.id.regBtn);
        uname = (EditText) findViewById(R.id.r_uname);
        pwd = (EditText) findViewById(R.id.r_pwd);
        email = (EditText) findViewById(R.id.r_email);
        location = (EditText) findViewById(R.id.r_location);
        male = (RadioButton) findViewById(R.id.mButton);
        female = (RadioButton) findViewById(R.id.fButton);
        rgroup = (RadioGroup) findViewById(R.id.rgroup);

        reg.setOnClickListener(this);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
        rgroup.setOnCheckedChangeListener(listener);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regBtn:
                //Toast.makeText(getApplicationContext(), "Register button is selected!", Toast.LENGTH_SHORT).show();
                String rUsername = uname.getText().toString();
                String rPassword = pwd.getText().toString();
                String rEmail = email.getText().toString();
                String rLocation = location.getText().toString();


                User user = new User(rUsername, rPassword, rEmail, rLocation);
               // registerUser(user);
                break;
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


        private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int p = group.indexOfChild((RadioButton) findViewById(checkedId));
                int count = group.getChildCount();
                switch (checkedId){
                    case R.id.mButton:
                        gender = "male";
                        Toast.makeText(getApplicationContext(), "Male button is selected!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fButton:
                        gender = "female";
                        Toast.makeText(getApplicationContext(), "Female button is selected!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

}
