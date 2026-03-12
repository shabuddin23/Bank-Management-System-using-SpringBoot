package com.example.bank;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    BankRepository BankRepository;

    // Create Account
    @PostMapping("/Banks")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        try {
            Bank savedBank = BankRepository.save(bank);
            return new ResponseEntity<>(savedBank, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // View Balance
    @GetMapping("/Banks/{accountNo}")
    public ResponseEntity<String> viewBalance(@PathVariable String accountNo) {

        Optional<Bank> bank = BankRepository.findByAccountNo(accountNo);

        if (bank.isPresent()) {
            String message = "Your name is " + bank.get().getName() +
                    "\nYour balance is " + bank.get().getBalance();
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update Account
    @PutMapping("/Banks/{accountNo}")
    public ResponseEntity<Bank> updateBank(@PathVariable String accountNo, @RequestBody Bank bankDetails) {

        Optional<Bank> bank = BankRepository.findByAccountNo(accountNo);

        if (bank.isPresent()) {

            Bank existingBank = bank.get();

            existingBank.setName(bankDetails.getName());
            existingBank.setBalance(bankDetails.getBalance());

            Bank updatedBank = BankRepository.save(existingBank);

            return ResponseEntity.ok(updatedBank);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Account
    @DeleteMapping("/Banks/{accountNo}")
    public ResponseEntity<String> deleteBank(@PathVariable String accountNo) {

        Optional<Bank> bank = BankRepository.findByAccountNo(accountNo);

        if (bank.isPresent()) {
            BankRepository.delete(bank.get());
            return ResponseEntity.ok("Account deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}