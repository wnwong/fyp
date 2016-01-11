package tabs;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.secondhandtradingplatform.R;

import org.w3c.dom.Text;

import java.io.EOFException;

public class add_Info extends Fragment implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView imageToUpload, downloadedImage;
    Button bUploadImage, bDownloadImage;
    EditText uploadImageName, downloadImageName;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        imageToUpload = (ImageView) v.findViewById(R.id.imageToUpload);
        downloadedImage = (ImageView) v.findViewById(R.id.downloadedImage);

        bUploadImage = (Button) v.findViewById(R.id.bUploadImage);
        bDownloadImage = (Button) v.findViewById(R.id.bDownloadImage);

        uploadImageName = (EditText) v.findViewById(R.id.etUploadName);
        downloadImageName = (EditText) v.findViewById(R.id.etDownloadName);

        imageToUpload.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);
        bDownloadImage.setOnClickListener(this);
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
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE );
                break;
            case R.id.bUploadImage:

                break;
            case R.id.bDownloadImage:

                break;
        }
    }
}
