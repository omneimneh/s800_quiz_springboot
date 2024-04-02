package com.s800.quiz_springboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private SaleEntity sale;
    @ManyToOne
    private ProductEntity product;

    private int quantity;
}
