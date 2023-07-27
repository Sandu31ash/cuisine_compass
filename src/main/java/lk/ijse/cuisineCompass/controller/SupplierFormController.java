package lk.ijse.cuisineCompass.controller;

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
import lk.ijse.cuisineCompass.dto.Supplier;
import lk.ijse.cuisineCompass.dto.tm.EmployeeTM;
import lk.ijse.cuisineCompass.dto.tm.SupplierTM;
import lk.ijse.cuisineCompass.model.EmployeeModel;
import lk.ijse.cuisineCompass.model.SupplierModel;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private Button btnBackSupSupp;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<SupplierTM> tbSuppSup;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtName;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }
    void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    void getAll() {
        try {
            ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
            List<Supplier> supList = SupplierModel.getAll();

            for(Supplier sup : supList) {
                obList.add(new SupplierTM(
                        sup.getId(),
                        sup.getName(),
                        sup.getEmail(),
                        sup.getContact()
                ));
            }
            tbSuppSup.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnBackSupSuppOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/sup_dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Supervisor Dashboard");
        stage.centerOnScreen();
    }

    public void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtMail.getText();
        String contact = txtContact.getText();

        if (txtId.getText().matches("^S\\d{3}$")) {
            if (txtMail.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                if (txtContact.getText().matches("^0\\d{9}$")) {
            try {
                boolean isSaved = SupplierModel.isSaved(id, name, email, contact);
                if (isSaved) {
                    getAll();
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier added!").show();
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
            new Alert(Alert.AlertType.ERROR, "Invalid supplier id!\nTry Again").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String email = txtMail.getText();

        try {
            boolean isUpdated = SupplierModel.isUpdated(id, name, contact, email);
            if (isUpdated) {
                getAll();
                new Alert(Alert.AlertType.CONFIRMATION, "Employee details successfully updated!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error! Try Again").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent event) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete? :)", yes,no).showAndWait();
        if(result.orElse(no)==yes) {

            boolean isDeleted = SupplierModel.isDeleted(txtId.getText());

            if (isDeleted) {
                getAll();
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }
        }
    }

    public void btnClearOnAction(ActionEvent event) {
        txtId.setText("");
        txtName.setText("");
        txtMail.setText("");
        txtContact.setText("");
    }


    public void txtIdOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();

        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT * FROM supplier WHERE supplier_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);
        try (ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                String sId = resultSet.getString(1);
                String name = resultSet.getString(2);
                String contact = resultSet.getString(3);
                String email = resultSet.getString(4);

                txtId.setText(sId);
                txtName.setText(name);
                txtMail.setText(email);
                txtContact.setText(contact);

                txtName.requestFocus();

            } else {
                txtName.setText("");
                txtContact.setText("");
                txtMail.setText("");
                txtName.requestFocus();
            }
        }
        txtName.requestFocus();
    }

    public void txtNameOnAction(ActionEvent event) {
        txtMail.requestFocus();
    }

    public void txtMailOnAction(ActionEvent event) {
        txtContact.requestFocus();
    }

    public void txtContactOnAction(ActionEvent event) {    }

    public void tbSuppSupOnMouseClicked(MouseEvent mouseEvent) {
        tbSuppSup.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection!=null){
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtMail.setText(newSelection.getEmail());
                txtContact.setText(newSelection.getContact());
            }
        });
    }

}
