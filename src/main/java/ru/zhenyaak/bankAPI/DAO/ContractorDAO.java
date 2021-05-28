package ru.zhenyaak.bankAPI.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.zhenyaak.bankAPI.entity.Test;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ContractorDAO {



    @Autowired
    private EntityManager entityManager;

    public List<Test> findAll(){
        Session currentSession = entityManager.unwrap(Session.class);
        List<Test> test = currentSession.createQuery( "FROM Test", Test.class).getResultList();
        return test;
    }

}
