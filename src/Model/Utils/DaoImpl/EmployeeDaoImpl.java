package Model.Utils.DaoImpl;

import Model.Employee;
import Model.EmployeeRoleEnum;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.EmployeeDAO;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDAO {

    @Override
    public Employee getEmployee(String employeeID) throws SQLException,
            InvalidStringException {
        String sql = "SELECT * FROM employee WHERE employeeID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, employeeID);

        connection.rs = connection.pstmt.executeQuery();

        Employee employee = new Employee(connection.rs.getString("employeeID"),
                connection.rs.getString("name"),
                connection.rs.getString("surname"),
                connection.rs.getString("birthDate"),
                (EmployeeRoleEnum) connection.rs.getObject("role"),
                connection.rs.getString("employedSince"));

        connection.closeConnection();

        return employee;
    }

    @Override
    public void addEmployee(String employeeID, String name, String surname,
                            String birthDate, EmployeeRoleEnum role, String employedSince,
                            String password)
            throws SQLException {
        String sql = "INSERT INTO employee(employeeID, name, surname, birthDate," +
                "role, employedSince, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, employeeID);
        connection.pstmt.setString(2, name);
        connection.pstmt.setString(3, surname);
        connection.pstmt.setString(4, birthDate);
        connection.pstmt.setString(5, role.toString());
        connection.pstmt.setString(6, employedSince);
        connection.pstmt.setString(7, password);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }
}
