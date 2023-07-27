package lk.ijse.cuisineCompass.controller;

import com.jfoenix.controls.JFXBadge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cuisineCompass.model.RecipeModel;
import lk.ijse.cuisineCompass.model.UserModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class LoginFormController {

    @FXML
    private AnchorPane pane;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPw;
    @FXML
    private Button btnLogin;
    public static String user;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {

            String userName = txtUsername.getText();
            String pw = txtPw.getText();
            user = txtUsername.getText();

            //try {
            boolean isValid = UserModel.isValid(userName, pw);
            if (isValid) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/cuisineCompass/view/admin_dashboard_form.fxml"));
                Parent root = loader.load();
                AdminDashboardFormController adDash = loader.getController();
                //adDash.setName(userName);

                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/admin_dashboard_form.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                //Stage stage = (Stage) pane.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Admin Dashboard");
                stage.centerOnScreen();
                //AdminDashboardFormController.setText(userName);


            } else if (UserModel.isSup(userName, pw)) {

                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/sup_dashboard_form.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Supervisor Dashboard");
                stage.centerOnScreen();

            } else {
                new Alert(Alert.AlertType.ERROR, "Login Failed!\nInvalid Username or Password").show();
            }
            // } catch (SQLException e) {
            // e.printStackTrace();
            //}
            //stage.show();
    }
        public void txtUsernameOnAction (ActionEvent actionEvent){ txtPw.requestFocus(); }

        public void txtPwOnAction (ActionEvent actionEvent){
            btnLogin.requestFocus();
        }



}
