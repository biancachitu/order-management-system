package model;

public class Orders {
    int id;
    int clientId;
    int productId;
    int qty;

    public Orders(){}

    public Orders(int clientId, int productId, int qty) {
        this.clientId = clientId;
        this.productId = productId;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return  id + ", " + clientId + ", " + productId + ", " + qty;
    }
}
