package Model.Utils.DAOs;

import Model.Employee;
import Model.EmployeeRoleEnum;
import Model.Exceptions.InvalidStringException;

import java.sql.SQLException;

public interface EmployeeDAO {

    public Employee getEmployee(String employeeID) throws SQLException,
            InvalidStringException;

    public void addEmployee(String employeeID, String name, String surname,
                            String birthDate, EmployeeRoleEnum role, String employedSince)
            throws SQLException;
}
