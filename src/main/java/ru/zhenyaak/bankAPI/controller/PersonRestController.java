package ru.zhenyaak.bankAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Test;
import ru.zhenyaak.bankAPI.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonRestController {

    @Autowired
    private PersonService personService;

    @PostMapping("/newcard")
    public Card createNewCard(@RequestBody Card card){
        card.setId(0);
        personService.createNewCard(card);
        return card;
    }

    @GetMapping("/allcards")
    public List<Card> allCards() {
        return personService.getAllCards();
    }

    @GetMapping("/mycards/{id_person}")
    public List<Card> myCards(@PathVariable int id_person){
        return personService.getMyCards(id_person);
    }
}
