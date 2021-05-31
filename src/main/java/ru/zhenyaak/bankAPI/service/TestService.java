package ru.zhenyaak.bankAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhenyaak.bankAPI.DAO.TestDAO;
import ru.zhenyaak.bankAPI.entity.Test;

import java.util.List;

@Service
public class TestService {

    @Autowired
    TestDAO testDAO;

    public List<Test> getTest(){
        return testDAO.getTest();
    }
}
