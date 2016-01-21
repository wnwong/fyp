package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.secondhandtradingplatform.Add_Gadget_Activity;
import com.example.user.secondhandtradingplatform.Login;
import com.example.user.secondhandtradingplatform.R;
import com.example.user.secondhandtradingplatform.Register;
import com.example.user.secondhandtradingplatform.addGadget;

import user.UserLocalStore;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);

        Fragment frag = new CameraFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, frag);
        fragmentTransaction.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent myIntent = new Intent(Main.this, addGadget.class);
                startActivity(myIntent);
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("OnStart");
        authenticate();
    }

    private boolean authenticate(){
        if(userLocalStore.getLoggedInUser() == null){
            return false;
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        // After User login change the login button into logout button
        if(authenticate() == true){
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_login).setTitle(getString(R.string.logout));
            menu.findItem(R.id.nav_register).setVisible(false);
            //Update nav_header
            TextView username = (TextView) findViewById(R.id.username);
            TextView email = (TextView) findViewById(R.id.email);
            username.setText(userLocalStore.getLoggedInUser().getUsername().toString());
            email.setText(userLocalStore.getLoggedInUser().getEmail().toString());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       Fragment fragment = null;
        String title = getString(R.string.app_name);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {

            fragment = new CameraFragment();
            title = getString(R.string.title_Camera);
            // Handle the camera action
        } else if (id == R.id.nav_tablet) {
            title = getString(R.string.title_Tablet);
        } else if (id == R.id.nav_smartphone) {

        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_login) {
            if(authenticate() == true){
             logoutMessage();
              NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
              Menu menu = navigationView.getMenu();
              menu.findItem(R.id.nav_login).setTitle(getString(R.string.title_activity_login));
                menu.findItem(R.id.nav_register).setVisible(true);
                //Update nav_header
                TextView username = (TextView) findViewById(R.id.username);
                TextView email = (TextView) findViewById(R.id.email);
                username.setText("");
                email.setText("");
          }else{
              Intent myIntent = new Intent(this, Login.class);
              startActivity(myIntent);
              finish();
          }
        } else if (id == R.id.nav_register) {
            Intent myIntent = new Intent(this, Register.class);
            startActivity(myIntent);
            finish();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutMessage(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Main.this);
        dialogBuilder.setMessage("確定要登出嗎？");
        dialogBuilder.setTitle("提示");
        dialogBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

            }
        });
        dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.show();
    }

}
