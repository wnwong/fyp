package data_model;


import java.util.ArrayList;
import java.util.List;

import product.Product;

public class Model {
    private String product = null;
    private List<Product> product_list = new ArrayList<>();

    public Model(String product){
        this.product = product;
    }

    public void add(Product p){
        product_list.add(p);
    }

    public List<Product> get(){
        return product_list;
    }
}
