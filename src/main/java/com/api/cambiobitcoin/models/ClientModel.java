package com.api.cambiobitcoin.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "clients")
@NoArgsConstructor
public class ClientModel implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Getter private UUID id;
    @Column(nullable = false)
    @Getter @Setter private String fullName;
    @Column(nullable = false, unique = true)
    @Getter @Setter private String cpf;
}
