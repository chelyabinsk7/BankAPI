package ru.zhenyaak.bankAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.zhenyaak.bankAPI.controller.exceptions.person.PersonNotFoundException;
import ru.zhenyaak.bankAPI.entity.AccountTransaction;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Person;
import ru.zhenyaak.bankAPI.service.ContractorService;
import ru.zhenyaak.bankAPI.service.PersonService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonRestController {

    private final PersonService personService;

    @Autowired
    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    /*
    http://localhost:8080/person/newcard
    {
        "number": "1892567800456780",
        "id_account": 3
    }
    */
    @PostMapping("/newcard")
    public Card createNewCard(@RequestBody Card card) {
        return personService.createNewCard(card);
    }

/*
http://localhost:8080/person/refill
{
    "id_from": 1,
    "id_to": 2,
    "amount": "100.9"
}
*/
    @PostMapping("/refill")
    public AccountTransaction refill(@RequestBody AccountTransaction accountTransaction){
        return personService.refill(accountTransaction);
    }

/*
http://localhost:8080/person/allcards
*/
    @GetMapping("/allcards")
    public List<Card> allCards() {
        return personService.getAllCards();
    }

/*
http://localhost:8080/person/allaccounttransactions
*/
    @GetMapping("/allaccounttransactions")
    public List<AccountTransaction> allAccountTransactions() {
        return personService.getAllAccountTransactions();
    }

/*
http://localhost:8080/person/mycards/1
*/
    @GetMapping("/mycards/{id_person}")
    public List<Card> myCards(@PathVariable int id_person){
        Person person = personService.getPerson(id_person);
        if (person.getId_person() == 0)
            throw new PersonNotFoundException("Person with id_person = " + id_person + " not found");
        List<Card> cards = personService.getMyCards(id_person);
        return cards;
    }


/*
http://localhost:8080/person/id/1
*/
    @GetMapping("/id/{id_person}")
    public Person getPerson(@PathVariable int id_person){
        Person person = personService.getPerson(id_person);
        if (person.getId_person() == 0)
            throw new PersonNotFoundException("Person with id_person = " + id_person + " not found");
        return person;
    }

/*
http://localhost:8080/person/card/1
*/
    @GetMapping("/card/{id}")
    public Card getCard(@PathVariable int id){
        Card card = personService.getCard(id);
        return card;
    }

/*
http://localhost:8080/person/account/1/balance
*/
    @GetMapping("/account/{id}/balance")
    public BigDecimal getAccountBalance(@PathVariable int id){
        return personService.getAccountBalance(id);
    }
}
