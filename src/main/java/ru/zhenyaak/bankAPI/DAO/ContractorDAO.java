package ru.zhenyaak.bankAPI.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Contractor;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.List;

@Repository
public class ContractorDAO {

    private static final String URL = "jdbc:h2:file:/Users/u19215200/Documents/bankAPI/src/main/resources/data/bank;AUTO_SERVER=true";
    private static final String USERNAME = "Eugeny";
    private static final String PASSWORD = "12345678";
    private Connection connection;
//    private static ResultSet rs1;
//    private static ResultSet rs2;
//    private static PreparedStatement preparedStatement;
//    private static PreparedStatement preparedStatement1;
//    private static PreparedStatement preparedStatement2;

    static{
//        try {
//            Class.forName("org.h2.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            System.out.println("DB connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
    }

    public int createNewOwner(){
        int id_owner = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("INSERT INTO owners (type_owner) VALUES ('UR')", Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.executeUpdate();
            try (ResultSet rs1 = preparedStatement.getGeneratedKeys()){
                if (rs1.next())
                    id_owner = rs1.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id_owner;
    }

    public Contractor createNewContractor(Contractor contractor, int id_owner) {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("INSERT INTO contractors VALUES (?, ?, ?)")
        ){
            preparedStatement.setInt(1, id_owner);
            preparedStatement.setString(2, contractor.getName());
            preparedStatement.setString(3, contractor.getInn());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contractor;
    }

    public Contractor getContractor (int id_contractor){
        Contractor contractor = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("SELECT * FROM Contractors WHERE id_contractor = ?")
        ){
            preparedStatement.setInt(1, id_contractor);
            try (ResultSet rs = preparedStatement.executeQuery()){
                contractor = new Contractor();
                rs.next();
                contractor.setId_contractor(rs.getInt("id_contractor"));
                contractor.setName(rs.getString("name"));
                contractor.setInn(rs.getString("inn"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contractor;
    }
}
