package Model;

import Model.Exceptions.InvalidStringException;

public class Employee {
    private String employeeID;
    private String name;
    private String surname;
    private String birthDate;
    private String role;
    private String employedSince;

    public Employee(String employeeID, String name, String surname,
                    String birthDate, EmployeeRoleEnum role, String employedSince)
            throws InvalidStringException {

        if(employeeID.length() == 0 || name.length() == 0 || surname.length() == 0
                || birthDate.length() == 0 || role.toString().length() == 0
                || employedSince.length() == 0){
            throw new InvalidStringException();
        }

        this.employeeID = employeeID;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.role = role.toString();
        this.employedSince = employedSince;
    }

    public String getEmployeeID(){ return employeeID; }

    public String getName(){ return name; }

    public String getSurname() { return surname; }

    public String getBirthDate(){ return birthDate; }

    public String getRole(){ return role; }

    public String getEmployedSince(){ return employedSince; }
}
