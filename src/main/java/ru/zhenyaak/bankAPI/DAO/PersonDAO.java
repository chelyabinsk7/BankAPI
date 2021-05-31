package ru.zhenyaak.bankAPI.DAO;

import org.springframework.stereotype.Repository;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Person;
import ru.zhenyaak.bankAPI.entity.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAO {

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

    public void createNewCard(Card card){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO cards (number, id_account) VALUES (?, ?)");
            preparedStatement.setString(1, card.getNumber());
            preparedStatement.setInt(2, card.getId_account());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Card> getAllCards(){
        List<Card> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cards");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                list.add(new Card(rs.getInt("id"),
                                  rs.getString("number"),
                                  rs.getInt("id_account"),
                                  rs.getString("status_card")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Card> getMyCards(int id_person){
        List<Card> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT c.id, c.number, c.id_account, c.status_card FROM persons AS p " +
                            "INNER JOIN accounts AS a ON a.id_owner = p.id_person " +
                            "INNER JOIN cards AS c ON c.id_account = a.id " +
                            "WHERE p.id_person = ?");
            preparedStatement.setInt(1, id_person);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                list.add(new Card(rs.getInt("id"),
                        rs.getString("number"),
                        rs.getInt("id_account"),
                        rs.getString("status_card")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
