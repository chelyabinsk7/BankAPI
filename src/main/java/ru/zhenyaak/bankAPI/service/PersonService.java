package ru.zhenyaak.bankAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhenyaak.bankAPI.DAO.PersonDAO;
import ru.zhenyaak.bankAPI.DAO.TestDAO;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Test;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonDAO personDAO;

    public void createNewCard(Card card){
        personDAO.createNewCard(card);
    }

    public List<Card> getAllCards(){
        return personDAO.getAllCards();
    }

    public List<Card> getMyCards(int id_person){
        return personDAO.getMyCards(id_person);
    }
}
