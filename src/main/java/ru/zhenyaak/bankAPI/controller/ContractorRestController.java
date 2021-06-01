package ru.zhenyaak.bankAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.zhenyaak.bankAPI.controller.exceptions.PersonNotFoundException;
import ru.zhenyaak.bankAPI.entity.Card;
import ru.zhenyaak.bankAPI.entity.Contractor;
import ru.zhenyaak.bankAPI.entity.Person;
import ru.zhenyaak.bankAPI.entity.Test;
import ru.zhenyaak.bankAPI.service.ContractorService;

import java.util.List;

@RestController
@RequestMapping("/contractor")
public class ContractorRestController {

    @Autowired
    private ContractorService contractorService;

//    http://localhost:8080/contractor/newcontractor
//    {
//        "name": "Sberbank",
//        "inn": "8888222200"
//    }
    @PostMapping("/newcontractor")
    public Contractor createNewContractor(@RequestBody Contractor contractor) {
        return contractorService.createNewContractor(contractor);
    }

//    http://localhost:8080/contractor/allcontractors
//    @GetMapping("/allcontractors")
//    public List<Card> allContractors() {
//        return contractorService.getAllCards();
//    }

    @GetMapping("/owner")
    public void createOwner(){
        contractorService.newOwner();
    }

    //    http://localhost:8080/contractor/id/6
    @GetMapping("/id/{id_contractor}")
    public Contractor getContractor(@PathVariable int id_contractor){
        Contractor contractor = contractorService.getContractor(id_contractor);
        System.out.println(contractor);
//        if (contractor.getId_contractor() == 0)
//            throw new PersonNotFoundException("Contractor with id_contractor = " + id_contractor + " not found");
        return contractor;
    }
}
