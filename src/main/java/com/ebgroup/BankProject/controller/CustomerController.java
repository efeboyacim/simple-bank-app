package com.ebgroup.BankProject.controller;

import com.ebgroup.BankProject.model.Account;
import com.ebgroup.BankProject.model.Customer;
import com.ebgroup.BankProject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    private ResponseEntity<List<Customer>> showAllCustomers(){
        List<Customer> list = customerService.findAllCustomers();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    private ResponseEntity<String> createCustomer(@RequestParam String fullName){
        Customer newCustomer = customerService.addCustomer(fullName);
        return ResponseEntity.ok().body("New customer created with id: "+ newCustomer.getId());
    }

    //Show the Customer's accounts according to customer id
    @GetMapping("/accounts/{id}")
    private ResponseEntity<List<Account>> showCustomerAccounts(@PathVariable int id){
        return ResponseEntity.ok(customerService.showCustomersAccounts(id));
    }







}
