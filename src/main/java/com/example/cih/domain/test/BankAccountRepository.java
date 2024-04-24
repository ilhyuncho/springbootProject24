package com.example.cih.domain.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


public interface BankAccountRepository extends BillingDetailsRepository<BankAccount, Long>{

    List<BankAccount> findBySwift(String swift);

}
