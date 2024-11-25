package com.springboot.springbankingapplication.controller;

import com.springboot.springbankingapplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> transfer(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam BigDecimal amount) {
        try {
            accountService.transfer(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok("Transfer successful!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
