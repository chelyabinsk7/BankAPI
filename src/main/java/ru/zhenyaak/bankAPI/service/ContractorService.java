package ru.zhenyaak.bankAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyaak.bankAPI.DAO.ContractorDAO;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Contractor;
import ru.zhenyaak.bankAPI.entity.Test;

import java.util.List;

@Service
public class ContractorService {

    @Autowired
    private ContractorDAO contractorDAO;

    @Transactional
    public Contractor createNewContractor(Contractor contractor) {
        int id_owner = contractorDAO.createNewOwner();
        contractorDAO.createNewContractor(contractor, id_owner);
        return contractorDAO.getContractor(id_owner);
    }

    public int newOwner(){
        return contractorDAO.createNewOwner();
    }

    public Contractor getContractor(int id_contractor) {
        Contractor contractor = contractorDAO.getContractor(id_contractor);
        return contractor;
    }
}
