package lk.ijse.cuisineCompass.model;

import lk.ijse.cuisineCompass.db.DBConnection;
import lk.ijse.cuisineCompass.dto.Category;
import lk.ijse.cuisineCompass.dto.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CategoryModel {

    public static List<Category> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM category";

        List<Category> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Category(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return data;
    }

    public static List<String> getCate() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT category_code FROM category";

        List<String> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new String(
                    resultSet.getString(1)
            ));
        }
        return data;
    }

}
