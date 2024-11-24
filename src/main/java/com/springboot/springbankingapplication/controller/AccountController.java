package com.springboot.springbankingapplication.controller;

import com.springboot.springbankingapplication.model.Account;
import com.springboot.springbankingapplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    // Create account
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(
            @RequestParam Long userId,
            @RequestParam String accountType,
            @RequestParam BigDecimal initialBalance) {
        try {
            Account account = accountService.createAccount(userId, accountType, initialBalance);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get user's accounts
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserAccounts(@PathVariable Long userId) {
        try {
            List<Account> accounts = accountService.getUserAccounts(userId);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Deposit
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount) {
        try {
            Account account = accountService.deposit(accountNumber, amount);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Withdraw
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount) {
        try {
            Account account = accountService.withdraw(accountNumber, amount);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
