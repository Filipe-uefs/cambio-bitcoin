package com.api.cambiobitcoin.repositories;

import com.api.cambiobitcoin.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
    ClientModel findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    void deleteByCpf(String cpf);
}
