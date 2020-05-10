package Model.Utils.DAOs;

import Model.Employee;
import java.sql.SQLException;

public interface EmployeeDAO {

    public Employee getEmployee(String employeeID) throws SQLException;

    public void addEmployee(String employeeID, String name, String surname,
                            String birthDate, String role, String employedSince)
            throws SQLException;

}
