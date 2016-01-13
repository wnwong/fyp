package tabs;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.secondhandtradingplatform.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import product.Camera;
import server.ServerRequests;

public class add_Info extends Fragment implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE = 1;
    private static  final int REQUEST_CAMERA = 10;
    public static final String SERVER_ADDRESS = "http://php-etrading.rhcloud.com/";
    ImageView imageToUpload;// downloadedImage;
    ImageButton addGalleryBtn, addCameraBtn;
    Button bUploadImage;
    EditText uploadImageName, downloadImageName;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        imageToUpload = (ImageView) v.findViewById(R.id.imageToUpload);
  //      downloadedImage = (ImageView) v.findViewById(R.id.downloadedImage);
        bUploadImage = (Button) v.findViewById(R.id.bUploadImage);
        addGalleryBtn = (ImageButton) v.findViewById(R.id.addGalleryBtn);
        addCameraBtn = (ImageButton) v.findViewById(R.id.addCameraBtn);

        uploadImageName = (EditText) v.findViewById(R.id.etUploadName);
  //      downloadImageName = (EditText) v.findViewById(R.id.etDownloadName);

  //      imageToUpload.setOnClickListener(this);
        addGalleryBtn.setOnClickListener(this);
        addCameraBtn.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_addinfo,container,false);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data !=null ){
            Uri selectedImage = data.getData();  // Get the address of the selected image
            imageToUpload.setImageURI(selectedImage);
        }else if(requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK && data !=null){
            Uri selectedImage = data.getData();  // Get the address of the selected image
            imageToUpload.setImageURI(selectedImage);

        }
    }

/*    @Override
    public void onResume() {
        super.onResume();
        if(imageToUpload.getDrawable() != null){
            ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap().recycle();
        }
    }
*/

    @Override
    public void onPause() {
        super.onPause();
        imageToUpload.setImageDrawable(null);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bUploadImage:
                Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
                new UploadImage(image, uploadImageName.getText().toString()).execute();
                break;
            case R.id.addGalleryBtn:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.addCameraBtn:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
                break;
    }
    }
    private class UploadImage extends AsyncTask<Void, Void, Void>{
        Bitmap image;
        String name;
        OutputStreamWriter writer = null;
        BufferedReader reader = null;

        public UploadImage(Bitmap image, String name){
            this.image = image;
            this.name = name;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] array = byteArrayOutputStream.toByteArray();
            String encodeImage = Base64.encodeToString(array, Base64.DEFAULT);

            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("image", encodeImage);
            dataToSend.put("name", name);
            System.out.println(dataToSend.toString());
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("image", encodeImage)
                    .appendQueryParameter("name", name);
            String query = builder.build().getEncodedQuery();

            System.out.println(query);
            try{
                URL url = new URL(SERVER_ADDRESS + "SavePicture.php");
                HttpURLConnection   con = (HttpURLConnection) url.openConnection();
                Log.i("custom_check", "HTTP connection opened!!");
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoInput(true);
                Log.i("custom_check", "Start Writing from Server");
                writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(query);
                writer.flush();
                Log.i("custom_check", "Start Reading from Server");
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while((line = reader.readLine()) != null){
                    Log.i("custom_check", line);
                }
                writer.close();
                reader.close();
                con.disconnect();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
        }
    }
}
