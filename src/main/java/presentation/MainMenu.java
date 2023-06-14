package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame {

    private JButton clientBtn;
    private JButton productBtn;
    private JButton orderBtn;
    private JButton billBtn;
    Color bg = new Color(183, 138, 240, 1);

    public MainMenu() {
        setTitle("Main Menu");
        setSize(400, 300);
       //setBackground(bg);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        clientBtn = new JButton("Clients");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(clientBtn, constraints);

        productBtn = new JButton("Products");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(productBtn, constraints);

        orderBtn = new JButton("Orders");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(orderBtn, constraints);

        billBtn = new JButton("Bills");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(billBtn, constraints);

        getContentPane().add(mainPanel);
        getContentPane().setBackground(bg);
        mainPanel.setOpaque(true);
        mainPanel.setBackground(bg);

        clientBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientInterface clientInterface = new ClientInterface();
            }
        });
        productBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProductInterface productInterface = new ProductInterface();
            }
        });
        orderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OrderInterface orderInterface = new OrderInterface();
            }
        });
        billBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BillsInterface billsInterface = new BillsInterface();
            }
        });
    }

}

