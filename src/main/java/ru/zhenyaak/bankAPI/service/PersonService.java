package ru.zhenyaak.bankAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyaak.bankAPI.DAO.PersonDAO;
import ru.zhenyaak.bankAPI.DAO.TestDAO;
import ru.zhenyaak.bankAPI.entity.*;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonDAO personDAO;

//    @Transactional
//    public AccountTransaction refill(int id_from, int id_to, BigDecimal amount){
//        Account account_from = personDAO.getAccount(id_from);
//        Account account_to = personDAO.getAccount(id_to);
//        if (account_from.getStatus_account().equals("OPEN")){
//            if (account_to.getStatus_account().equals("OPEN")){
//                if (account_from.getBalance().compareTo(amount) >= 0 ){
//                    personDAO.updateBalance(account_from, amount, "from");
//                    personDAO.updateBalance(account_to, amount, "to");
//                    return personDAO.newAccountTransaction(id_from, id_to, amount, "YES", "Operation was successful");
//                }
//                else
//                    return personDAO.newAccountTransaction(id_from, id_to, amount, "NO",
//                            "Operation failed, not enough money in the account");
//            }
//            else
//               return personDAO.newAccountTransaction(id_from, id_to, amount, "NO", "Operation failed, account_to is close");
//        }
//        else
//            return personDAO.newAccountTransaction(id_from, id_to, amount, "NO", "Operation failed, account_from is close");
//    }

    @Transactional
    public AccountTransaction refill(AccountTransaction accountTransaction){
        Account account_from = personDAO.getAccount(accountTransaction.getId_from());
        Account account_to = personDAO.getAccount(accountTransaction.getId_to());
        if (account_from.getStatus_account().equals("OPEN")){
            if (account_to.getStatus_account().equals("OPEN")){
                if (account_from.getBalance().compareTo(accountTransaction.getAmount()) >= 0 ){
                    personDAO.updateBalance(account_from, accountTransaction.getAmount(), "from");
                    personDAO.updateBalance(account_to, accountTransaction.getAmount(), "to");
                    return personDAO.newAccountTransaction(accountTransaction, "YES", "Operation was successful");
                }
                else
                    return personDAO.newAccountTransaction(accountTransaction, "NO",
                            "Operation failed, not enough money in the account");
            }
            else
                return personDAO.newAccountTransaction(accountTransaction, "NO", "Operation failed, account_to is close");
        }
        else
            return personDAO.newAccountTransaction(accountTransaction, "NO", "Operation failed, account_from is close");
    }

    public Person getPerson(int id_person){
        return personDAO.getPerson(id_person);
    }

    public Card getCard(int id){
        return personDAO.getCard(id);
    }

    public Card createNewCard(Card card){
        return personDAO.createNewCard(card);
    }

    public List<Card> getAllCards(){
        return personDAO.getAllCards();
    }

    public List<Card> getMyCards(int id_person){
        return personDAO.getMyCards(id_person);
    }

    public List<AccountTransaction> getAllAccountTransactions() {
        return personDAO.getAllAccountTransactions();
    }

    public BigDecimal getAccountBalance(int id) {
        return personDAO.getAccount(id).getBalance();
    }
}
