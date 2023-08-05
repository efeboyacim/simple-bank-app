package com.ebgroup.BankProject.controller;

import com.ebgroup.BankProject.configuration.MQConfig;
import com.ebgroup.BankProject.dao.DepositRequest;
import com.ebgroup.BankProject.model.Account;
import com.ebgroup.BankProject.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AmqpTemplate template;

    @GetMapping("/list")
    private ResponseEntity<List<Account>> showAll(){
        List<Account> list = accountService.findAllAccounts();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    private ResponseEntity<String> createAccount(@RequestParam int customerId, @RequestParam double balance, @RequestParam String currency){
        Account newAcc = accountService.createAccount(customerId,balance,currency);
        return ResponseEntity.ok()
                .body("New account created with id: "+ newAcc.getId());
    }


    @PutMapping("/withdraw/{accountID}/{balance}")
    private void withDrawMoney(@PathVariable int accountID, @PathVariable double balance, DepositRequest depositRequest){
        depositRequest.setAccountID(accountID);
        depositRequest.setBalance(balance);
        Account myAccount = accountService.findAccountByID(accountID);
        if(myAccount.getBalance() > balance) {
            template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, depositRequest);
        }
    }



}
