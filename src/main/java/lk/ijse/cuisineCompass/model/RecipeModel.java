package lk.ijse.cuisineCompass.model;

import javafx.scene.control.Alert;
import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.dto.Recipe;
import lk.ijse.cuisineCompass.dto.RecipeIngredientDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RecipeModel {

    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<Recipe> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM recipe";

        List<Recipe> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Recipe(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }

    public static List<String> getRecipeApp() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> recipes = new ArrayList<>();

        //String sql = "SELECT description FROM recipe WHERE course = ?";
        String sql = "SELECT description, course FROM recipe";

        //String rC = "Appertizer";

        //PreparedStatement preparedStatement = con.prepareStatement(sql);
        //preparedStatement.setNString(1,"Appertizer");
        //preparedStatement.setString(1, rC);
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            if(resultSet.getString(2).equalsIgnoreCase("appertizer")){
                recipes.add(resultSet.getString(1));
            }
        }
        return recipes;
    }

    public static List<String> getRecipeSoup() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> recipes = new ArrayList<>();

        String sql = "SELECT description, course FROM recipe";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            if(resultSet.getString(2).equalsIgnoreCase("soup/salad")){
                recipes.add(resultSet.getString(1));
            }
        }
        return recipes;
    }

    public static List<String> getRecipeMain() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> recipes = new ArrayList<>();

        String sql = "SELECT description, course FROM recipe";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            if(resultSet.getString(2).equalsIgnoreCase("main course")){
                recipes.add(resultSet.getString(1));
            }
        }
        return recipes;
    }

    public static List<String> getRecipeDessert() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> recipes = new ArrayList<>();

        String sql = "SELECT description, course FROM recipe";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            if(resultSet.getString(2).equalsIgnoreCase("dessert")){
                recipes.add(resultSet.getString(1));
            }
        }
        return recipes;
    }

    public static List<String> getRecipeBev() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> recipes = new ArrayList<>();

        String sql = "SELECT description, course FROM recipe";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            if(resultSet.getString(2).equalsIgnoreCase("beverage")){
                recipes.add(resultSet.getString(1));
            }
        }
        return recipes;
    }

    public static String getAllRCode(String des) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT recipe_code FROM recipe WHERE description LIKE ?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, des);
        ResultSet resultSet = preparedStatement.executeQuery();
        String rCode = null;
        if(resultSet.next()){
            rCode = resultSet.getString(1);
        }
        return rCode;
    }

    public static List<RecipeIngredientDetails> getAllRecipeIng(String rCode) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT ingredient_code, unit, qty FROM recipe_ingredient_details WHERE recipe_code= ?";

        List<RecipeIngredientDetails> data = new ArrayList<>();

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, rCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.add(new RecipeIngredientDetails(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3)
                ));
            }
        return data;
    }

    public static String getMethod(String des) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT method FROM recipe WHERE description LIKE ?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, des);
        ResultSet resultSet = preparedStatement.executeQuery();
        String method = null;
        if(resultSet.next()){
            method = resultSet.getString(1);
        }
        return method;
    }

    public static boolean isSetIng(String rCode, String iCode, String unit, double qty) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO recipe_ingredient_details(recipe_code, ingredient_code, unit, qty) VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1,rCode);
        pstm.setString(2, iCode);
        pstm.setString(3, unit);
        pstm.setDouble(4, qty);

        int affectedRows = pstm.executeUpdate();

        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isSaved(String rCode, String des, String cCode, String method, String course, String userName) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "INSERT INTO recipe(recipe_code, description, category_code, method, course,user_name) VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, rCode);
        pstm.setString(2, des);
        pstm.setString(3, cCode);
        pstm.setString(4, method);
        pstm.setString(5, course);
        pstm.setString(6, userName);


        int affectedRows = pstm.executeUpdate();

        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isSaved1(String rCode, String iCode, String unit, double qty1) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql1 = "INSERT INTO recipe_ingredient_details(recipe_code, ingredient_code, unit, qty) VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql1);
        pstm.setString(1, rCode);
        pstm.setString(2, iCode);
        pstm.setString(3, unit);
        pstm.setDouble(4, qty1);

        int affectedRows1 = pstm.executeUpdate();
        if (affectedRows1 > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isUpdated(String rCode, String des, String cCode, String method, String course, String userName) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "UPDATE recipe SET description = ?, category_code = ?, method = ?, course = ? , user_name = ? WHERE recipe_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1, des);
        pstm.setString(2, cCode);
        pstm.setString(3, method);
        pstm.setString(4, course);
        pstm.setString(5, userName);
        pstm.setString(6, rCode);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isUpdated1(String rCode, String iCode, String unit, double qty1) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        //String sql = "UPDATE recipe_ingredient_details SET ingredient_code = ?, unit = ?, qty = ? WHERE recipe_code = ?";
        String sql = "UPDATE recipe_ingredient_details SET unit = ?, qty = ? WHERE recipe_code = ? AND ingredient_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, unit);
        pstm.setDouble(2, qty1);
        pstm.setString(3, rCode);
        pstm.setString(4, iCode);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isDeleted(String rCode) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM recipe WHERE recipe_code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, rCode);

            if (pstm.executeUpdate()> 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDeleted1(String rCode) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM recipe_ingredient_details WHERE recipe_code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, rCode);

            if (pstm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    public static ResultSet getImage(String id) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT image FROM image_recipe WHERE id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return resultSet;
        }
        return null;
    }


    /*public static ResultSet getLatestImage(String rCode) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT image FROM image_recipe WHERE id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, rCode);

        ResultSet resultSet = pstm.executeQuery();

        //if(resultSet.next()){
            return resultSet;
        //}
        //return null;
    }*/

    public static String getLatestR(String rCode) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT description FROM recipe WHERE recipe_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, rCode);


        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            String des = resultSet.getString(1);
            return des;
        }
        return null;
    }

    public static String getLatest() throws SQLException {
        //Connection con = DBConnection.getInstance().getConnection();

        //List<String> recipe = new ArrayList<>();

        //String sql = "SELECT description FROM recipe ORDER BY recipe_code DESC LIMIT 1;";

        //String des = con.createStatement().executeQuery(sql);
        //return des;

        Connection con1 = DriverManager.getConnection(URL, props);
        String sql1 = "SELECT recipe_code FROM recipe ORDER BY recipe_code DESC LIMIT 1";

        PreparedStatement pstm1 = con1.prepareStatement(sql1);
        //pstm1.setString(1, id);

        ResultSet resultSet1 = pstm1.executeQuery();

        if (resultSet1.next()) {
            String id = resultSet1.getString(1);
            return id;
        }else{
            new Alert(Alert.AlertType.ERROR, "Employee doesn't exist!").show();
        }
        return null;
    }

}

