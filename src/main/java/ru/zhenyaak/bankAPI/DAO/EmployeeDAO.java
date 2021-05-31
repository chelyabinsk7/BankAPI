package ru.zhenyaak.bankAPI.DAO;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class EmployeeDAO {

    private static final String URL = "jdbc:h2:file:/Users/u19215200/Documents/bankAPI/src/main/resources/data/bank;AUTO_SERVER=true";
    private static final String USERNAME = "Eugeny";
    private static final String PASSWORD = "12345678";
    private static Connection connection;

    static{
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("DB connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
    }
}
