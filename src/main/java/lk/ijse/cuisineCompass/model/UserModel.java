package lk.ijse.cuisineCompass.model;

import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserModel {
    private static final String URL = "jdbc:mysql://localhost:3306/cuisine_compass";
    private final static Properties props = new Properties();
    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<User> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM User";

        List<User> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    public static boolean isValid(String userName, String pw) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        //String sql = "SELECT * FROM user WHERE user_name =? AND password =? AND job_role =? ";
        String sql = "SELECT * FROM user WHERE user_name = ? AND password = ? AND (job_role = ? OR job_role = ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1,userName);
        pstm.setString(2,pw);
        pstm.setString(3,"Head Chef");
        pstm.setString(4,"Sous Chef");
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return true;
        }
        return false;
    }
    public static boolean isSup(String userName, String pw) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user WHERE user_name = ? AND password = ? AND job_role = ? ";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1,userName);
        pstm.setString(2,pw);
        pstm.setString(3,"Chef de Partie");
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public static boolean isDeleted(String id) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM user WHERE emp_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUpdated(String id, String jobRole, String userName, String pW) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "UPDATE user SET user_name = ?, password = ?, job_role = ? WHERE emp_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, pW);
        pstm.setString(3, jobRole);
        pstm.setString(4, id);

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isCreated(String userName, String pW, String id, String jobRole) throws SQLException {

        Connection con = DriverManager.getConnection(URL, props);
        String sql = "INSERT INTO user(user_name, password, emp_id, job_role) VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, pW);
        pstm.setString(3, id);
        pstm.setString(4, jobRole);

        int affectedRows = pstm.executeUpdate();
        if(affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ResultSet getImage(String userName) throws SQLException {
        Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT image FROM image_user WHERE user_name = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, userName);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return resultSet;
        }
        return null;
    }

    /*public static String getTxt(String userName) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT user_name FROM user WHERE user_name =? ";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1,userName);
        return null;
    }*/
}
