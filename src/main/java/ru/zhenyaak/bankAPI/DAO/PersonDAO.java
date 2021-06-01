package ru.zhenyaak.bankAPI.DAO;

import org.springframework.stereotype.Repository;
import ru.zhenyaak.bankAPI.entity.*;

import java.math.BigDecimal;
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

    public void updateBalance(Account account, BigDecimal amount, String change){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE accounts SET balance = ? WHERE id = ?");
            if (change == "from")
                preparedStatement.setBigDecimal(1, account.getBalance().subtract(amount));
            else if (change == "to")
                preparedStatement.setBigDecimal(1, account.getBalance().add(amount));
            preparedStatement.setInt(2, account.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Card createNewCard(Card card){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO cards (number, id_account) VALUES (?, ?)");
            preparedStatement.setString(1, card.getNumber());
            preparedStatement.setInt(2, card.getId_account());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM cards WHERE number = ?");
            preparedStatement.setString(1, card.getNumber());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            card.setStatus_card(rs.getString("status_card"));
            card.setId(rs.getInt("id"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return card;
    }

    public AccountTransaction newAccountTransaction(AccountTransaction accountTransaction, String status_transaction, String message){
        int id_transaction = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO accountTransactions (id_from, id_to, amount, status_transaction, message) VALUES (?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, accountTransaction.getId_from());
            preparedStatement.setInt(2, accountTransaction.getId_to());
            preparedStatement.setBigDecimal(3, accountTransaction.getAmount());
            preparedStatement.setString(4, status_transaction);
            preparedStatement.setString(5, message);
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()){
                if (rs.next()){
                    id_transaction = rs.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM AccountTransactions WHERE id = ?");
            preparedStatement.setInt(1, id_transaction);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            accountTransaction.setId(rs.getInt("id"));
            accountTransaction.setId_from(rs.getInt("id_from"));
            accountTransaction.setId_to(rs.getInt("id_to"));
            accountTransaction.setAmount(rs.getBigDecimal("amount"));
            accountTransaction.setTime(rs.getTimestamp("time"));
            accountTransaction.setStatus_transaction(rs.getString("status_transaction"));
            accountTransaction.setMessage(rs.getString("message"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(accountTransaction);
        return accountTransaction;
    }

//    public AccountTransaction newAccountTransaction(int id_from, int id_to, BigDecimal amount, String status_transaction, String message){
//        AccountTransaction accountTransaction = null;
//        int id_transaction = 0;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement
//                    ("INSERT INTO accountTransactions (id_from, id_to, amount, status_transaction, message) VALUES (?, ?, ?, ?, ?)",
//                            Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setInt(1, id_from);
//            preparedStatement.setInt(2, id_to);
//            preparedStatement.setBigDecimal(3, amount);
//            preparedStatement.setString(4, status_transaction);
//            preparedStatement.setString(5, message);
//            preparedStatement.executeUpdate();
//            try (ResultSet rs = preparedStatement.getGeneratedKeys()){
//                if (rs.next()){
//                    id_transaction = rs.getInt(1);
//                }
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement
//                    ("SELECT * FROM AccountTransaction WHERE id = ?");
//            preparedStatement.setInt(1, id_transaction);
//            ResultSet rs = preparedStatement.executeQuery();
//            rs.next();
//            accountTransaction.setId(rs.getInt("id"));
//            accountTransaction.setId_from(rs.getInt("id_from"));
//            accountTransaction.setId_to(rs.getInt("id_to"));
//            accountTransaction.setAmount(rs.getBigDecimal("amount"));
//            accountTransaction.setTime(rs.getTimestamp("time"));
//            accountTransaction.setStatus_transaction(rs.getString("status_transaction"));
//            accountTransaction.setMessage(rs.getString("message"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return accountTransaction;
//    }

    public void refill(AccountTransaction accountTransaction, int id_from, int id_to, BigDecimal amount){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO accountTransactions (id_from, id_to, amount) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, id_from);
            preparedStatement.setInt(2, id_to);
            preparedStatement.setBigDecimal(3, amount);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Account getAccount(int id){
        Account account = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Accounts WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            account = new Account();
            account.setId(rs.getInt("id"));
            account.setNumber(rs.getString("number"));
            account.setId_owner(rs.getInt("id_owner"));
            account.setBalance(rs.getBigDecimal("balance"));
            account.setStatus_account(rs.getString("status_account"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return account;
    }

    public Person getPerson(int id_person){
        Person person = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Persons WHERE id_person = ?");
            preparedStatement.setInt(1, id_person);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            person = new Person();
            person.setId_person(rs.getInt("id_person"));
            person.setFirstName(rs.getString("firstname"));
            person.setLastName(rs.getString("lastname"));
            person.setBirthday(rs.getDate("birthday"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return person;
    }

    public Card getCard(int id){
        Card card = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Cards WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            card = new Card();
            card.setId(rs.getInt("id"));
            card.setNumber(rs.getString("number"));
            card.setId_account(rs.getInt("id_account"));
            card.setStatus_card(rs.getString("status_card"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return card;
    }

//    public Account getAccount(int id){
//        Account account = null;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement
//                    ("SELECT * FROM Accounts WHERE id = ?");
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//            rs.next();
//            account = new Account();
//            account.setId(rs.getInt("id"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return account;
//    }


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


    public List<AccountTransaction> getAllAccountTransactions() {
        List<AccountTransaction> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM AccountTransactions");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                list.add(new AccountTransaction(rs.getInt("id"),
                        rs.getInt("id_from"),
                        rs.getInt("id_to"),
                        rs.getBigDecimal("amount"),
                        rs.getTimestamp("time"),
                        rs.getString("status_transaction"),
                        rs.getString("message")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
