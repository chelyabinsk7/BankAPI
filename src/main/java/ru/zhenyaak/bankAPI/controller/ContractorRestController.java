package ru.zhenyaak.bankAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.zhenyaak.bankAPI.entity.*;
import ru.zhenyaak.bankAPI.service.ContractorService;

import java.util.List;

@RestController
@RequestMapping("/contractor")
public class ContractorRestController {

    private final ContractorService contractorService;

    @Autowired
    public ContractorRestController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

/*
http://localhost:8080/contractor/newcontractor
{
    "name": "Sberbank",
    "inn": "8888222200"
}
*/
    @PostMapping("/newcontractor")
    public Contractor createNewContractor(@RequestBody Contractor contractor) {
        return contractorService.createNewContractor(contractor);
    }

/*
http://localhost:8080/contractor/allcontractors
*/
    @GetMapping("/allcontractors")
    public List<Contractor> allContractors() {
        return contractorService.getAllContractors();
    }

/*
http://localhost:8080/contractor/id/6
*/
    @GetMapping("/id/{id_contractor}")
    public Contractor getContractorById(@PathVariable int id_contractor){
        return contractorService.getContractorById(id_contractor);
    }

/*
http://localhost:8080/contractor/refill
{
    "id_from": 8,
    "id_to": 10,
    "amount": "100.9"
}
*/
    @PostMapping("/refill")
    public AccountTransaction refill(@RequestBody AccountTransaction accountTransaction){
        return contractorService.refill(accountTransaction);
    }

}
