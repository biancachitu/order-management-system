package presentation;

import bll.ClientBll;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClientInterface extends JFrame {
    private JTable clientTable;
    private JButton addBtn = new JButton("Add");
    private JButton editBtn = new JButton("Edit");
    private JButton deleteBtn = new JButton("Delete");
    JPanel btnPanel = new JPanel();
    ClientBll clientBll;
    Reflection<Client> reflection;

    public ClientInterface(){
        setTitle("Client List");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        reflection = new Reflection<>(Client.class);
        clientBll = new ClientBll();

        List<Client> clients = new ArrayList<>();
        clients.add(new Client("Bianca", "Chitu", "Zorilor"));
        clients.add(new Client("Maria", "Pop", "Cucuietii din Deal"));
        clients.add(new Client("Diana", "Princess of Wales", "UK"));
        clients.add(new Client("Where's", "Waldo", "idk"));
        clients.add(new Client("Milburn", "Monopoly", "Monopoly"));
        clientBll.truncateClient();
        clientTable = reflection.populateTable(clients);

        JPanel clientPanel = new JPanel();
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(clientPanel);
        contentPane.add(btnPanel, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(clientTable), BorderLayout.CENTER);

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(editBtn);

        addBtn.addActionListener(e -> {
            JFrame editDialog = new JFrame("Edit Client Information");
            JPanel dialogPanel = new JPanel(new GridLayout(3, 2));
            JLabel fnameLabel = new JLabel("First Name:");
            JTextField fnameField = new JTextField("");
            JLabel lnameLabel = new JLabel("Last Name:");
            JTextField lnameField = new JTextField("");
            JLabel addressLabel = new JLabel("Address:");
            JTextField addressField = new JTextField("");
            dialogPanel.add(fnameLabel);
            dialogPanel.add(fnameField);
            dialogPanel.add(lnameLabel);
            dialogPanel.add(lnameField);
            dialogPanel.add(addressLabel);
            dialogPanel.add(addressField);

            int result = JOptionPane.showConfirmDialog(editDialog, dialogPanel, "Edit Client",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String addedFname = fnameField.getText();
                String addedLname = lnameField.getText();
                String addedAddress = addressField.getText();

                clientBll.insertClient(addedFname, addedLname, addedAddress);
                refreshTable();
            }
        });

        deleteBtn.addActionListener(e -> {
            int selectedRow = clientTable.getSelectedRow();
            if (selectedRow != -1) {
                int clientId = (int) clientTable.getValueAt(selectedRow, 0);
                clientBll.deleteClientById(clientId);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(ClientInterface.this, "Please select a client to delete.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editBtn.addActionListener(e -> {
            int selectedRow = clientTable.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = new Object[clientTable.getColumnCount()];
                for (int column = 0; column < clientTable.getColumnCount(); column++) {
                    rowData[column] = clientTable.getValueAt(selectedRow, column);
                }

                JFrame editDialog = new JFrame("Edit Client Information");
                JPanel dialogPanel = new JPanel(new GridLayout(3, 2));
                JLabel fnameLabel = new JLabel("First Name:");
                JTextField fnameField = new JTextField(rowData[1].toString());
                JLabel lnameLabel = new JLabel("Last Name:");
                JTextField lnameField = new JTextField(rowData[2].toString());
                JLabel addressLabel = new JLabel("Address:");
                JTextField addressField = new JTextField(rowData[3].toString());
                dialogPanel.add(fnameLabel);
                dialogPanel.add(fnameField);
                dialogPanel.add(lnameLabel);
                dialogPanel.add(lnameField);
                dialogPanel.add(addressLabel);
                dialogPanel.add(addressField);

                int result = JOptionPane.showConfirmDialog(editDialog, dialogPanel, "Edit Client",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String updatedFname = fnameField.getText();
                    String updatedLname = lnameField.getText();
                    String updatedAddress = addressField.getText();

                    rowData[1] = updatedFname;
                    rowData[2] = updatedLname;
                    rowData[3] = updatedAddress;

                    clientTable.setValueAt(updatedFname, selectedRow, 1);
                    clientTable.setValueAt(updatedLname, selectedRow, 2);
                    clientTable.setValueAt(updatedAddress, selectedRow, 3);

                    int id = Integer.parseInt(rowData[0].toString());
                    clientBll.updateClientFirstName(id, updatedFname);
                    clientBll.updateClientLastName(id, updatedLname);
                    clientBll.updateClientAddress(id, updatedAddress);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void refreshTable() {
        List<Client> allclients = clientBll.findAllClients();
        DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
        model.setRowCount(0);
        for (Client client : allclients) {
            model.addRow(new Object[]{client.getId(), client.getFirstname(), client.getLastname(), client.getAddress()});
        }
    }
}
