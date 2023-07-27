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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.dto.Category;
import lk.ijse.cuisineCompass.dto.Menu;
import lk.ijse.cuisineCompass.dto.tm.CategoryTM;
import lk.ijse.cuisineCompass.dto.tm.MenuTM;
import lk.ijse.cuisineCompass.model.CategoryModel;
import lk.ijse.cuisineCompass.model.EmployeeModel;
import lk.ijse.cuisineCompass.model.MenuModel;
import lk.ijse.cuisineCompass.model.RecipeModel;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MenuFormController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }
    @FXML
    private Button btnBackAdMenu;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colCatCode;

    @FXML
    private TableColumn<?, ?> colCatCodeMenu;

    @FXML
    private TableColumn<?, ?> colCatDes;

    @FXML
    private TableColumn<?, ?> colDish;

    @FXML
    private TableColumn<?, ?> colMeal;

    @FXML
    private TableColumn<?, ?> colMenuCode;

    @FXML
    private TableColumn<?, ?> colMenuDes;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<CategoryTM> tbCategory;

    @FXML
    private TableView<MenuTM> tbMenu;

    @FXML
    private TextField txtDish;

    @FXML
    private JFXComboBox<String> cBoxMeal;

    @FXML
    private TextField txtMenuCode;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtAddingby;

    @FXML
    private ImageView pic;

    @FXML
    private JFXComboBox<String> cBoxCourse;

    @FXML
    private JFXComboBox<String> cBoxCate;

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        txtAddingby.setText(LoginFormController.user);
        setCellValueFactoryCate();
        loadMeals();
        loadCourses();
        loadCate();
        getAllCate();
        setCellValueFactoryMenu();
        getAllMenu();
    }

    void setCellValueFactoryCate() {
        colCatCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCatDes.setCellValueFactory(new PropertyValueFactory<>("des"));
    }

    private void loadMeals() {
        List<String> jobs = new ArrayList<>();

        jobs.add(0, "Breakfast");
        jobs.add(1, "Lunch");
        jobs.add(2, "Dinner");

        ObservableList<String> obList = FXCollections.observableArrayList();
        for (String code : jobs) {
            obList.add(code);
        }
        cBoxMeal.setItems(obList);
    }

    private void loadCourses() {
        List<String> courses = new ArrayList<>();

        courses.add(0, "Appertizer");
        courses.add(1, "Soup/Salad");
        courses.add(2, "Main Course");
        courses.add(3, "Dessert");
        courses.add(4, "Beverage");

        ObservableList<String> obList = FXCollections.observableArrayList();
        for (String course : courses) {
            obList.add(course);
        }
        cBoxCourse.setItems(obList);
    }

    private void loadCate() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> codes = CategoryModel.getCate();

        for (String code : codes) {
            obList.add(code);
        }
        cBoxCate.setItems(obList);
    }

    void getAllCate() {
        try {
            ObservableList<CategoryTM> obList = FXCollections.observableArrayList();
            List<Category> catList = CategoryModel.getAll();

            for(Category category : catList) {
                obList.add(new CategoryTM(
                        category.getCode(),
                        category.getType(),
                        category.getDes()
                ));
            }
            tbCategory.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    void setCellValueFactoryMenu() {
        colMenuCode.setCellValueFactory(new PropertyValueFactory<>("mCode"));
        colDish.setCellValueFactory(new PropertyValueFactory<>("dish"));
        colMenuDes.setCellValueFactory(new PropertyValueFactory<>("des"));
        colMeal.setCellValueFactory(new PropertyValueFactory<>("meal"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCatCodeMenu.setCellValueFactory(new PropertyValueFactory<>("cCode"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
    }

    void getAllMenu() {
        try {
            ObservableList<MenuTM> obList = FXCollections.observableArrayList();
            List<Menu> menuList = MenuModel.getAll();

            for(Menu menu : menuList) {
                obList.add(new MenuTM(
                        menu.getMCode(),
                        menu.getDish(),
                        menu.getDes(),
                        menu.getMeal(),
                        menu.getPrice(),
                        menu.getCCode(),
                        menu.getUserName()
                ));
            }
            tbMenu.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    private void getImage(String mCode) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        ResultSet resultSet = null;
        resultSet = MenuModel.getImage(mCode);
        Image image = null;
        image = new Image(resultSet.getBinaryStream("image"));
        if(resultSet==null){
            pic.setImage(null);
        }else {
            pic.setImage(image);
            pic.setPreserveRatio(false);
        }
    }

    public void btnBackAdMenuOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/cuisineCompass/view/admin_dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.centerOnScreen();
    }

    public void btnSaveOnAction(ActionEvent event) {
        String mCode = txtMenuCode.getText();
        String dish = txtDish.getText();
        String des = cBoxCourse.getValue();
        String meal = cBoxMeal.getValue();
        String sPrice = txtPrice.getText();
        double price = Double.parseDouble(sPrice);
        String cCode = cBoxCate.getValue();
        String userName = txtAddingby.getText();

        if (txtMenuCode.getText().matches("^M\\d{3}$")) {
            try {
                boolean isSaved = MenuModel.isSaved(mCode, dish, des, meal, price, cCode, userName);
                if (isSaved) {
                    getAllMenu();
                    new Alert(Alert.AlertType.CONFIRMATION, "Menu added!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error! Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid menu code! Try Again").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String mCode = txtMenuCode.getText();
        String dish = txtDish.getText();
        String des = cBoxCourse.getValue();
        String meal = cBoxMeal.getValue();
        String sPrice = txtPrice.getText();
        double price = Double.parseDouble(sPrice);
        String cCode = cBoxCate.getValue();
        String userName = txtAddingby.getText();

        try {
            boolean isUpdated = MenuModel.isUpdated(mCode, dish, des, meal, price, cCode, userName);
            if (isUpdated) {
                getAllMenu();
                new Alert(Alert.AlertType.CONFIRMATION, "Menu details successfully updated!!").show();
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

            boolean isDeleted = MenuModel.isDeleted(txtMenuCode.getText());

            if (isDeleted) {
                getAllMenu();
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }
        }
    }

    public void btnClearOnAction(ActionEvent event) {
        txtMenuCode.setText("");
        txtDish.setText("");
        cBoxCourse.setValue("");
        cBoxMeal.setValue("");
        txtPrice.setText("");
        cBoxCate.setValue("");
        txtAddingby.setText("");
    }



    public void txtMenuCodeOnAction(ActionEvent event) throws SQLException {

        String mCode = txtMenuCode.getText();

        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT * FROM menu WHERE menu_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, mCode);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String code = resultSet.getString(1);
            String dish = resultSet.getString(2);
            String des = resultSet.getString(3);
            String meal = resultSet.getString(4);
            String price = resultSet.getString(5);
            String cCode = resultSet.getString(6);
            String userName = resultSet.getString(7);

            txtMenuCode.setText(code);
            txtDish.setText(dish);
            cBoxCourse.setValue(des);
            cBoxMeal.setValue(meal);
            txtPrice.setText(price);
            cBoxCate.setValue(cCode);
            txtAddingby.setText(userName);
            pic.setImage(null);
            getImage(mCode);

            txtDish.requestFocus();

        } else {
            txtDish.setText("");
            cBoxCourse.setValue("");
            cBoxMeal.setValue("");
            txtPrice.setText("");
            cBoxCate.setValue("");
            //txtAddingby.setText("");
            txtDish.requestFocus();
            pic.setImage(null);
        }

        txtDish.requestFocus();
    }

    public void txtDishOnAction(ActionEvent event) {
        cBoxMeal.requestFocus();
    }

    public void cBoxMealOnAction(ActionEvent event) {
        txtPrice.requestFocus();
    }

    public void txtPriceOnAction(ActionEvent event) {
        cBoxCate.requestFocus();
    }

    public void cBoxCateOnAction(ActionEvent event) {
        txtAddingby.requestFocus();
    }

    public void txtAddingbyOnAction(ActionEvent event) {
        cBoxCourse.requestFocus();
    }

    public void tbMenuOnMouseClicked(MouseEvent mouseEvent) {
        tbMenu.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection!=null){
                txtMenuCode.setText(newSelection.getMCode());
                txtDish.setText(newSelection.getDish());
                cBoxCourse.setValue(newSelection.getDes());
                cBoxMeal.setValue(String.valueOf(newSelection.getMeal()));
                txtPrice.setText(String.valueOf(newSelection.getPrice()));
                cBoxCate.setValue(newSelection.getCCode());
                txtAddingby.setText(newSelection.getUserName());
                pic.setImage(null);
            }
        });
    }

    public void btnPicOnAction(ActionEvent event) {
        String id = txtMenuCode.getText();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                FileInputStream fis = new FileInputStream(file);

                String sql = "INSERT INTO image_menu (image, id) VALUES (?, ?)";
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
                pstm.setBinaryStream(1, fis, fis.available());
                pstm.setString(2, id);
                int x = pstm.executeUpdate();

                if (x > 0) {
                    ResultSet resultSet = RecipeModel.getImage(id);
                    if (resultSet.next()) {
                        Image image = new Image(resultSet.getBinaryStream("image"));
                        pic.setImage(image);
                    }
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
