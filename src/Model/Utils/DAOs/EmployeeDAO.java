package Model.Utils.DAOs;

import Model.Employee;
import Model.EmployeeRoleEnum;
import Model.Exceptions.InvalidStringException;

import java.sql.SQLException;

public interface EmployeeDAO {

    public Employee getEmployee(String employeeEmail) throws SQLException,
            InvalidStringException;

    public void addEmployee(Employee employee)
            throws SQLException;
}
