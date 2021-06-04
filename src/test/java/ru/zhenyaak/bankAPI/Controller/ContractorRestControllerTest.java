package ru.zhenyaak.bankAPI.Controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.zhenyaak.bankAPI.DAO.ContractorDAO;
import ru.zhenyaak.bankAPI.controller.ContractorRestController;
import ru.zhenyaak.bankAPI.entity.Account;
import ru.zhenyaak.bankAPI.entity.Contractor;
import ru.zhenyaak.bankAPI.service.ContractorService;
import ru.zhenyaak.bankAPI.service.EmployeeService;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
//@AutoConfigureMockMvc
//@WebMvcTest(ContractorRestController.class)
public class ContractorRestControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ContractorDAO contractorDAO;

    @Test
    public void test1() throws Exception {
//        List<Contractor> list = new ArrayList<>();
//        list.add(new Contractor(6, "Granit", "1234567890"));
//        list.add(new Contractor(7, "IBS", "5674209809"));
//        list.add(new Contractor(8, "COFF", "1734507820"));
//        when(contractorDAO.getAllContractors()).thenReturn(list);
//        mockMvc.perform(get("/allcontractors")).andExpect(status().isOk());
    }



//    @Autowired
//    private ContractorDAO contractorDAO;

//    @Test
//    public void contextLoads(){
//
//    }
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    public void getContractorById() throws Exception{
//        this.mvc.perform(get("http://localhost:8080/contractor/id/6"))
////                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void test1(){
//        assertTrue(1 < 9);
//    }

//    private DataSource dataSource;
//
//    @Before
//    public void setup(){
//        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
//                .addScript("classpath:schema.sql")
//                .addScript("classpath:data-test.sql")
//                .build();
//    }

//    @Test
//    public void test1(){
//        ContractorDAO cd = new ContractorDAO(dataSource);
//        ContractorService cs = new ContractorService(cd);
//        ContractorRestController cr = new ContractorRestController(cs);
//        System.out.println("--------------" + cr.getContractorById(6));
//        assertTrue(8 < 19);
//    }
//
//    @Test
//    public void test2() throws Exception{
//        this.mvc.perform(get("http://localhost:8080/contr9actor/id/6")).andExpect(status().isNotFound());
//    }

//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @MockBean
//    private EmployeeService employeeServiceMock;
//
//    @Before
//    public void setUp() {
//        this.mockMvc = webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Test
//    public void should_CreateAccount_When_ValidRequest() throws Exception {
//
////        when(employeeServiceMock.createNewAccount(any(Account.class))).thenReturn();
//
//        mockMvc.perform(post("/employee/newaccount")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{ \"number\": \"44442222888855551100\", \"id_owner\": 1 }")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
////                .andExpect(header().string("Location", "/api/account/12345"))
//                .andExpect(jsonPath("$.id").value("11"));
////                .andExpect(jsonPath("$.accountType").value("SAVINGS"))
////                .andExpect(jsonPath("$.balance").value(5000));
//    }
}
