package com.ebgroup.BankProject.service;

import com.ebgroup.BankProject.configuration.MQConfig;
import com.ebgroup.BankProject.dao.AccountRepository;
import com.ebgroup.BankProject.dao.DepositRequest;
import com.ebgroup.BankProject.model.Account;
import com.ebgroup.BankProject.model.Customer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;


    @Autowired
    private EntityManager entityManager;




    public List<Account> findAllAccounts(){
        log.info("All of the accounts listed.");
        return accountRepository.findAll();
    }

    public Account createAccount(int customerId, double balance, String currency){
        Customer cusForAccount = customerService.findByID(customerId);
        Account newAccount = new Account(cusForAccount,balance,currency);
        log.info("New account created with id: "+newAccount.getId());
        cusForAccount.getAccountList().add(newAccount);
        newAccount.setCustomer(customerService.findByID(customerId));
        accountRepository.save(newAccount);
        return newAccount;
    }

    public Account findAccountByID(int id){
        Optional<Account> account = accountRepository.findById(id);
        Account tempAccount = null;
        if (account.isPresent()) {
            tempAccount= account.get();
        }
        else {
            throw new RuntimeException("Did not find course id: " + id);
        }
        return tempAccount;
    }

    public Account withDrawMoney(double balance,int id){
        Account targetAccount = this.findAccountByID(id);
        targetAccount.setBalance(targetAccount.getBalance()-balance);
        accountRepository.save(targetAccount);
        return targetAccount;
    }








}
