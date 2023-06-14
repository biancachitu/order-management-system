package model;

public class Product {
    private int id;
    private String name;
    private int qty;
    private double price;

    public Product(){}

    public Product(int id, String name, int qty, double price){
        super();
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public Product(String name, int qty, double price){
        super();
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void increaseQty(Product product){
        product.setQty(product.getQty() + 1);
    }

    public int decreaseQty(int productId, int decreaseQuantity) {
        int currentQuantity = this.getQty();
        int newQuantity = currentQuantity - decreaseQuantity;
        if (newQuantity < 0) {
            newQuantity = 0;
        }
        this.setQty(newQuantity);
        return newQuantity;
    }


    @Override
    public String toString() {
        return  id + ", " + name + ", " + qty + ", " + price;
    }
}
