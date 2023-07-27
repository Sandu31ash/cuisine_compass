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
import lk.ijse.cuisineCompass.dto.Ingredient;
import lk.ijse.cuisineCompass.dto.tm.EmployeeTM;
import lk.ijse.cuisineCompass.dto.tm.IngredientTM;
import lk.ijse.cuisineCompass.model.IngredientModel;
import lk.ijse.cuisineCompass.model.InventoryModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class IngredientFormController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private Button btnBackIngSup;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnit;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<IngredientTM> tbIngSup;

    @FXML
    private TextArea txtDes;

    @FXML
    private JFXComboBox<String> cBoxIng;

    @FXML
    private TextField txtQty;

    @FXML
    private DatePicker datePick;

    @FXML
    private TextField txtUnit;

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        loadIng();
    }

    void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("ingCode"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("des"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    void getAll() {
        try {
            ObservableList<IngredientTM> obList = FXCollections.observableArrayList();
            List<Ingredient> ingList = IngredientModel.getAll();

            for(Ingredient ing : ingList) {
                obList.add(new IngredientTM(
                        ing.getIngCode(),
                        ing.getDes(),
                        ing.getUnit(),
                        ing.getQty(),
                        ing.getDate()
                ));
            }
            tbIngSup.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    private void loadIng() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> codes = InventoryModel.getInv();

        for (String code : codes) {
            obList.add(code);
        }
        cBoxIng.setItems(obList);
    }

    public void btnBackIngSupOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/sup_dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Supervisor Dashboard");
        stage.centerOnScreen();
    }

    public void btnClearOnAction(ActionEvent event) {
        cBoxIng.setValue("");
        txtDes.setText("");
        txtUnit.setText("");
        txtQty.setText("");
        //datePick.setValue(LocalDate.parse(null));
        datePick.setValue(null);
    }

    public void btnDeleteOnAction(ActionEvent event) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete? :)", yes,no).showAndWait();
        if(result.orElse(no)==yes) {

            boolean isDeleted = IngredientModel.isDeleted(cBoxIng.getValue());

            if (isDeleted) {
                getAll();
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String iCode = cBoxIng.getValue();
        String des = txtDes.getText();
        String unit = txtUnit.getText();
        String sQty = txtQty.getText();
        double qty = Double.parseDouble(sQty);
        Date date = Date.valueOf(datePick.getValue());
        try {
            boolean isUpdated = IngredientModel.isUpdated(iCode, des, unit, qty, date);
            if (isUpdated) {
                getAll();
                new Alert(Alert.AlertType.CONFIRMATION, "Ingredient details successfully updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error! Try Again").show();
        }
    }

    public void btnSaveOnAction(ActionEvent event) {
        String iCode = cBoxIng.getValue();
        String des = txtDes.getText();
        String unit = txtUnit.getText();
        String sQty = txtQty.getText();
        double qty = Double.parseDouble(sQty);
        //Date date = Date.valueOf(datePick.getValue());
        Date date = Date.valueOf(datePick.getValue());
        try {
            boolean isSaved = IngredientModel.isSaved(iCode, des, unit, qty, date);
            if (isSaved) {
                getAll();
                new Alert(Alert.AlertType.CONFIRMATION, "Ingredient added!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error! Try Again").show();
        }
    }

    public void cBoxIngOnAction(ActionEvent event) throws SQLException {

        String iCode = cBoxIng.getValue();

        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT * FROM ingredient WHERE ingredient_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, iCode);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String code = resultSet.getString(1);
            String des = resultSet.getString(2);
            String unit = resultSet.getString(3);
            String qty = resultSet.getString(4);
            String date = resultSet.getString(5);
            //Date date = Date.valueOf(datePick.getValue());

            cBoxIng.setValue(code);
            txtDes.setText(des);
            txtUnit.setText(unit);
            txtQty.setText(qty);
            datePick.setValue(LocalDate.parse(date));

            //valueOf(datePick.setValue(date));

            txtDes.requestFocus();

        } else {

            //String iCode = cBoxIng.getValue();

            Connection con1 = DriverManager.getConnection(URL, props);
            String sql1 = "SELECT * FROM inventory WHERE inventory_code = ?";

            PreparedStatement pstm1 = con1.prepareStatement(sql1);
            pstm1.setString(1, iCode);

            ResultSet resultSet1 = pstm1.executeQuery();

            if (resultSet1.next()) {
                String ing = resultSet1.getString(1);
                cBoxIng.setValue(ing);
                String des = resultSet1.getString(2);
                txtDes.setText(des);
                String unit = resultSet1.getString(3);
                txtUnit.setText(unit);

                txtQty.setText("");
                datePick.setValue(null);

                txtQty.requestFocus();

            }else{
                txtDes.requestFocus();
                txtDes.setText("");
                txtQty.setText("");
                txtQty.setText("");
                datePick.setValue(null);
            }

        }
    }

    public void txtDesOnAction(MouseEvent mouseEvent) {
        txtUnit.requestFocus();
    }

    public void txtUnitOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    public void txtQtyOnAction(ActionEvent event) {
        datePick.requestFocus();
    }

    public void tbIngSupOnMouseClicked(MouseEvent mouseEvent) {
        tbIngSup.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection!=null){
                cBoxIng.setValue(newSelection.getIngCode());
                txtDes.setText(newSelection.getDes());
                txtUnit.setText(newSelection.getUnit());
                txtQty.setText(String.valueOf(newSelection.getQty()));
                datePick.setValue(LocalDate.parse(newSelection.getDate()));
            }
        });
    }

}
