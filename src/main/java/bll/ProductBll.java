package bll;


import dao.ProductDao;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBll {
    private ProductDao productdao;

    public ProductBll(){
        productdao = new ProductDao(Product.class);
    }

    public void updateProductName(int id, String name) {
        productdao.updateTable(id, "name", name);
    }
    public void updateProductQty(int id, String qty) {
        productdao.updateTable(id, "qty", qty);
    }
    public void updateProductPrice(int id, String price) {
        productdao.updateTable(id, "price", price);
    }

    public void truncateProduct(){
        productdao.truncateTable();
    }

    public int insertProduct( String name, int qty, double price) {
        Product product = new Product(name, qty, price);
        int result = productdao.insertEntry(product);
        if (result == 0) {
            throw new NoSuchElementException("Oops! Couldn't insert a new product.");
        }
        return result;
    }

    public void deleteProductById(int id){
        productdao.deleteById(id);
    }

    public List<Product> findAllProducts(){
        List<Product> products;
        products = productdao.findAll();
        return products;
    }

    public Product findProductById(int id){
        return productdao.findbyid(id);
    }

    public int decreaseQtyBll(int id, int qty){
        int newQty = productdao.findbyid(id).decreaseQty(id,qty);
        return newQty;
    }
}
