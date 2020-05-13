package Model;

public class Employee {
    public String employeeID;
    public String name;
    public String surname;
    public String birthDate;
    public String role;
    public String employedSince;

    public Employee(String employeeID, String name, String surname,
                    String birthDate, String role, String employedSince){
        this.employeeID = employeeID;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.role = role;
        this.employedSince = employedSince;
    }
}
