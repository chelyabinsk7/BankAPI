package ru.zhenyaak.bankAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhenyaak.bankAPI.DAO.EmployeeDAO;
import ru.zhenyaak.bankAPI.entity.Account;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Person;

@Service
public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeService (EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public Person createNewPerson(Person person) {
        return employeeDAO.getPersonById(employeeDAO.createNewPerson(person));
    }

    public Account createNewAccount(Account account) {
        return employeeDAO.createNewAccount(account);
    }

    public Card changeCardStatus(Card card) {
        return employeeDAO.changeCardStatus(card);
    }

    public Card getCardByNumber(String number) {
        return employeeDAO.getCardByNumber(number);
    }
}
