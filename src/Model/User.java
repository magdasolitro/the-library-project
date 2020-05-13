package Model;

public class User extends UserNotReg{
    public String password;
    public String homeAddress;
    public String streetNumber;
    public String ZIPCode;
    public String homeCity;

    public User(String name, String surname, String phone, String email,
                String password, String homeAddress, String streetNumber, String ZIPCode,
                String homeCity) {
        super(name, surname, phone, email);
        this.password = password;
        this.homeAddress = homeAddress;
        this.streetNumber = streetNumber;
        this.ZIPCode = ZIPCode;
        this.homeCity = homeCity;
    }

    public String getPassword(){ return password; }

    public String getHomeAddress(){ return homeAddress; }

    public String getStreetNumber(){ return streetNumber; }

    public String getZIPCode(){ return ZIPCode; }

    public String getHomeCity(){ return homeCity; }

}
