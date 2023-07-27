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
import lk.ijse.cuisineCompass.dto.Order;
import lk.ijse.cuisineCompass.dto.Supplier;
import lk.ijse.cuisineCompass.dto.tm.OrderTM;
import lk.ijse.cuisineCompass.dto.tm.SupplierTM;
import lk.ijse.cuisineCompass.model.InventoryModel;
import lk.ijse.cuisineCompass.model.OrderModel;
import lk.ijse.cuisineCompass.model.SupplierModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderViewFormController implements Initializable {
    @FXML
    private Button btnBackAdOrder;

    @FXML
    private JFXComboBox<String> cBoxCode;

    @FXML
    private TableColumn<?, ?> colInv;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTot;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<OrderTM> tbOrderAd;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtOrderedBy;

    @FXML
    private TextField txtSupp;

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        //setCellValueFactory();
        //getAll();
        loadCodes();
    }
    void setCellValueFactory() {
        colInv.setCellValueFactory(new PropertyValueFactory<>("iCode"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTot.setCellValueFactory(new PropertyValueFactory<>("tot"));
    }

    private void loadCodes() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> codes = OrderModel.getCodes();

        for (String code : codes) {
            obList.add(code);
        }
        cBoxCode.setItems(obList);
    }

    void getAll(String oCode) {
        Order order=null;
        try {
            System.out.println("tfyd");
            order = OrderModel.getAll(oCode);
            System.out.println("fgfdgsgs");
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error mmm!").show();
        }
        txtDate.setText(order.getDate());
        txtOrderedBy.setText(order.getOrderBy());
        txtSupp.setText(order.getSupplier());
    }

    void getAllDetails(String oCode) {
        try {
            ObservableList<OrderTM> obList = FXCollections.observableArrayList();
            List<Order> orderList = OrderModel.getAllDetails(oCode);

            for(Order order : orderList) {
                obList.add(new OrderTM(
                        order.getICode(),
                        order.getPrice(),
                        order.getQty(),
                        order.getTot()
                ));
            }
            tbOrderAd.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error nnnn!").show();
        }
    }

    public void btnBackAdOrderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/admin_dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.centerOnScreen();
    }

    public void cBoxCodeOnAction(ActionEvent event) {
        String oCode = cBoxCode.getValue();
        setCellValueFactory();
        getAll(oCode);
        getAllDetails(oCode);
    }
}
