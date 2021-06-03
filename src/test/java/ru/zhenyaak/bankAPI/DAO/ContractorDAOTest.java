package ru.zhenyaak.bankAPI.DAO;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.zhenyaak.bankAPI.controller.exceptions.account.AccountNotFoundException;
import ru.zhenyaak.bankAPI.controller.exceptions.account.AccountTypeException;
import ru.zhenyaak.bankAPI.controller.exceptions.accountTransaction.AccountTransactionException;
import ru.zhenyaak.bankAPI.controller.exceptions.contractor.ContractorNotFoundException;
import ru.zhenyaak.bankAPI.entity.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import static org.junit.Assert.*;

public class ContractorDAOTest {

    private DataSource dataSource;

    @Before
    public void setup(){
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data-test.sql")
                .build();
    }

    @Test
    public void getAccountById(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        Account account = new Account (1, "12345672901234567890", 1, BigDecimal.valueOf(1000), "OPEN");
        assertEquals(account, contractorDAO.getAccountById(1));
    }

    @Test
    public void getAccountByIdWithException(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        Assertions.assertThrows(AccountNotFoundException.class, () -> {contractorDAO.getAccountById(1000);});
    }

    @Test
    public void getContractorById(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        Contractor contractor = new Contractor(6, "Granit", "1234567890");
        assertEquals(contractor, contractorDAO.getContractorById(6));
    }

    @Test
    public void getContractorByIdWithException(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        Assertions.assertThrows(ContractorNotFoundException.class, () -> {contractorDAO.getContractorById(1000);});
    }

    @Test
    public void getAllContractors(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        assertEquals(3, contractorDAO.getAllContractors().size());
    }

    @Test
    public void createNewContractor(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        Contractor contractor = new Contractor();
        contractor.setName("VTB");
        contractor.setInn("6662229991");
        int sizeBeforeCreate = contractorDAO.getAllContractors().size();
        contractorDAO.createNewContractor(contractor);
        int sizeAfterCreate = contractorDAO.getAllContractors().size();
        assertEquals(sizeBeforeCreate + 1, sizeAfterCreate);
    }

    @Test
    public void createNewContractorWithContractorNotFoundException(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        Contractor contractor = new Contractor();
        contractor.setName("VTB");
        contractor.setInn("1234567890"); // С таким ИНН уже существует
        Assertions.assertThrows(ContractorNotFoundException.class, () -> {contractorDAO.createNewContractor(contractor);});
    }

    @Test
    public void newAccountTransaction(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(1);
        accountTransaction.setId_to(2);
        accountTransaction.setAmount(BigDecimal.valueOf(1000));
        assertTrue(contractorDAO.newAccountTransaction(accountTransaction, null, null).getAmount().equals(BigDecimal.valueOf(1000)));
    }

    @Test
    public void newAccountTransactionWithAccountTransactionException(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(100);
        accountTransaction.setId_to(2);
        accountTransaction.setAmount(BigDecimal.valueOf(1000));
        Assertions.assertThrows(AccountTransactionException.class, () -> {contractorDAO.newAccountTransaction(accountTransaction, null, null);});
    }

    @Test
    public void refillNormal(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(9);
        accountTransaction.setId_to(10);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        BigDecimal balance_from_old = contractorDAO.getAccountById(9).getBalance();
        BigDecimal balance_from_new = contractorDAO.getAccountById(10).getBalance();
        AccountTransaction accountTransactionNew = contractorDAO.refill(accountTransaction);
        assertEquals(accountTransactionNew.getStatus_transaction(), "YES");
        assertEquals(accountTransactionNew.getMessage(), "Operation was successfull");
        assertEquals(contractorDAO.getAccountById(9).getBalance(), balance_from_old.subtract(BigDecimal.valueOf(100.9)));
        assertEquals(contractorDAO.getAccountById(10).getBalance(), balance_from_new.add(BigDecimal.valueOf(100.9)));
    }

    @Test
    public void refillAccountFromClose(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(8);
        accountTransaction.setId_to(10);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        BigDecimal balance_from_old = contractorDAO.getAccountById(8).getBalance();
        BigDecimal balance_from_new = contractorDAO.getAccountById(10).getBalance();
        AccountTransaction accountTransactionNew = contractorDAO.refill(accountTransaction);
        assertEquals(accountTransactionNew.getStatus_transaction(), "NO");
        assertEquals(accountTransactionNew.getMessage(), "Operation failed, account_from is close");
        assertEquals(contractorDAO.getAccountById(8).getBalance(), balance_from_old);
        assertEquals(contractorDAO.getAccountById(10).getBalance(), balance_from_new);
    }

    @Test
    public void refillAccountToClose(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(9);
        accountTransaction.setId_to(8);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        BigDecimal balance_from_old = contractorDAO.getAccountById(9).getBalance();
        BigDecimal balance_from_new = contractorDAO.getAccountById(8).getBalance();
        AccountTransaction accountTransactionNew = contractorDAO.refill(accountTransaction);
        assertEquals(accountTransactionNew.getStatus_transaction(), "NO");
        assertEquals(accountTransactionNew.getMessage(), "Operation failed, account_to is close");
        assertEquals(contractorDAO.getAccountById(9).getBalance(), balance_from_old);
        assertEquals(contractorDAO.getAccountById(8).getBalance(), balance_from_new);
    }

    @Test
    public void refillAccountFromNonContractor(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(1);
        accountTransaction.setId_to(10);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        Assertions.assertThrows(AccountTypeException.class, () -> {contractorDAO.refill(accountTransaction);});
    }

    @Test
    public void refillAccountToNonContractor(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(9);
        accountTransaction.setId_to(1);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        Assertions.assertThrows(AccountTypeException.class, () -> {contractorDAO.refill(accountTransaction);});
    }

    @Test
    public void refillNotEnoughMoney(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(9);
        accountTransaction.setId_to(10);
        accountTransaction.setAmount(BigDecimal.valueOf(1000.9));
        BigDecimal balance_from_old = contractorDAO.getAccountById(9).getBalance();
        BigDecimal balance_from_new = contractorDAO.getAccountById(10).getBalance();
        AccountTransaction accountTransactionNew = contractorDAO.refill(accountTransaction);
        assertEquals(accountTransactionNew.getStatus_transaction(), "NO");
        assertEquals(accountTransactionNew.getMessage(), "Operation failed, not enough money in the account");
        assertEquals(contractorDAO.getAccountById(9).getBalance(), balance_from_old);
        assertEquals(contractorDAO.getAccountById(10).getBalance(), balance_from_new);
    }

    @Test
    public void refillWithAccountNotFoundException(){
        ContractorDAO contractorDAO = new ContractorDAO(dataSource);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setId_from(100);
        accountTransaction.setId_to(2);
        accountTransaction.setAmount(BigDecimal.valueOf(100.9));
        Assertions.assertThrows(AccountNotFoundException.class, () -> {contractorDAO.refill(accountTransaction);});
    }

}
