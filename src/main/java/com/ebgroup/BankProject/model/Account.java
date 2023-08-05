package com.ebgroup.BankProject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="account")
public class Account {

    public Account(Customer customer, double balance, String currency) {
        this.customer = customer;
        this.balance = balance;
        this.currency = currency;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;


    @JsonManagedReference
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REFRESH})
    @JoinColumn(name="customerID")
    private Customer customer;

    @Column(name="balance")
    private double balance;

    @Column(name="currency")
    private String currency;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customer=" + customer +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                '}';
    }
}
