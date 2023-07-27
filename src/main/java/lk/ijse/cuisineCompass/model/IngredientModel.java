package lk.ijse.cuisineCompass.model;

import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.dto.Category;
import lk.ijse.cuisineCompass.dto.Ingredient;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class IngredientModel {

    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    /*public static List<Ingredient> getAllIng() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT ingredient_code, description FROM ingredient";

        List<Ingredient> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Ingredient(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }*/

    public static List<String> getIng() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT ingredient_code FROM ingredient";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static List<Ingredient> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT ingredient_code, description, unit, qty, date FROM ingredient";

        List<Ingredient> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Ingredient(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    /*public static List<String> getCodes() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT code FROM Item";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }*/

    public static boolean isSaved(String iCode, String des, String unit, double qty, Date date) throws SQLException {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean inventoryUpdated = InventoryModel.update(iCode,qty);
            if (inventoryUpdated) {
                    boolean isSaved = save(iCode, des, unit,qty,date);
                    if (isSaved) {
                        con.commit();
                        return true;
                    }
                }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }

    public static boolean save(String iCode, String des, String unit, double qty, Date date) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "INSERT INTO ingredient(ingredient_code, description, unit, qty, date) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, iCode);
        pstm.setString(2, des);
        pstm.setString(3, unit);
        pstm.setDouble(4, qty);
        pstm.setDate(5, date);

        int affectedRows = pstm.executeUpdate();
        if(affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean update(String iCode, String des, String unit, double qty, Date date) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "UPDATE ingredient SET description = ?, unit = ?, qty = ?, date = ? WHERE ingredient_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, des);
        pstm.setString(2, unit);
        pstm.setDouble(3, qty);
        pstm.setDate(4, date);
        pstm.setString(5, iCode);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isDeleted(String iCode) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM ingredient WHERE ingredient_code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, iCode);

            if (pstm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUpdated(String iCode, String des, String unit, double qty, Date date) throws SQLException {
        /*Connection con = DriverManager.getConnection(URL, props);
        String sql = "UPDATE ingredient SET description = ?, unit = ?, qty = ?, date = ? WHERE ingredient_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, des);
        pstm.setString(2, unit);
        pstm.setDouble(3, qty);
        pstm.setDate(4, date);
        pstm.setString(5, iCode);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;*/
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean inventoryUpdated = InventoryModel.update(iCode,qty);
            if (inventoryUpdated) {
                boolean isSaved = update(iCode, des, unit,qty,date);
                if (isSaved) {
                    con.commit();
                    return true;
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }
}
