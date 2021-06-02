package ru.zhenyaak.bankAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhenyaak.bankAPI.controller.exceptions.card.CardNotFoundException;
import ru.zhenyaak.bankAPI.entity.Account;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Person;
import ru.zhenyaak.bankAPI.service.ContractorService;
import ru.zhenyaak.bankAPI.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

/*
http://localhost:8080/employee/newperson
{
    "firstName": "Tagir",
    "lastName": "Nuriev",
    "birthday": "1994-02-12"
}
*/
    @PostMapping("/newperson")
    public Person createNewPerson(@RequestBody Person person) {
        return employeeService.createNewPerson(person);
    }

/*
http://localhost:8080/employee/newaccount
{
    "number": "44442222888855551100",
    "id_owner": 1
}
*/
    @PostMapping("/newaccount")
    public Account createNewAccount(@RequestBody Account account) {
        return employeeService.createNewAccount(account);
    }

/*
http://localhost:8080/employee/changecardstatus
{
    "id": 2,
    "card_status": "OPEN"
}
*/
    @PostMapping("/changecardstatus")
    public Card changeCardStatus(@RequestBody Card card){
        if (card.getId() == 0)
            throw new CardNotFoundException("Card with id = " + card.getId() + " not found");
        return employeeService.changeCardStatus(card);
    }
}
