package dao;

import model.Orders;

public class OrderDao extends AbstractDao<Orders>{

    public OrderDao(Class<Orders> type) {
        super(type);
    }

}
