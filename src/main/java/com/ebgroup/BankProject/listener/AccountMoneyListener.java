package com.ebgroup.BankProject.listener;

import com.ebgroup.BankProject.configuration.MQConfig;
import com.ebgroup.BankProject.dao.DepositRequest;
import com.ebgroup.BankProject.model.Account;
import com.ebgroup.BankProject.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountMoneyListener {

    @Autowired
    private AccountService accountService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void transferMoneyCheck(DepositRequest depositRequest){
        accountService.withDrawMoney(depositRequest.getBalance(),depositRequest.getAccountID());
        log.info(depositRequest.getAccountID() + " withdrawed money: "+ depositRequest.getBalance());
    }


}
