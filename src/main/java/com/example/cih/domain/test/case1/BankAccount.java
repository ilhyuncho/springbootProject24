package com.example.cih.domain.test.case1;


import lombok.Getter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
public class BankAccount extends BillingDetails{

    @NotNull
    private String account;
    @NotNull
    private String bankname;
    @NotNull
    private String swift;
    public BankAccount() {
    }

    public BankAccount(String owner, String account, String bankname, String swift) {
        super(owner);
        this.account = account;
        this.bankname = bankname;
        this.swift = swift;
    }

}
