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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.cuisineCompass.dto.Employee;
import lk.ijse.cuisineCompass.dto.Order;
import lk.ijse.cuisineCompass.dto.OrderDetails;
import lk.ijse.cuisineCompass.dto.Supplier;
import lk.ijse.cuisineCompass.dto.tm.EmployeeTM;
import lk.ijse.cuisineCompass.dto.tm.OrderDetailsTM;
import lk.ijse.cuisineCompass.dto.tm.OrderTM;
import lk.ijse.cuisineCompass.model.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Double.parseDouble;

public class OrderFormController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private Button btnBackSupOrder;

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
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colOrderedBy;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<OrderTM> tbOrder;

    @FXML
    private TextField txtCode;

    @FXML
    private DatePicker datePick;

    @FXML
    private JFXComboBox<String> cBoxS;

    @FXML
    private TextField txtOrderBy;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtTot;

    @FXML
    private TableColumn<?, ?> colInv;

    @FXML
    private TableColumn<?, ?> colOCode;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTot;

    @FXML
    private TableView<OrderDetailsTM> tbOrderDetails;

    @FXML
    private Label lbl;

    @FXML
    private Button btnAdd;

    @FXML
    private JFXComboBox<String> cBoxInvCode;

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        txtOrderBy.setText(LoginFormController.user);
        setCellValueFactory();
        setCellValueFactory1();
        getAll1();
        getAllDetails1();
        loadSId();
        loadInv();
    }
    void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderedBy.setCellValueFactory(new PropertyValueFactory<>("orderBy"));
        //colOCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        //colInv.setCellValueFactory(new PropertyValueFactory<>("iCode"));
        //colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        //colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        //colTot.setCellValueFactory(new PropertyValueFactory<>("tot"));
    }

    void setCellValueFactory1() {
        colOCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colInv.setCellValueFactory(new PropertyValueFactory<>("iCode"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTot.setCellValueFactory(new PropertyValueFactory<>("tot"));
    }

    private void loadSId() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> codes = SupplierModel.getAllSup();

        for (String code : codes) {
            obList.add(code);
        }
        cBoxS.setItems(obList);
    }

    private void loadInv() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> codes = InventoryModel.getInv();

        for (String code : codes) {
            obList.add(code);
        }
        cBoxInvCode.setItems(obList);
    }

    void getAll1() {
        try {
            ObservableList<OrderTM> obList = FXCollections.observableArrayList();
            List<Order> orderList = OrderModel.getAll1();

            for(Order order : orderList) {
                obList.add(new OrderTM(
                        order.getCode(),
                        order.getId(),
                        order.getDate(),
                        order.getOrderBy()
                ));
            }
            tbOrder.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    void getAllDetails1() {
        try {
            ObservableList<OrderDetailsTM> obList = FXCollections.observableArrayList();
            List<OrderDetails> orderList = OrderModel.getAllDetails1();

            for(OrderDetails orderDetails : orderList) {
                obList.add(new OrderDetailsTM(
                        orderDetails.getCode(),
                        orderDetails.getICode(),
                        orderDetails.getPrice(),
                        orderDetails.getQty(),
                        orderDetails.getTot()
                ));
            }
            tbOrderDetails.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnBackSupOrderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/sup_dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Supervisor Dashboard");
        stage.centerOnScreen();
    }

    public void btnClearOnAction(ActionEvent event) {
        txtCode.setText("");
        cBoxS.setValue(null);
        datePick.setValue(null);
        txtCode.requestFocus();

        cBoxInvCode.setValue("");
        txtPrice.setText("");
        txtQty.setText("");
        txtTot.setText("");
    }

    public void btnDeleteOnAction(ActionEvent event) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete? :)", yes,no).showAndWait();
        if(result.orElse(no)==yes) {

            boolean isDeleted = OrderModel.isDeleted(txtCode.getText());
            boolean isDeleted1 = OrderModel.isDeleted1(txtCode.getText());
            if (isDeleted || isDeleted1) {
                getAll1();
                getAllDetails1();
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String oCode = txtCode.getText();
        String id = String.valueOf(cBoxS.getValue());
        Date date = Date.valueOf(datePick.getValue());
        String orderBy = txtOrderBy.getText();

        String iCode = cBoxInvCode.getValue();
        double price = parseDouble(txtPrice.getText());
        double qty = parseDouble(txtQty.getText());
        double total = (price*qty);
        txtTot.setText(String.valueOf(total));
        double tot = parseDouble(txtTot.getText());

        try {
            boolean isUpdated = OrderModel.isUpdated(oCode, id, date, orderBy);
            boolean isUpdated1 = OrderModel.isUpdated1(oCode, iCode, price, qty, tot);
            if (isUpdated || isUpdated1) {
                getAll1();
                getAllDetails1();
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory successfully updated!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error! Try Again").show();
        }
    }

    public void btnSaveOnAction(ActionEvent event) {
        String oCode = txtCode.getText();
        String id = String.valueOf(cBoxS.getValue());
        Date date = Date.valueOf(datePick.getValue());
        String orderBy = txtOrderBy.getText();

        //String code = txtCode.getText();
        String iCode = cBoxInvCode.getValue();
        double price= parseDouble(txtPrice.getText());
        double qty = parseDouble(txtQty.getText());
        double total = (price*qty);
        txtTot.setText(String.valueOf(total));
        double tot= parseDouble(txtTot.getText());

        if (txtCode.getText().matches("^O\\d{3}$")) {
            try {
                boolean isSaved = OrderModel.isSaved(oCode, id, date, orderBy);
                boolean isSaved1 = OrderModel.isSaved1(oCode, iCode, price, qty, tot);
                if (isSaved || isSaved1) {
                    getAll1();
                    getAllDetails1();
                    new Alert(Alert.AlertType.CONFIRMATION, "Order successfully saved!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error! Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid order code! Try Again").show();
        }
    }


    public void txtCodeOnAction(ActionEvent event) throws SQLException {
        String oCode = txtCode.getText();

        getAllOrderDetails(oCode);

        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT * FROM ordr WHERE order_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oCode);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String code = resultSet.getString(1);
            String id = resultSet.getString(2);
            String date = resultSet.getString(3);
            String orderBy = resultSet.getString(4);

            /*String iCode = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            double qty = resultSet.getDouble(4);
            double tot = resultSet.getDouble(5);*/

            txtCode.setText(code);
            cBoxS.setValue(id);
            datePick.setValue(LocalDate.parse(date));
            txtOrderBy.setText(orderBy);

            /*txtInvCode.setText(iCode);
            txtPrice.setText(String.valueOf(price));
            txtQty.setText(String.valueOf(qty));
            txtTot.setText(String.valueOf(tot));*/

            cBoxS.requestFocus();

        } else {
            cBoxS.setValue(null);
            datePick.setValue(null);

            cBoxInvCode.setValue("");
            txtPrice.setText("");
            txtQty.setText("");
            txtTot.setText("");
        }
        datePick.requestFocus();
    }

    void getAllOrderDetails(String oCode) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT * FROM order_details WHERE order_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oCode);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String code = resultSet.getString(1);
            String iCode = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            double qty = resultSet.getDouble(4);
            double tot = resultSet.getDouble(5);

            txtCode.setText(code);
            cBoxInvCode.setValue(iCode);
            txtPrice.setText(String.valueOf(price));
            txtQty.setText(String.valueOf(qty));
            txtTot.setText(String.valueOf(tot));

        } else {
            cBoxInvCode.setValue("");
            txtPrice.setText("");
            txtQty.setText("");
            txtTot.setText("");
        }
    }

    public void datePickOnAction(ActionEvent event) {
        cBoxS.requestFocus();
    }

    public void cBoxSOnAction(ActionEvent event) {
        txtOrderBy.requestFocus();
    }

    public void txtOrderByAction(ActionEvent event) {
        cBoxInvCode.requestFocus();
    }

    public void cBoxInvCodeOnAction(ActionEvent event) {
        txtPrice.requestFocus();
    }

    public void txtPriceOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    public void txtQtyOnAction(ActionEvent event) {
        double price = parseDouble(txtPrice.getText());
        double qty = parseDouble(txtQty.getText());
        double total = (price*qty);
        txtTot.setText(String.valueOf(total));
        txtTot.requestFocus();
    }

    public void tbOrderOnMouseClicked(MouseEvent mouseEvent) {
        tbOrder.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection!=null){
                txtCode.setText(newSelection.getCode());
                cBoxS.setValue(newSelection.getId());
                datePick.setValue(LocalDate.parse(newSelection.getDate()));
                txtOrderBy.setText(newSelection.getOrderBy());
            }
        });
    }

    public void tbOrderDetailsOnMouseClicked(MouseEvent mouseEvent) {
        tbOrderDetails.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection!=null){
                txtCode.setText(newSelection.getCode());
                cBoxInvCode.setValue(newSelection.getICode());
                txtPrice.setText(String.valueOf(newSelection.getPrice()));
                txtQty.setText(String.valueOf(newSelection.getQty()));
                txtTot.setText(String.valueOf(newSelection.getTot()));
            }
        });
    }

    public void btnAddOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String iCode = cBoxInvCode.getValue();
        double price= parseDouble(txtPrice.getText());
        double qty = parseDouble(txtQty.getText());
        double tot= parseDouble(txtTot.getText());

            try {
                boolean isSet = OrderModel.isSet(code, iCode, price, qty, tot);
                if (isSet) {
                    getAllDetails1();
                    new Alert(Alert.AlertType.CONFIRMATION, "Inventory successfully added!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error! Try Again").show();
            }
    }

    public void btnAddOnMouseEntered(MouseEvent mouseEvent) {
        lbl.setVisible(true);
    }

    public void btnAddOnMouseExited(MouseEvent mouseEvent) {
        lbl.setVisible(false);
    }

    public void btnInvOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/inventory_view_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage oldStage = (Stage) pane.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Inventory");
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initOwner(oldStage);
        newStage.setWidth(500);
        newStage.setHeight(600);
        newStage.initStyle(StageStyle.UTILITY);
        anchorPane.setDisable(true);
        newStage.show();
    }

    public void btnSupOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/supplier_view_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage oldStage = (Stage) pane.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Supplier");
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initOwner(oldStage);
        newStage.setWidth(500);
        newStage.setHeight(600);
        newStage.initStyle(StageStyle.UTILITY);
        anchorPane.setDisable(true);
        newStage.show();
    }
}
