package presentation;

import bll.ProductBll;
import model.Orders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class BillsInterface extends JFrame{

    JTable billsTable;
    Reflection<Orders> reflection;
    ProductBll productBll;

    public BillsInterface(){
        productBll = new ProductBll();
        reflection = new Reflection<Orders>(Orders.class);
        billsTable  = reflection.putInTable();
        DefaultTableModel model = (DefaultTableModel) billsTable.getModel();
        Double[] data = new Double[100];
        for(int i = 0 ; i < model.getRowCount(); i++){
            double price = productBll.findProductById((int) model.getValueAt(i,2)).getPrice();
            Integer qtyAux = (Integer) model.getValueAt(i,3);
            data[i] = price * Double.parseDouble(qtyAux.toString());
        }
        model.addColumn("Total price", data);
        setTitle("Bills List");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(billsTable);
        getContentPane().add(scrollPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTable getBillsTable() {
        return billsTable;
    }
}
