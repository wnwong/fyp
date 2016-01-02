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
import user.User;
import user.UserLocalStore;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login;
    EditText uname, pwd;
    UserLocalStore userLocalStore;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(this, Main.class);
        startActivity(myIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize TextView and Button
        uname = (EditText) findViewById(R.id.input_uname);
        pwd = (EditText) findViewById(R.id.input_pwd);
        login = (Button) findViewById(R.id.login_Button);

        login.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_Button:
              //  Toast.makeText(getApplicationContext(), "Login button is selected!", Toast.LENGTH_SHORT).show();
                String username = uname.getText().toString();
                String password = pwd.getText().toString();

                User user = new User (username, password);
                authenticate(user);
        }
    }

    private void authenticate(User user){

    }

    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this, Main.class));
    }
}
