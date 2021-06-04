package ru.zhenyaak.bankAPI.DAO;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.zhenyaak.bankAPI.controller.exceptions.account.AccountNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.card.CardChangeStatusException;
import ru.zhenyaak.bankAPI.controller.exceptions.card.CardNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.contractor.ContractorNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.person.PersonNotFoundException;
import ru.zhenyaak.bankAPI.entity.Account;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Contractor;
import ru.zhenyaak.bankAPI.entity.Person;

import javax.sql.DataSource;
import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EmployeeDAOTest {

    private DataSource dataSource;

    @Before
    public void setup(){
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data-test.sql")
                .build();
    }

    @Test
    public void getPersonById(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Person person = new Person(1, "Nastya", "Morar", Date.valueOf("1995-06-23"));
        assertEquals(person, employeeDAO.getPersonById(1));
    }

    @Test
    public void getPersonByIdWithException(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Assertions.assertThrows(PersonNotFoundException.class, () -> {employeeDAO.getPersonById(1000);});
    }

    @Test
    public void createNewPerson(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Person person = new Person();
        person.setFirstName("Kosta");
        person.setLastName("Chvoro");
        person.setBirthday(Date.valueOf("1995-04-22"));
        assertEquals(9, employeeDAO.createNewPerson(person));
    }

    @Test
    public void getCardByNumber(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Card card = new Card(1, "1234567812345677", 1, "OPEN");
        assertEquals(card, employeeDAO.getCardByNumber("1234567812345677"));
    }

    @Test
    public void getCardByNumberWithException() {
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Assertions.assertThrows(CardNotFoundException.class, () -> {employeeDAO.getCardByNumber("1234567812345670");});
    }

    @Test
    public void changeCardStatusNormal(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Card card = new Card();
        card.setNumber("1234567812345678");
        card.setStatus_card("OPEN");
        Card cardBeforeUpdate = employeeDAO.getCardByNumber(card.getNumber());
        Card cardAfterUpdate = employeeDAO.changeCardStatus(card);
        assertEquals(card.getNumber(), cardAfterUpdate.getNumber());
        assertNotEquals(cardBeforeUpdate.getStatus_card(), cardAfterUpdate.getStatus_card());
    }

    @Test
    public void changeCardStatusWithCardNotFoundException(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Card card = new Card();
        card.setNumber("1234567812345670"); // Нет карты с таким номером
        card.setStatus_card("OPEN");
        Assertions.assertThrows(CardNotFoundException.class, () -> {employeeDAO.changeCardStatus(card);});
    }

    @Test
    public void changeCardStatusWithCardChangeStatusException(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Card card = new Card();
        card.setNumber("1234567812345678");
        card.setStatus_card("CREATED"); // Совпадение с текущим статусом
        Assertions.assertThrows(CardChangeStatusException.class, () -> {employeeDAO.changeCardStatus(card);});
    }

    @Test
    public void createNewAccount(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Account account = new Account();
        account.setId_owner(1);
        account.setNumber("44442222888855551100");
        assertEquals(11, employeeDAO.createNewAccount(account).getId());
    }

    @Test
    public void createNewAccountIncorrectOwnerWithAccountNotFoundException(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Account account = new Account();
        account.setId_owner(100); // Владельца с таким id нет
        account.setNumber("44442222888855551100");
        Assertions.assertThrows(AccountNotFoundException.class, () -> {employeeDAO.createNewAccount(account);});
    }

    @Test
    public void createNewAccountRepeatNumberWithAccountNotFoundException(){
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Account account = new Account();
        account.setId_owner(1);
        account.setNumber("12345678906204567892"); // Такой номер счёта уже есть
        Assertions.assertThrows(AccountNotFoundException.class, () -> {employeeDAO.createNewAccount(account);});
    }

}
