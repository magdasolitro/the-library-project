package Model.Utils.DAOs;

import Model.Employee;
import Model.Exceptions.InvalidStringException;

import java.sql.SQLException;

public interface EmployeeDAO {

    Employee getEmployee(String employeeEmail) throws SQLException,
            InvalidStringException;

    void addEmployee(Employee employee)
            throws SQLException;
}
