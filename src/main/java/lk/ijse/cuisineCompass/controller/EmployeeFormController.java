package lk.ijse.cuisineCompass.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cuisineCompass.dto.Employee;
import lk.ijse.cuisineCompass.dto.tm.EmployeeTM;
import lk.ijse.cuisineCompass.model.EmployeeModel;
import lk.ijse.cuisineCompass.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class EmployeeFormController implements Initializable{
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private JFXComboBox<String> cBoxJ;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtContact;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJob;

    @FXML
    private TableColumn<?, ?> colMail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<EmployeeTM> tbEmp;

    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        loadJobRoles();
    }*/

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        loadJobRoles();
    }
    void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    void getAll() {
        try {
            ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
            List<Employee> empList = EmployeeModel.getAll();

            for(Employee employee : empList) {
                obList.add(new EmployeeTM(
                        employee.getId(),
                        employee.getName(),
                        employee.getJobRole(),
                        employee.getEmail(),
                        employee.getContact()
                ));
            }
            tbEmp.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    private void loadJobRoles() {
        List<String> jobs = new ArrayList<>();

        jobs.add(0, "Head Chef");
        jobs.add(1, "Sous Chef");
        jobs.add(2, "Chef de Partie");
        jobs.add(3, "Commis Chef");
        jobs.add(4, "Kitchen Assistant");
        jobs.add(5, "Dish Washer");
        jobs.add(6, "Waiter");

        ObservableList<String> obList = FXCollections.observableArrayList();
            for (String code : jobs) {
                obList.add(code);
            }
            cBoxJ.setItems(obList);
    }

    public void btnBackEmpOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/admin_dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.centerOnScreen();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtId.setText("");
        txtName.setText("");
        cBoxJ.setValue("");
        txtContact.setText("");
        txtMail.setText("");
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional <ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete? :)", yes,no).showAndWait();
        if(result.orElse(no)==yes) {

                boolean isDeleted = EmployeeModel.isDeleted(txtId.getText());

                if (isDeleted) {
                    getAll();
                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();
        String name = txtName.getText();
        String jobRole = cBoxJ.getValue();
        String contact = txtContact.getText();
        String email = txtMail.getText();

        if (txtId.getText().matches("^E\\d{3}$")) {
            if (txtMail.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                if (txtContact.getText().matches("^0\\d{9}$")) {
                    try {
                        boolean isUpdated = EmployeeModel.isUpdated(id, name, jobRole, contact, email);
                        if (isUpdated) {
                            getAll();
                            new Alert(Alert.AlertType.CONFIRMATION, "Employee details successfully updated!!").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Error! Try Again").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid contact number!\nTry Again").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid email address!\nTry Again").show();
                //lblPw.setText("Invalid Username!\nTry Again");
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Username!\nTry Again").show();
        }


    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();
        String name = txtName.getText();
        String jobRole = cBoxJ.getValue();
        String contact = txtContact.getText();
        String email = txtMail.getText();

        if (txtId.getText().matches("^E\\d{3}$")) {
            if (txtMail.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                if (txtContact.getText().matches("^0\\d{9}$")) {
                    try {
                        boolean isSaved = EmployeeModel.isSaved(id, name, jobRole, contact, email);
                        if (isSaved) {
                            getAll();
                            new Alert(Alert.AlertType.CONFIRMATION, "Employee added!").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Error! Try Again").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid contact number!\nTry Again").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid email address!\nTry Again").show();
                //lblPw.setText("Invalid Username!\nTry Again");
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Username!\nTry Again").show();
        }

    }

    /*public void initialize(DocFlavor.URL url, ResourceBundle rb) {
        cBoxJob.getItems().add("Head Chef");
        cBoxJob.getItems().add("Sous Chef");
    }*/

    /*public void txtNameOnAction(ActionEvent actionEvent) {
        cBoxJob.requestFocus();
    }*/

    public void txtIdOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();

        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT * FROM employee WHERE emp_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String emp_id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String job_role = resultSet.getString(3);
                String email = resultSet.getString(4);
                String contact = resultSet.getString(5);

                txtId.setText(emp_id);
                txtName.setText(name);
                cBoxJ.setValue(job_role);
                txtMail.setText(email);
                txtContact.setText(contact);

                txtName.requestFocus();

            } else {
                txtName.setText("");
                cBoxJ.setValue("");
                txtContact.setText("");
                txtMail.setText("");
                txtName.requestFocus();
            }

    }

    /*public void cBoxOnAction(ActionEvent actionEvent) {
        txtMail.requestFocus();
    }*/

    /*@FXML
    void cBoxOnAction(ActionEvent event) {
        String jobRole = cBox.getSelectionModel().getSelectedItem();
        try {
            Employee emp = EmployeeModel.searchById(jobe_role);
            lblCustomerName.setText(customer.getName());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }*/

    public void txtNameOnAction(ActionEvent event) {
        cBoxJ.requestFocus();
    }

    public void cBoxJOnAction(ActionEvent actionEvent) {
        txtMail.requestFocus();
    }

    public void txtMailOnAction(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void tbEmpOnMouseClicked(MouseEvent mouseEvent) {
        tbEmp.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection!=null){
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                cBoxJ.setValue(newSelection.getJobRole());
                txtMail.setText(newSelection.getEmail());
                txtContact.setText(newSelection.getContact());
            }
        });
    }
}
