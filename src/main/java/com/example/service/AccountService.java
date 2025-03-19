package com.example.service;
import com.example.repository.*;
import com.example.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account register(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            return null;
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        if (!accountRepository.existsByUsername(account.getUsername())) {
            return null;
        }

        Account newAccount = accountRepository.getByUsername(account.getUsername());

        if (newAccount.getPassword().equals(account.getPassword())) {
            return newAccount;
        }

        return null;
    }
}