package presentation;

import bll.ClientBll;
import bll.OrderBll;
import bll.ProductBll;
import model.Orders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderInterface extends JFrame {
    private JLabel clientLabel;
    private JLabel productLabel;
    private JLabel qtyLabel;
    private JButton orderBtn;
    private JTextField clientField;
    private JTextField productField;
    private JTextField qtyField;
    Color bg = new Color(183, 138, 240, 1);

    ProductBll productBll;
    ClientBll clientBll;
    OrderBll orderBll;
    Reflection<Orders> reflection;

    public OrderInterface(){
        setTitle("Order");
        setSize(400, 300);
        // setBackground(bg);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        productBll = new ProductBll();
        clientBll = new ClientBll();
        orderBll = new OrderBll();
        reflection = new Reflection<>(Orders.class);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        clientLabel = new JLabel("Client ID");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(clientLabel, constraints);

        clientField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(clientField, constraints);

        productLabel = new JLabel("Product ID");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(productLabel, constraints);

        productField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(productField, constraints);

        qtyLabel = new JLabel("Quantity");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(qtyLabel, constraints);

        qtyField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(qtyField, constraints);

        orderBtn = new JButton("Order");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(orderBtn, constraints);

        getContentPane().add(mainPanel);
        getContentPane().setBackground(bg);
        mainPanel.setOpaque(true);
        mainPanel.setBackground(bg);

        orderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int clientID = Integer.parseInt(clientField.getText());
                int productID = Integer.parseInt(productField.getText());
                int quantity = Integer.parseInt(qtyField.getText());

                if(quantity > productBll.findProductById(productID).getQty()) {
                    JOptionPane.showMessageDialog(OrderInterface.this, "Sorry, not enough products left.");
                }else if(clientBll.findClientById(clientID) == null) {
                    JOptionPane.showMessageDialog(OrderInterface.this, "Sorry, there is no client with that ID.");
                }else if(productBll.findProductById(productID) == null) {
                    JOptionPane.showMessageDialog(OrderInterface.this, "Sorry, there is no product with that ID.");
                } else {
                    JOptionPane.showMessageDialog(OrderInterface.this, "Order created successfully! :D");
                    reflection.insertOneSingle(new Orders(clientID, productID, quantity));
                    orderBll.updateQty(productID, quantity);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

}
