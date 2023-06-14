package model;

public class Client {
    private int id;
    private String firstname;
    private String lastname;
    private String address;

    public Client(){}

    public Client(int id, String firstname, String lastname, String address){
        super();
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }
    public Client(String firstname, String lastname, String address){
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public String toString2() {
        return "Client [id =" + id + ", first name =" + firstname + ", last name =" + lastname + ", address =" + address +  "]";
    }

    @Override
    public String toString() {
        return  id + ", " + firstname + ", " + lastname + ", " + address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
