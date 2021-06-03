package ru.zhenyaak.bankAPI.DAO;

import org.springframework.stereotype.Repository;
import ru.zhenyaak.bankAPI.controller.exceptions.account.AccountNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.card.CardNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.person.PersonNotFoundException;
import ru.zhenyaak.bankAPI.entity.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAO {

    static{
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DataSource dataSource;

    public PersonDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }


    public Person getPerson(int id_person){
        Person person = null;
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Persons WHERE id_person = ?")){
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
                throw new PersonNotFoundException("Person with id_person = " + id_person + " not found. More details: " + throwables);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return person;
    }

    public Account getAccount(int id){
        Account account = null;
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Accounts WHERE id = ?")){
                preparedStatement.setInt(1, id);
                try (ResultSet rs = preparedStatement.executeQuery()){
                    rs.next();
                    account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setNumber(rs.getString("number"));
                    account.setId_owner(rs.getInt("id_owner"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setStatus_account(rs.getString("status_account"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new AccountNotFoundException("Account with id = " + id + " not found. More details: " + throwables);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return account;
    }

    public Card getCard(int id){
        Card card = null;
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Cards WHERE id = ?")){
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
                throw new CardNotFoundException("Card with id = " + id + " not found. More details" + throwables);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return card;
    }

    public List<Card> getAllCards(){
        List<Card> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cards")){
                try (ResultSet rs = preparedStatement.executeQuery()){
                    while (rs.next())
                        list.add(new Card(rs.getInt("id"),
                                          rs.getString("number"),
                                          rs.getInt("id_account"),
                                          rs.getString("status_card")));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return list;
    }

    public List<Card> getMyCards(int id_person){
        List<Card> list = new ArrayList<>();
        Person person = null;
        try (Connection connection = dataSource.getConnection()){

            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Persons WHERE id_person = ?")){
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
                throw new PersonNotFoundException("Person with id_person = " + id_person + " not found. More details: " + throwables);
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT c.id, c.number, c.id_account, c.status_card FROM persons AS p " +
                            "INNER JOIN accounts AS a ON a.id_owner = p.id_person " +
                            "INNER JOIN cards AS c ON c.id_account = a.id " +
                            "WHERE p.id_person = ?")){
                preparedStatement.setInt(1, id_person);
                try (ResultSet rs = preparedStatement.executeQuery()){
                    if (rs.next()){
                        do {
                            list.add(new Card(rs.getInt("id"),
                                    rs.getString("number"),
                                    rs.getInt("id_account"),
                                    rs.getString("status_card")));
                        } while (rs.next());
                    }
                    else
                        throw new CardNotFoundException("Person with id_person = " + id_person + " has no cards");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return list;
    }

    public AccountTransaction refill(AccountTransaction accountTransaction){
        Account account_from = null;
        Account account_to = null;
        int id_transaction = 0;

        try (Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement
                        ("SELECT * FROM Accounts WHERE id = ?")){
                preparedStatement.setInt(1, accountTransaction.getId_from());
                try (ResultSet rs = preparedStatement.executeQuery()){
                    rs.next();
                    account_from = new Account();
                    account_from.setId(rs.getInt("id"));
                    account_from.setNumber(rs.getString("number"));
                    account_from.setId_owner(rs.getInt("id_owner"));
                    account_from.setBalance(rs.getBigDecimal("balance"));
                    account_from.setStatus_account(rs.getString("status_account"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (account_from.getId() == 0)
                throw new AccountNotFoundException("Account_from with id = " + accountTransaction.getId_from() + " not found");
            if (account_from.getStatus_account().equals("CLOSE"))
                return newAccountTransaction(accountTransaction, "NO", "Account_from is close");

            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Accounts WHERE id = ?")){
                preparedStatement.setInt(1, accountTransaction.getId_to());
                try (ResultSet rs = preparedStatement.executeQuery()){
                    rs.next();
                    account_to = new Account();
                    account_to.setId(rs.getInt("id"));
                    account_to.setNumber(rs.getString("number"));
                    account_to.setId_owner(rs.getInt("id_owner"));
                    account_to.setBalance(rs.getBigDecimal("balance"));
                    account_to.setStatus_account(rs.getString("status_account"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (account_to.getId() == 0)
                throw new AccountNotFoundException("Account_to with id = " + accountTransaction.getId_to() + " not found");
            if (account_to.getStatus_account().equals("CLOSE"))
                return newAccountTransaction(accountTransaction, "NO", "Account_to is close");

            if (account_from.getBalance().compareTo(accountTransaction.getAmount()) < 0)
                return newAccountTransaction(accountTransaction, "NO", "Operation failed, not enough money in the account");

            Savepoint savepoint = connection.setSavepoint("Savepoint");

            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE accounts SET balance = ? WHERE id = ?")
            ){
                preparedStatement.setBigDecimal(1, account_from.getBalance().subtract(accountTransaction.getAmount()));
                preparedStatement.setInt(2, account_from.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE accounts SET balance = ? WHERE id = ?")
            ){
                preparedStatement.setBigDecimal(1, account_to.getBalance().add(accountTransaction.getAmount()));
                preparedStatement.setInt(2, account_to.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                connection.rollback(savepoint);
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement
                        ("INSERT INTO accountTransactions (id_from, id_to, amount, status_transaction, message) VALUES (?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setInt(1, accountTransaction.getId_from());
                preparedStatement.setInt(2, accountTransaction.getId_to());
                preparedStatement.setBigDecimal(3, accountTransaction.getAmount());
                preparedStatement.setString(4, "YES");
                preparedStatement.setString(5, "Operation was successfull");
                preparedStatement.executeUpdate();
                try (ResultSet rs = preparedStatement.getGeneratedKeys()){
                    if (rs.next())
                        id_transaction = rs.getInt(1);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                connection.rollback();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement
                        ("SELECT * FROM AccountTransactions WHERE id = ?")){
                preparedStatement.setInt(1, id_transaction);
                try (ResultSet rs = preparedStatement.executeQuery()){
                    rs.next();
                    accountTransaction.setId(rs.getInt("id"));
                    accountTransaction.setId_from(rs.getInt("id_from"));
                    accountTransaction.setId_to(rs.getInt("id_to"));
                    accountTransaction.setAmount(rs.getBigDecimal("amount"));
                    accountTransaction.setTime(rs.getTimestamp("time"));
                    accountTransaction.setStatus_transaction(rs.getString("status_transaction"));
                    accountTransaction.setMessage(rs.getString("message"));
                }
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return accountTransaction;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return accountTransaction;
    }

    public Card createNewCard(Card card){
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO cards (number, id_account) VALUES (?, ?)")){
                preparedStatement.setString(1, card.getNumber());
                preparedStatement.setInt(2, card.getId_account());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM cards WHERE number = ?")){
                preparedStatement.setString(1, card.getNumber());
                try (ResultSet rs = preparedStatement.executeQuery()){
                    rs.next();
                    card.setStatus_card(rs.getString("status_card"));
                    card.setId(rs.getInt("id"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return card;
    }

    public AccountTransaction newAccountTransaction(AccountTransaction accountTransaction, String status_transaction, String message){
        int id_transaction = 0;
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO accountTransactions (id_from, id_to, amount, status_transaction, message) VALUES (?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS)){
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

            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM AccountTransactions WHERE id = ?")){
                preparedStatement.setInt(1, id_transaction);
                try (ResultSet rs = preparedStatement.executeQuery()){
                    rs.next();
                    accountTransaction.setId(rs.getInt("id"));
                    accountTransaction.setId_from(rs.getInt("id_from"));
                    accountTransaction.setId_to(rs.getInt("id_to"));
                    accountTransaction.setAmount(rs.getBigDecimal("amount"));
                    accountTransaction.setTime(rs.getTimestamp("time"));
                    accountTransaction.setStatus_transaction(rs.getString("status_transaction"));
                    accountTransaction.setMessage(rs.getString("message"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return accountTransaction;
    }




    public List<AccountTransaction> getAllAccountTransactions() {
        List<AccountTransaction> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM AccountTransactions")){
                try (ResultSet rs = preparedStatement.executeQuery()){
                    while (rs.next())
                        list.add(new AccountTransaction(rs.getInt("id"),
                                                        rs.getInt("id_from"),
                                                        rs.getInt("id_to"),
                                                        rs.getBigDecimal("amount"),
                                                        rs.getTimestamp("time"),
                                                        rs.getString("status_transaction"),
                                                        rs.getString("message")));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return list;
    }

//    public void insert1() {
//        try (Connection connection = dataSource.getConnection()){
//            try (PreparedStatement preparedStatement = connection.prepareStatement
//                    ("INSERT INTO owners (type_owner) VALUES (?)")) {
//                preparedStatement.setString(1, "FIZ");
//                preparedStatement.executeUpdate();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("No connection to DB");
//        }
//    }
//
//    public void insert2() {
//        try (Connection connection = dataSource.getConnection()){
//            try (PreparedStatement preparedStatement = connection.prepareStatement
//                    ("INSERT INTO owners (type_owner) VALUES (?)")) {
//                preparedStatement.setString(1, "URO");
//                preparedStatement.executeUpdate();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("No connection to DB");
//        }
//    }
}
