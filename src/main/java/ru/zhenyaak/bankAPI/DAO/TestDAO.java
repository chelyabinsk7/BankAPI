package ru.zhenyaak.bankAPI.DAO;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.zhenyaak.bankAPI.entity.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestDAO {

//    @Value("${spring.datasource.url}")
//    private String URL;
//
//    @Value("${spring.datasource.username}")
//    private String USERNAME;
//
//    @Value("${spring.datasource.password}")
//    private String PASSWORD= "12345678";

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

    public List<Test> getTest(){
        List<Test> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM test");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                list.add(new Test(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
