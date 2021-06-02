package ru.zhenyaak.bankAPI.DAO;

import org.springframework.stereotype.Repository;
import ru.zhenyaak.bankAPI.controller.exceptions.account.AccountNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.contractor.ContractorNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.person.PersonNotFoundException;
import ru.zhenyaak.bankAPI.entity.Account;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Contractor;
import ru.zhenyaak.bankAPI.entity.Person;

import java.sql.*;

@Repository
public class EmployeeDAO {

    private static final String URL = "jdbc:h2:file:/Users/u19215200/Documents/bankAPI/src/main/resources/data/bank;AUTO_SERVER=true";
    private static final String USERNAME = "Eugeny";
    private static final String PASSWORD = "12345678";

    static{
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int createNewPerson(Person person) {
        int id_owner = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint("Savepoint");
            try (PreparedStatement preparedStatement = connection.prepareStatement
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

            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO persons VALUES (?, ?, ?, ?)")){
                preparedStatement.setInt(1, id_owner);
                preparedStatement.setString(2, person.getFirstName());
                preparedStatement.setString(3, person.getLastName());
                preparedStatement.setDate(4, person.getBirthday());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                connection.rollback(savepoint);
                throw new PersonNotFoundException("Person creation failed because " + throwables);
            }
            return id_owner;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return id_owner;
    }

    public Person getPerson(int id_person) {
        Person person = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Persons WHERE id_person = ?")){
                preparedStatement.setInt(1, id_person);
                try (ResultSet rs = preparedStatement.executeQuery()){
                    person = new Person();
                    rs.next();
                    person.setId_person(rs.getInt("id_person"));
                    person.setFirstName(rs.getString("FirstName"));
                    person.setLastName(rs.getString("LastName"));
                    person.setBirthday(rs.getDate("Birthday"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return person;
    }

    public Account createNewAccount(Account account) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO Accounts (number, id_owner) VALUES (?, ?)")){
                preparedStatement.setString(1, account.getNumber());
                preparedStatement.setInt(2, account.getId_owner());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new AccountNotFoundException("Account creation failed because " + throwables);
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM Accounts WHERE number = ?")){
                preparedStatement.setString(1, account.getNumber());
                try (ResultSet rs = preparedStatement.executeQuery()){
                    rs.next();
                    account.setId(rs.getInt("id"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setStatus_account(rs.getString("status_account"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return account;
    }

    public Card changeCardStatus(Card card) {
        return null;
    }
}
