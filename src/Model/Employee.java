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

    // to be used when retrieving an employee
    public Employee(String email, String name, String surname,
                    String birthDate, String role, String employedSince,
                    String password)
            throws InvalidStringException {

        if(!role.equals(EmployeeRoleEnum.GENERAL_DIRECTOR.toString())
                && !role.equals(EmployeeRoleEnum.LOCAL_DIRECTOR.toString())
                && !role.equals(EmployeeRoleEnum.BOOK_SELLER.toString())
                && !role.equals(EmployeeRoleEnum.CASHIER.toString())){
            throw new InvalidStringException();
        }

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

    // to be used when creating new employee
    public Employee(String email, String name, String surname,
                    String birthDate, EmployeeRoleEnum role, String employedSince,
                    String password)
            throws InvalidStringException {

        if(email.length() == 0 || name.length() == 0 || surname.length() == 0
                || birthDate.length() == 0 || role.toString().length() == 0
                || employedSince.length() == 0 || password.length() == 0){
            throw new InvalidStringException();
        }

        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.role = role.toString();
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
