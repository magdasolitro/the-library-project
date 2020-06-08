package Model;

import Model.Exceptions.InvalidStringException;

public class User extends UserNotReg{
    private String password;
    private String homeAddress;
    private String streetNumber;
    private String ZIPCode;
    private String homeCity;

    public User(String name, String surname, String phone, String email,
                String password, String homeAddress, String streetNumber,
                String ZIPCode, String homeCity) throws InvalidStringException {
        super(name, surname, phone, email);

        if(password.length() == 0 || homeAddress.length() == 0
                || streetNumber.length() == 0 || ZIPCode.length() == 0
                || homeCity.length() == 0){
            throw new InvalidStringException();
        }

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
