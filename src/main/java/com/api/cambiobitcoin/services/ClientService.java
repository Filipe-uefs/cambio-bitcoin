package com.api.cambiobitcoin.services;

import com.api.cambiobitcoin.models.ClientModel;
import com.api.cambiobitcoin.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ClientModel getClientByCPF(String cpf) {
        return clientRepository.findByCpf(cpf);
    }
}
