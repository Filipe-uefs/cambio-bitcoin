package com.api.cambiobitcoin.repositories;

import com.api.cambiobitcoin.models.AccountModel;
import com.api.cambiobitcoin.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, UUID> {
    void deleteByClient(ClientModel client);

    AccountModel findByClient(ClientModel client);
}
