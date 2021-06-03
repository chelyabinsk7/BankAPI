package ru.zhenyaak.bankAPI.DAO;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.zhenyaak.bankAPI.controller.exceptions.account.AccountNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.accountTransaction.AccountTransactionException;
import ru.zhenyaak.bankAPI.controller.exceptions.card.CardNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.card.CardsListIsEmptyException;
import ru.zhenyaak.bankAPI.controller.exceptions.person.PersonNotFoundException;
import ru.zhenyaak.bankAPI.entity.Account;
import ru.zhenyaak.bankAPI.entity.AccountTransaction;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Person;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonDAOTest {

    private DataSource dataSource;

    @Before
    public void setup(){
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data-test.sql")
                .build();
    }

    @Test
    public void getPerson(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Person person = new Person(1, "Nastya", "Morar", Date.valueOf("1995-06-23"));
        assertEquals(person, personDAO.getPerson(1));
    }

    @Test
    public void getPersonWithException(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Assertions.assertThrows(PersonNotFoundException.class, () -> {personDAO.getPerson(1000);});
    }

    @Test
    public void getAccount(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Account account = new Account (1, "12345672901234567890", 1, BigDecimal.valueOf(1000), "OPEN");
        assertEquals(account, personDAO.getAccount(1));
    }

    @Test
    public void getAccountWithException(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Assertions.assertThrows(AccountNotFoundException.class, () -> {personDAO.getAccount(1000);});
    }

    @Test
    public void getCard(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Card card = new Card (1, "1234567812345677", 1, "OPEN");
        assertEquals(card, personDAO.getCard(1));
    }

    @Test
    public void getCardWithException(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Assertions.assertThrows(CardNotFoundException.class, () -> {personDAO.getCard(1000);});
    }

    @Test
    public void getAllCards(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        assertEquals(5, personDAO.getAllCards().size());
    }

    @Test
    public void getMyCards(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        assertEquals(3, personDAO.getMyCards(1).size());
    }

    @Test
    public void getMyCardsWithPersonNotFoundException(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Assertions.assertThrows(PersonNotFoundException.class, () -> {personDAO.getMyCards(1000);});
    }

    @Test
    public void getMyCardsWithCardNotFoundException(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Assertions.assertThrows(CardsListIsEmptyException.class, () -> {personDAO.getMyCards(5);});
    }

    @Test
    public void createNewCard(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Card card = new Card();
        card.setNumber("7777999900001111");
        card.setId_account(1);
        int sizeBeforeCreate = personDAO.getAllCards().size();
        personDAO.createNewCard(card);
        int sizeAfterCreate = personDAO.getAllCards().size();
        assertEquals(sizeBeforeCreate + 1, sizeAfterCreate);
    }

    @Test
    public void createNewCardWithCardNotFoundException(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        Card card = new Card();
        card.setNumber("1234567812345678");
        card.setId_account(5);
        Assertions.assertThrows(CardNotFoundException.class, () -> {personDAO.createNewCard(card);});
    }

    @Test
    public void newAccountTransaction(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(1);
        accountTransaction.setId_to(2);
        accountTransaction.setAmount(BigDecimal.valueOf(1000));
        assertTrue(personDAO.newAccountTransaction(accountTransaction, null, null).getAmount().equals(BigDecimal.valueOf(1000)));
    }

    @Test
    public void newAccountTransactionWithAccountTransactionException(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(100);
        accountTransaction.setId_to(2);
        accountTransaction.setAmount(BigDecimal.valueOf(1000));
        Assertions.assertThrows(AccountTransactionException.class, () -> {personDAO.newAccountTransaction(accountTransaction, null, null);});
    }

    @Test
    public void getAllAccountTransactions(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        assertEquals(6, personDAO.getAllAccountTransactions().size());
    }

    @Test
    public void refillNormal(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(1);
        accountTransaction.setId_to(2);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        BigDecimal balance_from_old = personDAO.getAccount(1).getBalance();
        BigDecimal balance_from_new = personDAO.getAccount(2).getBalance();
        AccountTransaction accountTransactionNew = personDAO.refill(accountTransaction);
        assertEquals(accountTransactionNew.getStatus_transaction(), "YES");
        assertEquals(accountTransactionNew.getMessage(), "Operation was successfull");
        assertEquals(personDAO.getAccount(1).getBalance(), balance_from_old.subtract(BigDecimal.valueOf(100.9)));
        assertEquals(personDAO.getAccount(2).getBalance(), balance_from_new.add(BigDecimal.valueOf(100.9)));
    }

    @Test
    public void refillAccountFromClose(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(4);
        accountTransaction.setId_to(2);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        BigDecimal balance_from_old = personDAO.getAccount(4).getBalance();
        BigDecimal balance_from_new = personDAO.getAccount(2).getBalance();
        AccountTransaction accountTransactionNew = personDAO.refill(accountTransaction);
        assertEquals(accountTransactionNew.getStatus_transaction(), "NO");
        assertEquals(accountTransactionNew.getMessage(), "Operation failed, account_from is close");
        assertEquals(personDAO.getAccount(1).getBalance(), balance_from_old);
        assertEquals(personDAO.getAccount(2).getBalance(), balance_from_new);
    }

    @Test
    public void refillAccountToClose(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(1);
        accountTransaction.setId_to(4);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        BigDecimal balance_from_old = personDAO.getAccount(4).getBalance();
        BigDecimal balance_from_new = personDAO.getAccount(2).getBalance();
        AccountTransaction accountTransactionNew = personDAO.refill(accountTransaction);
        assertEquals(accountTransactionNew.getStatus_transaction(), "NO");
        assertEquals(accountTransactionNew.getMessage(), "Operation failed, account_to is close");
        assertEquals(personDAO.getAccount(1).getBalance(), balance_from_old);
        assertEquals(personDAO.getAccount(2).getBalance(), balance_from_new);
    }

    @Test
    public void refillNotEnoughMoney(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(1);
        accountTransaction.setId_to(2);
        accountTransaction.setAmount(BigDecimal.valueOf(1000.9));
        BigDecimal balance_from_old = personDAO.getAccount(1).getBalance();
        BigDecimal balance_from_new = personDAO.getAccount(2).getBalance();
        AccountTransaction accountTransactionNew = personDAO.refill(accountTransaction);
        assertEquals(accountTransactionNew.getStatus_transaction(), "NO");
        assertEquals(accountTransactionNew.getMessage(), "Operation failed, not enough money in the account");
        assertEquals(personDAO.getAccount(1).getBalance(), balance_from_old);
        assertEquals(personDAO.getAccount(2).getBalance(), balance_from_new);
    }

    @Test
    public void refillWithAccountNotFoundException(){
        PersonDAO personDAO = new PersonDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(100);
        accountTransaction.setId_to(2);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        Assertions.assertThrows(AccountNotFoundException.class, () -> {personDAO.refill(accountTransaction);});
    }

    @Test
    public void test1(){
        assertTrue(1 > 0);
    }

}
