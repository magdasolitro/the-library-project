package Model;

public class Employee {
    public String employeeID;
    public String name;
    public String surname;
    public String birthDate;
    public EmployeeRole role;
    public String employedSince;

    public Employee(String employeeID, String name, String surname,
                    String birthDate, EmployeeRole role, String employedSince){
        this.employeeID = employeeID;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.role = role;
        this.employedSince = employedSince;
    }

    public String getEmployeeID(){ return employeeID; }

    public String getName(){ return name; }

    public String getSurname() { return surname; }

    public String getBirthDate(){ return birthDate; }

    public EmployeeRole getRole(){ return role; }

    public String getEmployedSince(){ return employedSince; }
}
