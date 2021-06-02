package ru.zhenyaak.bankAPI.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyaak.bankAPI.controller.exceptions.account.AccountNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.contractor.ContractorNotFoundException;
import ru.zhenyaak.bankAPI.entity.Account;
import ru.zhenyaak.bankAPI.entity.AccountTransaction;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Contractor;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractorDAO {

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

    public Contractor getContractor (int id_contractor){
        Contractor contractor = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            try (PreparedStatement preparedStatement = connection.prepareStatement
                     ("SELECT * FROM Contractors WHERE id_contractor = ?")){
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
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return contractor;
    }

    public int createNewContractor(Contractor contractor) {
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
                         ("INSERT INTO contractors VALUES (?, ?, ?)")){
                preparedStatement.setInt(1, id_owner);
                preparedStatement.setString(2, contractor.getName());
                preparedStatement.setString(3, contractor.getInn());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                connection.rollback(savepoint);
                throw new ContractorNotFoundException("Contractor creation failed because " + throwables);
            }
            return id_owner;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to DB");
        }
        return id_owner;
    }

    public List<Contractor> getAllContractors() {
        List<Contractor> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contractors")){
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                    list.add(new Contractor(rs.getInt("id_contractor"),
                                            rs.getString("name"),
                                            rs.getString("inn")));
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

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
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

    public AccountTransaction newAccountTransaction(AccountTransaction accountTransaction, String status_transaction, String message){
        int id_transaction = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
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

}
