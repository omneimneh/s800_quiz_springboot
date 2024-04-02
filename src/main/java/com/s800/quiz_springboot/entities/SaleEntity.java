package com.s800.quiz_springboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
public class SaleEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime creationDate = LocalDateTime.now();
    private float total;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions = new ArrayList<>();

    @ManyToOne
    private ClientEntity client;
}
