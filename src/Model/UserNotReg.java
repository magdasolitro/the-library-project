package Model;

import Model.Exceptions.InvalidStringException;

public class UserNotReg {
    private String name;
    private String surname;
    private String phone;
    private String email;

    public UserNotReg(String name, String surname, String phone,
                      String email) throws InvalidStringException {

        if(name.length() == 0 || surname.length() == 0 || phone.length() == 0
                || email.length() == 0){
            throw new InvalidStringException();
        }

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public String getName(){ return name; }

    public String getSurname(){ return surname; }

    public String getPhone(){ return phone; }

    public String getEmail(){ return email; }
}
