package lk.ijse.cuisineCompass.model;

import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.dto.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SupplierModel {

    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<Supplier> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";

        List<Supplier> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    resultSet.getString(3)
            ));
        }
        return data;
    }

    public static List<String> getAllSup() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT supplier_id FROM supplier";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static boolean isSaved(String id, String name, String email, String contact) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "INSERT INTO supplier(supplier_id, name, contact, email) VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);
        pstm.setString(2, name);
        pstm.setString(3, contact);
        pstm.setString(4, email);

        int affectedRows = pstm.executeUpdate();
        if(affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isUpdated(String id, String name, String contact, String email) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "UPDATE supplier SET name = ?, contact = ?, email = ? WHERE supplier_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, contact);
        pstm.setString(3, email);
        pstm.setString(4, id);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isDeleted(String id) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM supplier WHERE supplier_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

}
