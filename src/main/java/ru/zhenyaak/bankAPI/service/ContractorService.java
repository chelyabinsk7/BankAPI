package ru.zhenyaak.bankAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhenyaak.bankAPI.DAO.ContractorDAO;
import ru.zhenyaak.bankAPI.entity.Test;

import java.util.List;

@Service
public class ContractorService {

    private ContractorDAO contractorDAO;


    @Autowired
    public ContractorService (ContractorDAO theContractorDAO) {
        contractorDAO = theContractorDAO;
    }

    public List<Test> getTests() {
        return contractorDAO.findAll();
    }
}
