package com.ebgroup.BankProject.dao;

import com.ebgroup.BankProject.model.Account;
import com.ebgroup.BankProject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {


}
