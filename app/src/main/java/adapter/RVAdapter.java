package adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.user.secondhandtradingplatform.R;

import java.util.ArrayList;
import java.util.List;

import product.Camera;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CameraViewHolder>{

    private List<Camera> cameras = new ArrayList<>();
    private int rowLayout;
    public RVAdapter(List<Camera> cam, int rowLayout){
        this.cameras = cam;
        this.rowLayout = rowLayout;
    }

    public static class CameraViewHolder extends RecyclerView.ViewHolder{
        CardView cview;
        TextView name;
        TextView price;
        TextView warranty;
        TextView place;
        ImageView photo;

        CameraViewHolder(View itemView){
            super(itemView);
            cview = (CardView) itemView.findViewById(R.id.cview);
            name = (TextView) itemView.findViewById(R.id.product_name);
            price = (TextView) itemView.findViewById(R.id.product_price);
            warranty = (TextView) itemView.findViewById(R.id.product_warranty);
            place = (TextView)  itemView.findViewById(R.id.product_place);
            photo = (ImageView) itemView.findViewById(R.id.product_photo);
        }
    }

    @Override
    public CameraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CameraViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CameraViewHolder holder, int position) {
        // Get the list of product
        Camera camera =cameras.get(position);

        // Display the attributes of a product one by one
        holder.name.setText(camera.name);
        holder.price.setText(camera.price);
        holder.warranty.setText(camera.warranty);
        holder.place.setText(camera.place);
        holder.photo.setImageResource(camera.photoid);


    }

    @Override
    public int getItemCount() {
        return cameras.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
