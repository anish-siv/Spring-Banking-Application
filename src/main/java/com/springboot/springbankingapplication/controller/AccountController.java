package com.springboot.springbankingapplication.controller;

import com.springboot.springbankingapplication.model.Account;
import com.springboot.springbankingapplication.service.AccountService;
import com.springboot.springbankingapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    // Create new account
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createAccount(@PathVariable Long userId) {
        try {
            Account account = accountService.createAccount(userService.findById(userId));
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get user's accounts
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserAccounts(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getUserAccounts(userId));
    }

    // Deposit money
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount) {
        try {
            Account account = accountService.deposit(accountNumber, amount);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Withdraw money
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount) {
        try {
            Account account = accountService.withdraw(accountNumber, amount);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
