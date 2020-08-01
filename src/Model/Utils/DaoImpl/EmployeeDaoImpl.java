package Model.Utils.DaoImpl;

import Model.Employee;
import Model.EmployeeRoleEnum;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.EmployeeDAO;
import Model.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDAO {

    @Override
    public Employee getEmployee(String employeeEmail) throws SQLException,
            InvalidStringException {
        String sql = "SELECT * FROM employee WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, employeeEmail);

        ResultSet rs = pstmt.executeQuery();

        if(rs.next()) {
            Employee employee = new Employee(rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("birthDate"),
                    rs.getString("role"),
                    rs.getString("employedSince"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();

            connection.closeConnection();

            return employee;
        }

        return null;
    }

    @Override
    public void addEmployee(Employee employee)
            throws SQLException {
        String sql = "INSERT INTO employee(email, name, surname, birthDate," +
                "role, employedSince, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, employee.getEmail());
        pstmt.setString(2, employee.getName());
        pstmt.setString(3, employee.getSurname());
        pstmt.setString(4, employee.getBirthDate());
        pstmt.setString(5, employee.getRole());
        pstmt.setString(6, employee.getEmployedSince());
        pstmt.setString(7, employee.getPassword());

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }
}
