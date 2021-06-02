package ru.zhenyaak.bankAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyaak.bankAPI.DAO.ContractorDAO;
import ru.zhenyaak.bankAPI.DAO.EmployeeDAO;
import ru.zhenyaak.bankAPI.entity.*;

import java.sql.SQLException;
import java.util.List;

@Service
public class ContractorService {

    private final ContractorDAO contractorDAO;

    @Autowired
    public ContractorService (ContractorDAO contractorDAO){
        this.contractorDAO = contractorDAO;
    }

    public Contractor createNewContractor(Contractor contractor) {
        return contractorDAO.getContractor(contractorDAO.createNewContractor(contractor));
    }

    public Contractor getContractor(int id_contractor) {
        return contractorDAO.getContractor(id_contractor);
    }

    public List<Contractor> getAllContractors() {
        return contractorDAO.getAllContractors();
    }

    public AccountTransaction refill(AccountTransaction accountTransaction){
        return contractorDAO.refill(accountTransaction);
    }
}
