package com.api.cambiobitcoin.services;

import com.api.cambiobitcoin.models.AccountModel;
import com.api.cambiobitcoin.models.ClientModel;
import com.api.cambiobitcoin.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired AccountService accountService;

    public ClientModel getClientByCPF(String cpf) {
        return clientRepository.findByCpf(cpf);
    }

    public boolean existsClientByCpf(String cpf) {
        return clientRepository.existsByCpf(cpf);
    }

    @Transactional
    public ClientModel createClient(ClientModel client) {
        ClientModel newClient  = clientRepository.save(client);
        AccountModel newAccount = new AccountModel();
        newAccount.setClient(newClient);
        newAccount.setBalance((double) 0);
        newAccount.setQtdBitcoin(BigDecimal.valueOf(0));
        accountService.createAccount(newAccount);
        return newClient;
    }

    @Transactional
    public void deleteClientByCPF(String cpf) {
        accountService.deleteAccount(getClientByCPF(cpf));
        clientRepository.deleteByCpf(cpf);
    }
}
