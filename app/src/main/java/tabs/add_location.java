package tabs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.secondhandtradingplatform.R;
import com.example.user.secondhandtradingplatform.addGadget;

public class add_location extends Fragment implements View.OnClickListener {
    Button confirm;
    EditText location;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        location = (EditText) v.findViewById(R.id.location);
        confirm = (Button) v.findViewById(R.id.confirmBtn);

        confirm.setOnClickListener(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_addlocation,container,false);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.confirmBtn:
                ((addGadget) getActivity()).getLocation(location.getText().toString());
                break;
        }

    }
}
