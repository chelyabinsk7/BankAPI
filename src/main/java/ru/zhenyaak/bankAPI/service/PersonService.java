package ru.zhenyaak.bankAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhenyaak.bankAPI.DAO.PersonDAO;
import ru.zhenyaak.bankAPI.entity.*;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PersonService {

    private final PersonDAO personDAO;

    @Autowired
    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public AccountTransaction refill(AccountTransaction accountTransaction) {
        return personDAO.refill(accountTransaction);
    }

    public Account getAccountById(int id) {
        return personDAO.getAccountById(id);
    }

    public Person getPersonById(int id_person) {
        return personDAO.getPersonById(id_person);
    }

    public Card getCardById(int id) {
        return personDAO.getCardById(id);
    }

    public Card createNewCard(Card card) {
        return personDAO.createNewCard(card);
    }

    public List<Card> getAllCards() {
        return personDAO.getAllCards();
    }

    public List<Card> getMyCards(int id_person) {
        return personDAO.getMyCards(id_person);
    }

    public List<AccountTransaction> getAllAccountTransactions() {
        return personDAO.getAllAccountTransactions();
    }

    public BigDecimal getAccountBalance(int id) {
        return personDAO.getAccountById(id).getBalance();
    }

}
