package activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
        import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.user.secondhandtradingplatform.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import product.Camera;
import adapter.RVAdapter;


public class CameraFragment extends Fragment{
    public static final String SERVER_ADDRESS = "http://php-etrading.rhcloud.com/";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        RecyclerView rv;
        rv = (RecyclerView) v.findViewById(R.id.rview);
        rv.setHasFixedSize(true);

        //use a linear layout manager
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
/**********************************************************************************************/
  //testing
        Camera cam = new Camera("Canon PowerShot G7 X", "2500","Yes", "MongKok", R.mipmap.ic_g7);
        Camera cam1 = new Camera("Canon EOS 7D Mark II", "4300", "Yes", "Causeway Bay", R.mipmap.ic_7d);
        Camera cam2 = new Camera("Canon EOS 760D", "5000", "No", "HongKongIsland", R.mipmap.ic_760d);
        cam.add(cam);
        cam1.add(cam1);
        cam2.add(cam2);
/**********************************************************************************************/
        RVAdapter adapter = new RVAdapter(Camera.get(), R.layout.cardview);
        rv.setAdapter(adapter);
//        retrieveGadget();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_camera, container, false);


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class retrieveGadget extends AsyncTask<Void, Void, Camera>{

        @Override
        protected Camera doInBackground(Void... params) {

            try{
                URL url = new URL(SERVER_ADDRESS + "retrieveGadget.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder();
                while((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }
                Log.i("custon_check", "The retrieved gadgets:");
                Log.i("custon_check", sb.toString());

                JSONObject jObject = new JSONObject(sb.toString());

            }catch(Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Camera camera) {
            super.onPostExecute(camera);
        }
    }
}