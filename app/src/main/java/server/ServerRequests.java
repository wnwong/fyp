package server;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.User;

public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://php-etrading.rhcloud.com/";

    public ServerRequests(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallback).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new fetchUserDataAsyncTask(user, userCallback).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void>{
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback){
            this.user = user;
            this.userCallback = userCallback;
        }
        @Override
        protected Void doInBackground(Void... params) {
            //Data to be sent to the Server
            Map<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", user.getUsername());
            dataToSend.put("password", user.getPassword());
            dataToSend.put("email", user.getEmail());
            dataToSend.put("location", user.getLocation());
            dataToSend.put("gender", user.getGender());

            String encodedStr = getEncodedData(dataToSend);

            try{
                //Conerting address String to URL
                URL url = new URL(SERVER_ADDRESS + "Register.php");
                //Opening the connection
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                //POST method
                con.setRequestMethod("POST");
                //To enable inputting values using POST method
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                //Write dataToSend to OutputStreamWriter
                writer.write(encodedStr);
                writer.flush();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    private String getEncodedData(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for(String key : data.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(data.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if(sb.length()>0)
                sb.append("&");

            sb.append(key + "=" + value);
        }
        return sb.toString();
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User>{
        User user;
        GetUserCallback userCallback;

        public fetchUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... params) {
            Map<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", user.getUsername());
            dataToSend.put("password", user.getPassword());

            String encodedStr = getEncodedData(dataToSend);
            BufferedReader reader = null;
            User returnedUser=null;
            try{
                //Conerting address String to URL
                URL url = new URL(SERVER_ADDRESS + "FetchUserData.php");
                //Opening the connection
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                //POST method
                con.setRequestMethod("POST");
                //To enable inputting values using POST method
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                //Write dataToSend to OutputStreamWriter
                writer.write(encodedStr);
                writer.flush();
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }
                line = sb.toString();
                JSONObject jObject = new JSONObject(line);

                if(jObject.length() == 0){
                    returnedUser = null;
                }else{
                    //Retrieve the other information back
                    String email = jObject.getString("email");
                    String location = jObject.getString("location");
                    String gender = jObject.getString("gender");

                    returnedUser = new User(user.getUsername(), user.getPassword(), email, location, gender);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }
}
