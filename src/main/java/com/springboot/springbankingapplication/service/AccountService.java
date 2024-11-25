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

    // Create new account
    public Account createAccount(User user) {
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    // Get user's accounts
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

    // Generate unique account number
    private String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        // Get both accounts
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);

        // Validate accounts exist
        if (fromAccount == null || toAccount == null) {
            throw new RuntimeException("One or both accounts not found");
        }

        // Check sufficient funds
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds for transfer");
        }

        // Perform transfer
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        // Save both accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
