package lk.ijse.cuisineCompass.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cuisineCompass.dto.RecipeIngredientDetails;
import lk.ijse.cuisineCompass.dto.tm.RecipeIngredientDetailsTM;
import lk.ijse.cuisineCompass.model.RecipeModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class SoupFormController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXComboBox<String> cBoxRecipe;

    @FXML
    private TableView<RecipeIngredientDetailsTM> tbRecipe;

    @FXML
    private TextArea txtMethod;

    @FXML
    private TableColumn<?, ?> colIng;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnit;

    @FXML
    private ImageView pic;

    @SneakyThrows
    public void initialize(java.net.URL url, ResourceBundle resourceBundle){
        loadRecipes();
    }

    void setCellValueFactory() {
        colIng.setCellValueFactory(new PropertyValueFactory<>("ingCode"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void loadRecipes() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> recipes = RecipeModel.getRecipeSoup();

        for (String recipe : recipes) {
            obList.add(recipe);
        }
        cBoxRecipe.setItems(obList);
    }

    void getAllRCode() {
        try {
            String rCode = RecipeModel.getAllRCode(cBoxRecipe.getValue());
            getAll(rCode);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    void getAll(String rCode) {
        try {
            ObservableList<RecipeIngredientDetailsTM> obList = FXCollections.observableArrayList();
            //List<RecipeIngredientDetails> recipeList = RecipeModel.getAllRecipeIng(String.valueOf(RecipeModel.getAllRCode(String.valueOf(RecipeModel.getRecipe()))));
            List<RecipeIngredientDetails> recipeList = RecipeModel.getAllRecipeIng(rCode);

            for(RecipeIngredientDetails recipe : recipeList) {
                obList.add(new RecipeIngredientDetailsTM(
                        recipe.getIngCode(),
                        recipe.getUnit(),
                        recipe.getQty()
                ));
            }
            tbRecipe.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    void getMethod() throws SQLException {
        String method = RecipeModel.getMethod(cBoxRecipe.getValue());
        txtMethod.setText(method);
    }

    private void getImage(String rCode) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        ResultSet resultSet = null;
        resultSet = RecipeModel.getImage(rCode);
        Image image = null;
        image = new Image(resultSet.getBinaryStream("image"));
        pic.setImage(image);
        pic.setPreserveRatio(false);
    }

    public void btnBackSoupOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/recipe_view_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Recipe");
        stage.centerOnScreen();
    }

    @SneakyThrows
    public void cBoxRecipeOnAction(ActionEvent event) {
        getAllRCode();
        //getAll();
        getMethod();
        setCellValueFactory();
        getImage(RecipeModel.getAllRCode(cBoxRecipe.getValue()));
    }
}
