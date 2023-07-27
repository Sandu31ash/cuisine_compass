package lk.ijse.cuisineCompass.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.model.UserModel;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class SupDashboardFormController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField txtUser;

    @FXML
    private ImageView picUser;
    /*@FXML
    private Label lblDate;*/

    /*public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        lblDate.setText(formattedDate);
    }*/

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtUser.setText(LoginFormController.user);
        getImage(LoginFormController.user);
    }

    public void btnLogoutSupOnAction(ActionEvent actionEvent) throws IOException {

        ButtonType yes = new ButtonType("Yes, Log me out", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No, Just Kidding ;)", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Oh you're gonna leave!\nAre you sure to delete? :/", yes,no).showAndWait();
        if(result.orElse(no)==yes) {

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/login_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("CUISINE COMPASS - Login Form");
            stage.centerOnScreen();
        }
    }

    public void btnRecipeSupOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/recipe_view_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Recipe View");
        stage.centerOnScreen();
        //RecipeViewFormController r = new RecipeViewFormController();
        //r.getLatest();
    }

    public void btnMenuSupOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/menu_view_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu View");
        stage.centerOnScreen();
    }

    public void btnInvSupOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/inventory_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Inventory");
        stage.centerOnScreen();
    }

    public void btnIngSupOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/ingredient_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ingredient");
        stage.centerOnScreen();
    }

    public void btnSupOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/supplier_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Supplier");
        stage.centerOnScreen();
    }

    public void btnSupOrderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/order_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Order");
        stage.centerOnScreen();
    }

    public void btnSupReportOnAction(ActionEvent actionEvent) throws IOException {
        /*AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/sup_report_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Report");
        stage.centerOnScreen();*/

        try{
            Connection con = DBConnection.getInstance().getConnection();

            InputStream input = new FileInputStream(new File("C://Users//HP-computer//ASH//cuisine-compass//src//main//java//lk//ijse//cuisineCompass//util//report//MenuReport.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, null, con);
            JasperViewer.viewReport(fillReport, false);


        }catch(JRException | FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public void btnOrderReportOnAction(ActionEvent event) {
        try{
            Connection con = DBConnection.getInstance().getConnection();

            InputStream input = new FileInputStream(new File("C://Users//HP-computer//ASH//cuisine-compass//src//main//java//lk//ijse//cuisineCompass//util//report//Order.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, null, con);
            JasperViewer.viewReport(fillReport, false);


        }catch(JRException | FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    private void getImage(String userName) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        ResultSet resultSet = null;
        resultSet = UserModel.getImage(userName);
        Image image = null;
        //image = new Image(resultSet.getBinaryStream("image"));
        //if(resultSet == null){
        //pic.setImage(null);
        //}else{
        image = new Image(resultSet.getBinaryStream("image"));
        picUser.setImage(image);
        picUser.setPreserveRatio(false);
        //}
    }

}
