package lk.ijse.cuisineCompass.model;

import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.dto.Inventory;
import lk.ijse.cuisineCompass.dto.Menu;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InventoryModel {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<Inventory> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM inventory";

        List<Inventory> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Inventory(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }

    public static List<String> getInv() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT inventory_code FROM inventory";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    /*public static List<String> getInv1() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT description FROM inventory";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }*/

    public static boolean isSaved(String iCode, String des, String unit, double par, double qty, Date date) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "INSERT INTO inventory(inventory_code, description, unit, PAR_level, qtyOnHand, date) VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, iCode);
        pstm.setString(2, des);
        pstm.setString(3, unit);
        pstm.setDouble(4, par);
        pstm.setDouble(5, qty);
        pstm.setDate(6, date);

        int affectedRows = pstm.executeUpdate();
        if(affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isUpdated(String iCode, String des, String unit, double par, double qty, Date date) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "UPDATE inventory SET description = ?, unit = ?, PAR_level = ? , qtyOnHand = ?, date = ? WHERE inventory_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, des);
        pstm.setString(2, unit);
        pstm.setDouble(3, par);
        pstm.setDouble(4, qty);
        pstm.setDate(5, date);
        pstm.setString(6, iCode);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isDeleted(String iCode) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM inventory WHERE inventory_code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, iCode);

            if (pstm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean update(String iCode, double qty) throws SQLException {

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE Inventory SET qtyOnHand = (qtyOnHand - ?) WHERE inventory_code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setDouble(1, qty);
            pstm.setString(2, iCode);

            if (pstm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

}
