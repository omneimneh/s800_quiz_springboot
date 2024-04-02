package com.s800.quiz_springboot.models;

import java.util.UUID;

@lombok.Data
public class Transaction {
    UUID id;
    UUID productId;
    int quantity;
}
