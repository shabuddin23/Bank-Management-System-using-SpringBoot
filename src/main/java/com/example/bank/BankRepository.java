package com.example.bank;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Optional<Bank> findByAccountNo(String accountNo);

}