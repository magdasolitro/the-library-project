package View.EmployeeView;

import Model.Employee;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.Utils.DAOs.EmployeeDAO;
import Model.Utils.DaoImpl.EmployeeDaoImpl;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class EmployeeProfileView {

    public static GridPane buildEmployeeInfosGrid(String email){
        Employee employee;

        Label emailLabel;
        Label birthDateLabel;
        Label roleLabel;
        Label employedSinceLabel;

        Font otherInfosFont = new Font("Avenir Book", 20);

        GridPane infosContainer = new GridPane();

        try{
            EmployeeDAO employeeDAO = new EmployeeDaoImpl();

            employee = employeeDAO.getEmployee(email);

            if(employee == null){
                throw new UserNotInDatabaseException();
            }

            emailLabel = new Label(employee.getEmail());
            birthDateLabel = new Label(employee.getBirthDate());
            roleLabel = new Label(employee.getRole());
            employedSinceLabel = new Label(employee.getEmployedSince());

            ColumnConstraints column = new ColumnConstraints();
            column.setHalignment(HPos.LEFT);

            RowConstraints emailRow = new RowConstraints();
            emailRow.setValignment(VPos.CENTER);
            emailRow.setPercentHeight(100.0 / 4);     // ci sono 6 righe

            RowConstraints birthDateRow = new RowConstraints();
            birthDateRow.setValignment(VPos.CENTER);
            birthDateRow.setPercentHeight(100.0 / 4);

            RowConstraints roleRow = new RowConstraints();
            roleRow.setValignment(VPos.CENTER);
            roleRow.setPercentHeight(100.0 / 4);

            RowConstraints employedSinceRow = new RowConstraints();
            employedSinceRow.setValignment(VPos.CENTER);
            employedSinceRow.setPercentHeight(100.0 / 6);

            emailLabel.setFont(otherInfosFont);
            birthDateLabel.setFont(otherInfosFont);
            roleLabel.setFont(otherInfosFont);
            employedSinceLabel.setFont(otherInfosFont);

            infosContainer.add(emailLabel, 0, 0);
            infosContainer.add(birthDateLabel, 0,1);
            infosContainer.add(roleLabel, 0, 2);
            infosContainer.add(employedSinceLabel, 0, 3);

            infosContainer.setVgap(20);

        } catch(InvalidStringException ise){
            System.out.println("InvalidStringException: " + ise.getMessage());
        } catch(UserNotInDatabaseException unidbe){
            System.out.println("UserNotInDatabaseException: " + unidbe.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return infosContainer;
    }

    public static Label buildEmployeeNameSurnameLabel(String email){
        Employee employee;

        Label nameSurnameLabel;

        Font nameSurnameFont = new Font("Avenir Next Bold", 70);

        try{
            EmployeeDAO employeeDAO = new EmployeeDaoImpl();

            employee = employeeDAO.getEmployee(email);

            if(employee == null){
                throw new UserNotInDatabaseException();
            }

            nameSurnameLabel = new Label(employee.getName() + " " + employee.getSurname());
            nameSurnameLabel.setFont(nameSurnameFont);

            return nameSurnameLabel;

        } catch(InvalidStringException ise){
            System.out.println("InvalidStringException: " + ise.getMessage());
        } catch(UserNotInDatabaseException unidbe){
            System.out.println("UserNotInDatabaseException: " + unidbe.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
