package product;

import java.util.ArrayList;
import java.util.List;

public class Camera extends Product {
    public String name;
    public String price;
    public String warranty;
    public String place;
    public int photoid;

    //Constructor
   public Camera(String name, String price, String warranty, String place, int photoid){
        this.name = name;
        this.price = price;
        this.warranty = warranty;
        this.place = place;
        this.photoid = photoid;
    }
    public void add(Camera camera){

        cameras.add(camera);

    }

    public static List<Camera> get (){
        return cameras;
    }

    public static List<Camera> cameras = new ArrayList<>();


    private void initData(){
        cameras = new ArrayList<>();

    }


}
