package com.springboot.springbankingapplication.service;

import com.springboot.springbankingapplication.model.Account;
import com.springboot.springbankingapplication.model.User;
import com.springboot.springbankingapplication.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    // Create new account
    public Account createAccount(Long userId, String accountType, BigDecimal initialBalance) {
        User user = userService.findById(userId);

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(accountType);
        account.setBalance(initialBalance);
        account.setUser(user);

        return accountRepository.save(account);
    }

    // Get all accounts for a user
    public List<Account> getUserAccounts(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    // Deposit money
    public Account deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    // Withdraw money
    public Account withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    // Get account by number
    public Account getAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        return account;
    }

    // Generate unique account number
    private String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}