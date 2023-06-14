package bll;

import dao.ClientDao;
import model.Client;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBll {
    private ClientDao clientdao;

    public ClientBll(){
        clientdao = new ClientDao(Client.class);
    }

    public void updateClientFirstName(int id, String firstname) {
        clientdao.updateTable(id, "firstname", firstname);
    }
    public void updateClientLastName(int id, String lastname) {
        clientdao.updateTable(id, "lastname", lastname);
    }
    public void updateClientAddress(int id, String address) {
        clientdao.updateTable(id, "address", address);
    }

    public void truncateClient(){
        clientdao.truncateTable();
    }

    public void deleteClientById(int id){
        clientdao.deleteById(id);
    }
    public List<Client> findAllClients(){
        List<Client> clients;
        clients = clientdao.findAll();
        return clients;
    }

    public int insertClient(String firstName, String lastName, String address) {
        Client client = new Client(firstName, lastName, address);
        int result = clientdao.insertEntry(client);
        if (result == 0) {
            throw new NoSuchElementException("Oops! Couldn't insert a new client.");
        }
        return result;
    }

    public Client findClientById(int id){
        return clientdao.findbyid(id);
    }
}
