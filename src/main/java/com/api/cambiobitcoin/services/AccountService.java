package com.api.cambiobitcoin.services;

import com.api.cambiobitcoin.models.AccountModel;
import com.api.cambiobitcoin.models.ClientModel;
import com.api.cambiobitcoin.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountModel getAccountByClient(ClientModel client) {
        return accountRepository.findByClient(client);
    }
    @Transactional
    public void createAccount(AccountModel account) {
        accountRepository.save(account);
    }
    @Transactional
    public void deleteAccount(ClientModel client) {
        accountRepository.deleteByClient(client);
    }
    @Transactional
    public void updateCredit(AccountModel account, Double credit) {
        Double newBalance = account.getBalance() + credit;
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
    @Transactional
    public void updateCreditAndBtc(AccountModel account) {
        accountRepository.save(account);
    }
}
