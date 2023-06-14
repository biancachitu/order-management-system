package presentation;

import bll.ProductBll;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductInterface extends JFrame {
    private JTable productTable;
    private JButton addBtn = new JButton("Add");
    private JButton editBtn = new JButton("Edit");
    private JButton deleteBtn = new JButton("Delete");
    JPanel btnPanel = new JPanel();
    ProductBll productBll;
    Reflection<Product> reflection;

    public ProductInterface(){
        setTitle("Product List");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        productBll = new ProductBll();
        reflection = new Reflection<>(Product.class);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Bicicleta", 50, 600));
        products.add(new Product("Fierastrau electric", 36, 200));
        products.add(new Product("Pila electrica", 100, 70));
        products.add(new Product("Masa plianta", 212, 90));
        products.add(new Product("Masina de tuns iarba", 3, 1100));
        products.add(new Product("Gratar", 13, 2100));
        products.add(new Product("Umbrela plaja", 30, 40));
        productBll.truncateProduct();
        productTable = reflection.populateTable(products);

        JPanel productPanel = new JPanel();
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(productPanel);
        contentPane.add(btnPanel, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(productTable), BorderLayout.CENTER);

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(editBtn);

        addBtn.addActionListener(e -> {
            JFrame editDialog = new JFrame("Add Product Information");
            JPanel dialogPanel = new JPanel(new GridLayout(3, 2));
            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField("");
            JLabel qtyLabel = new JLabel("Quantity:");
            JTextField qtyField = new JTextField("");
            JLabel priceLabel = new JLabel("Price:");
            JTextField priceField = new JTextField("");
            dialogPanel.add(nameLabel);
            dialogPanel.add(nameField);
            dialogPanel.add(qtyLabel);
            dialogPanel.add(qtyField);
            dialogPanel.add(priceLabel);
            dialogPanel.add(priceField);

            int result = JOptionPane.showConfirmDialog(editDialog, dialogPanel, "Add Client",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String addedName = nameField.getText();
                int addedQty = Integer.parseInt(qtyField.getText());
                double addedPrice = Double.parseDouble(priceField.getText());

                productBll.insertProduct(addedName, addedQty, addedPrice);
                refreshTable();
            }
        });

        deleteBtn.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                int productId = (int) productTable.getValueAt(selectedRow, 0);
                productBll.deleteProductById(productId);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(ProductInterface.this, "Please select a product to delete.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editBtn.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = new Object[productTable.getColumnCount()];
                for (int column = 0; column < productTable.getColumnCount(); column++) {
                    rowData[column] = productTable.getValueAt(selectedRow, column);
                }

                JFrame editDialog = new JFrame("Edit Product Information");
                JPanel dialogPanel = new JPanel(new GridLayout(3, 2));
                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField(rowData[1].toString());
                JLabel qtyLabel = new JLabel("Quantity:");
                JTextField qtyField = new JTextField(rowData[2].toString());
                JLabel priceLabel = new JLabel("Price:");
                JTextField priceField = new JTextField(rowData[3].toString());
                dialogPanel.add(nameLabel);
                dialogPanel.add(nameField);
                dialogPanel.add(qtyLabel);
                dialogPanel.add(qtyField);
                dialogPanel.add(priceLabel);
                dialogPanel.add(priceField);

                int result = JOptionPane.showConfirmDialog(editDialog, dialogPanel, "Edit Product",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String updatedName = nameField.getText();
                    String updatedQty = qtyField.getText();
                    String updatedPrice = priceField.getText();

                    rowData[1] = updatedName;
                    rowData[2] = updatedQty;
                    rowData[3] = updatedPrice;

                    productTable.setValueAt(updatedName, selectedRow, 1);
                    productTable.setValueAt(updatedQty, selectedRow, 2);
                    productTable.setValueAt(updatedPrice, selectedRow, 3);

                    int id = Integer.parseInt(rowData[0].toString());
                    productBll.updateProductName(id, updatedName);
                    productBll.updateProductQty(id, updatedQty);
                    productBll.updateProductPrice(id,updatedPrice);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void refreshTable() {
        List<Product> allproducts = productBll.findAllProducts();
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);
        for (Product product : allproducts) {
            model.addRow(new Object[]{product.getId(), product.getName(), product.getQty(), product.getPrice()});
        }
    }
}

