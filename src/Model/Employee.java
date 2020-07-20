package Model;

import Model.Exceptions.InvalidStringException;

public class Employee {
    private final String email;
    private final String name;
    private final String surname;
    private final String birthDate;
    private String role;
    private final String employedSince;
    private String password;

    public Employee(String email, String name, String surname,
                    String birthDate, String role, String employedSince,
                    String password)
            throws InvalidStringException {

        if(email.length() == 0 || name.length() == 0 || surname.length() == 0
                || birthDate.length() == 0 || role.length() == 0
                || employedSince.length() == 0 || password.length() == 0){
            throw new InvalidStringException();
        }

        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.role = role;
        this.employedSince = employedSince;
        this.password = password;
    }

    public String getEmail(){ return email; }

    public String getName(){ return name; }

    public String getSurname() { return surname; }

    public String getBirthDate(){ return birthDate; }

    public String getRole(){ return role; }

    public String getEmployedSince(){ return employedSince; }

    public String getPassword(){ return password; }
}
