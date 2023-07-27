package lk.ijse.cuisineCompass.model;

import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.dto.Employee;
import lk.ijse.cuisineCompass.dto.Order;
import lk.ijse.cuisineCompass.dto.OrderDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderModel {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static Order getAll(String oCode) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM ordr";
        Order order = null;
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getString(1).equalsIgnoreCase(oCode)) {
                order = new Order(
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(2)
                );
            }
        }
        return order;
    }

    public static List<Order> getAll1() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM ordr";

        List<Order> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    public static List<Order> getAllDetails(String oCode) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM order_details";



        List<Order> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getString(1).equalsIgnoreCase(oCode)) {
                data.add(new Order(
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5)
                ));
            }
        }
        return data;
    }

    public static List<OrderDetails> getAllDetails1() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM order_details";

        List<OrderDetails> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new OrderDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            ));
        }
        return data;
    }

    public static List<String> getCodes() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT order_code FROM ordr";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static boolean isSaved(String oCode, String id, Date date, String orderBy) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "INSERT INTO ordr(order_code, user_name, supplier_id, date) VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oCode);
        pstm.setString(2, orderBy);
        pstm.setString(3, id);
        pstm.setDate(4, date);

        int affectedRows = pstm.executeUpdate();
        if(affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSaved1(String oCode, String iCode, double price, double qty, double tot) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "INSERT INTO order_details(order_code, inventory_code, unit_price, qty, tot) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oCode);
        pstm.setString(2, iCode);
        pstm.setDouble(3, price);
        pstm.setDouble(4, qty);
        pstm.setDouble(5, tot);

        int affectedRows = pstm.executeUpdate();
        if(affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isUpdated(String oCode, String id, Date date, String orderBy) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "UPDATE ordr SET supplier_id = ?, date = ?, orderBy = ? WHERE order_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);
        pstm.setDate(2, date);
        pstm.setString(3, orderBy);
        pstm.setString(4, oCode);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isUpdated1(String oCode, String iCode, double price, double qty, double tot) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "UPDATE order_details SET inventory_code = ?, unit_price = ?, qty = ?, tot = ? WHERE order_code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1, iCode);
        pstm.setDouble(2, price);
        pstm.setDouble(3, qty);
        pstm.setDouble(4, tot);
        pstm.setString(5, oCode);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isDeleted(String oCode) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM ordr WHERE order_code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, oCode);

            if (pstm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDeleted1(String oCode) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM order_details WHERE order_code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, oCode);

            if (pstm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSet(String code, String iCode, double price, double qty, double tot) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO order_details(order_code, inventory_code, unit_price, qty, tot) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);
        pstm.setString(2, iCode);
        pstm.setDouble(3, price);
        pstm.setDouble(4, qty);
        pstm.setDouble(5, tot);

        int affectedRows = pstm.executeUpdate();

        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    /*public static List<OrderDetails> getAllOrderDetails(String oCode) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM order_details WHERE order_code = ?";

        List<OrderDetails> data = new ArrayList<>();

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oCode);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            data.add(new OrderDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            ));
        }
        return data;
    }*/
}
