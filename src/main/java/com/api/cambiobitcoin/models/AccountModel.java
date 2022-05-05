package com.api.cambiobitcoin.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Mod10Check;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private UUID id;
    @OneToOne
    @JoinColumn(name = "client_id")
    @Getter @Setter
    private ClientModel client;
    @Getter @Setter
    private Double balance;
    @Getter @Setter
    private BigDecimal qtdBitcoin;
}
