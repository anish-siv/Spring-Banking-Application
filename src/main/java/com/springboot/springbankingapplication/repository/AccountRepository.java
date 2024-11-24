package com.springboot.springbankingapplication.repository;

import com.springboot.springbankingapplication.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Find all accounts for a specific user
    List<Account> findByUserId(Long userId);

    // Find account by account number
    Account findByAccountNumber(String accountNumber);

    // Check if account number exists
    boolean existsByAccountNumber(String accountNumber);
}
