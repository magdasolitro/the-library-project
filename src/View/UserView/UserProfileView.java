package View.UserView;

import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.User;
import Model.Utils.DAOs.UserDAO;
import Model.Utils.DaoImpl.UserDaoImpl;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class UserProfileView {

    public static GridPane buildUserInfos(String email){
        User user;

        Label homeAddressLabel;
        Label ZIPCodeLabel;
        Label homeCityLabel;
        Label phoneLabel;
        Label emailLabel;

        Font otherInfosFont = new Font("Avenir Book", 20);

        GridPane infosContainer = new GridPane();

        try{
            UserDAO userDAO = new UserDaoImpl();

            user = userDAO.getUser(email);

            if(user == null){
                throw new UserNotInDatabaseException();
            }

            homeAddressLabel = new Label(user.getHomeAddress());
            ZIPCodeLabel = new Label(user.getZIPCode());
            homeCityLabel = new Label(user.getHomeCity());
            phoneLabel = new Label(user.getPhone());
            emailLabel = new Label(user.getEmail());

            ColumnConstraints column = new ColumnConstraints();
            column.setHalignment(HPos.LEFT);

            RowConstraints homeAddressRow = new RowConstraints();
            homeAddressRow.setValignment(VPos.CENTER);
            homeAddressRow.setPercentHeight(100.0 / 5);     // ci sono 5 righe

            RowConstraints ZIPCodeRow = new RowConstraints();
            ZIPCodeRow.setValignment(VPos.CENTER);
            ZIPCodeRow.setPercentHeight(100.0 / 5);

            RowConstraints homeCityRow = new RowConstraints();
            homeCityRow.setValignment(VPos.CENTER);
            homeCityRow.setPercentHeight(100.0 / 5);

            RowConstraints phoneRow = new RowConstraints();
            phoneRow.setValignment(VPos.CENTER);
            phoneRow.setPercentHeight(100.0 / 5);

            RowConstraints emailRow = new RowConstraints();
            emailRow.setValignment(VPos.CENTER);
            emailRow.setPercentHeight(100.0 / 5);

            homeAddressLabel.setFont(otherInfosFont);
            ZIPCodeLabel.setFont(otherInfosFont);
            homeCityLabel.setFont(otherInfosFont);
            phoneLabel.setFont(otherInfosFont);
            emailLabel.setFont(otherInfosFont);

            infosContainer.add(homeAddressLabel, 0, 0);
            infosContainer.add(ZIPCodeLabel, 0, 1);
            infosContainer.add(homeAddressLabel, 0, 2);
            infosContainer.add(phoneLabel, 0, 3);
            infosContainer.add(emailLabel, 0, 4);

        } catch(InvalidStringException ise){
            System.out.println("InvalidStringException: " + ise.getMessage());
        } catch(UserNotInDatabaseException unidbe){
            System.out.println("UserNotInDatabaseException: " + unidbe.getMessage());
        }

        return infosContainer;
    }

    public static Label buildUserNameSurnameLabel(String email){
        User user;

        Label nameSurnameLabel;

        Font nameSurnameFont = new Font("Avenir Next Bold", 70);

        try{
            UserDAO userDAO = new UserDaoImpl();

            user = userDAO.getUser(email);

            if(user == null){
                throw new UserNotInDatabaseException();
            }

            nameSurnameLabel = new Label(user.getName() + " " + user.getSurname());
            nameSurnameLabel.setFont(nameSurnameFont);

            return nameSurnameLabel;

        } catch(InvalidStringException ise){
            System.out.println("InvalidStringException: " + ise.getMessage());
        } catch(UserNotInDatabaseException unidbe){
            System.out.println("UserNotInDatabaseException: " + unidbe.getMessage());
        }

        return null;
    }
}