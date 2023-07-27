package lk.ijse.cuisineCompass.model;

import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.dto.Employee;
import lk.ijse.cuisineCompass.dto.Menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MenuModel {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<Menu> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM menu";

        List<Menu> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Menu(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }

    public static List<Menu> getAllApp(String meal) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM menu WHERE description LIKE 'Appertizer'";

        List<Menu> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            if(resultSet.getString(4).equalsIgnoreCase(meal)){
            data.add(new Menu(
                    resultSet.getString(2),
                    resultSet.getDouble(5)
            ));
        }
        }
        return data;
    }

    public static List<Menu> getAllSoup(String meal) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM menu WHERE description LIKE 'soup/salad'";

        List<Menu> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            if(resultSet.getString(4).equalsIgnoreCase(meal)){
                data.add(new Menu(
                        resultSet.getString(2),
                        resultSet.getDouble(5)
                ));
            }
        }
        return data;
    }

    public static List<Menu> getAllMain(String meal) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM menu WHERE description LIKE 'main course'";

        List<Menu> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            if(resultSet.getString(4).equalsIgnoreCase(meal)) {
                data.add(new Menu(
                        resultSet.getString(2),
                        resultSet.getDouble(5)
                ));
            }
        }
        return data;
    }

    public static List<Menu> getAllDessert(String meal) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM menu WHERE description LIKE 'dessert'";

        List<Menu> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            if(resultSet.getString(4).equalsIgnoreCase(meal)) {
                data.add(new Menu(
                        resultSet.getString(2),
                        resultSet.getDouble(5)
                ));
            }
        }
        return data;
    }

    public static List<Menu> getAllBev(String meal) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM menu WHERE description LIKE 'beverage'";

        List<Menu> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            if(resultSet.getString(4).equalsIgnoreCase(meal)) {
                data.add(new Menu(
                        resultSet.getString(2),
                        resultSet.getDouble(5)
                ));
            }
        }
        return data;
    }

    public static ResultSet getImage(String mCode) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT image FROM image_menu WHERE id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, mCode);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return resultSet;
        }
        return null;
    }

    public static boolean isSaved(String mCode, String dish, String des, String meal, double price, String cCode, String userName) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "INSERT INTO menu(menu_code, dish, description, meal, price, category_code, user_name) VALUES(?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, mCode);
        pstm.setString(2, dish);
        pstm.setString(3, des);
        pstm.setString(4, meal);
        pstm.setDouble(5, price);
        pstm.setString(6, cCode);
        pstm.setString(7, userName);

        int affectedRows = pstm.executeUpdate();
        if(affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isUpdated(String mCode, String dish, String des, String meal, double price, String cCode, String userName) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "UPDATE menu SET dish = ?, description = ?, meal = ?, price = ? , category_code = ?, user_name = ? WHERE menu_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, dish);
        pstm.setString(2, des);
        pstm.setString(3, meal);
        pstm.setDouble(4, price);
        pstm.setString(5, cCode);
        pstm.setString(6, userName);
        pstm.setString(7, mCode);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isDeleted(String mCode) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM menu WHERE menu_code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, mCode);

            if (pstm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

}
