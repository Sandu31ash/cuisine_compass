package lk.ijse.cuisineCompass.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cuisineCompass.dto.Ingredient;
import lk.ijse.cuisineCompass.dto.Inventory;
import lk.ijse.cuisineCompass.dto.tm.IngredientTM;
import lk.ijse.cuisineCompass.dto.tm.InventoryTM;
import lk.ijse.cuisineCompass.model.IngredientModel;
import lk.ijse.cuisineCompass.model.InventoryModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class IngredientViewFormController implements Initializable {
    @FXML
    private Button btnBackAdIng;

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
    private TableView<IngredientTM> tbIngAd;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
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
            tbIngAd.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }


    public void btnBackAdIngOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/admin_dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.centerOnScreen();
    }
}
