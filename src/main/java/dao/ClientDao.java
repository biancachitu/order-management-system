package dao;

import model.Client;

public class ClientDao extends AbstractDao<Client>{
    public ClientDao(Class<Client> type) {
        super(type);
    }
}
