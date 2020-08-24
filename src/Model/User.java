package Model;

import Model.Exceptions.InvalidStringException;

public class User {
    private String name;
    private String surname;
    private String phone;
    private final String email;
    private String password;
    private String homeAddress;
    private String streetNumber;
    private String ZIPCode;
    private String homeCity;

    public User(String name, String surname, String phone, String email,
                String password, String homeAddress, String streetNumber,
                String ZIPCode, String homeCity) throws InvalidStringException {

        if(name.length() == 0 || surname.length() == 0 || phone.length() == 0
                || email.length() == 0 || password.length() == 0 || homeAddress.length() == 0
                || streetNumber.length() == 0 || ZIPCode.length() == 0
                || homeCity.length() == 0){
            throw new InvalidStringException();
        }

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.homeAddress = homeAddress;
        this.streetNumber = streetNumber;
        this.ZIPCode = ZIPCode;
        this.homeCity = homeCity;
    }

    public String getName(){ return name; }

    public String getSurname(){ return surname; }

    public String getPhone(){ return phone; }

    public String getEmail(){ return email; }

    public String getPassword(){ return password; }

    public String getHomeAddress(){ return homeAddress; }

    public String getStreetNumber(){ return streetNumber; }

    public String getZIPCode(){ return ZIPCode; }

    public String getHomeCity(){ return homeCity; }

}
