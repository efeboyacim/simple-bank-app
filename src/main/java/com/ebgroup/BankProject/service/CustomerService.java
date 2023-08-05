package com.ebgroup.BankProject.service;

import com.ebgroup.BankProject.dao.AccountRepository;
import com.ebgroup.BankProject.dao.CustomerRepository;
import com.ebgroup.BankProject.model.Account;
import com.ebgroup.BankProject.model.Customer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findByID(int id){
        Optional<Customer> cus = customerRepository.findById(id);
        Customer tempCustomer = null;
        if (cus.isPresent()) {
            tempCustomer=cus.get();
        }
        else {
            throw new RuntimeException("Did not find the customer id: " + id);
        }
        return tempCustomer;
    }

    public Customer addCustomer(String fullName){
        Customer a = new Customer(fullName);
        customerRepository.save(a);
        return a;
    }

    public List<Account> showCustomersAccounts(int id){
        Customer myCustomer = this.findByID(id);
        List<Account> list = myCustomer.getAccountList();
        return list;
    }

}
