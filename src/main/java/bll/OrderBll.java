package bll;

import dao.OrderDao;
import model.Orders;

public class OrderBll {
    OrderDao orderDao;
    ProductBll productBll;

    public OrderBll(){
        orderDao = new OrderDao(Orders.class);
        productBll = new ProductBll();
    }

    public void updateOrderCID(int id, String value){
        orderDao.updateTable(id, "clientId", value);
    }
    public void updateOrderPID(int id, String value){
        orderDao.updateTable(id, "productId", value);
    }
    public void updateOrderQty(int id, int value){
        orderDao.updateTable(id, "qty", value);
    }

//    public void insertOrder(int clientId, int productId, int qty) {
//        Order order = new Order(clientId, productId, qty);
//        abstractDao.insertEntry(order);
//    }

    public void updateQty(int productId, int qty){
        int newQty = productBll.decreaseQtyBll(productId, qty);
        String newQtyStr = String.valueOf(newQty);
        productBll.updateProductQty(productId, newQtyStr);
    }
}
