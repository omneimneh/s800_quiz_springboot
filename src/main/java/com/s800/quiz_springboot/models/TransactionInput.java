package com.s800.quiz_springboot.models;

import java.util.UUID;

@lombok.Data
public class TransactionInput {
    UUID productId;
    int quantity;
}
