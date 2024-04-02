package com.s800.quiz_springboot.models;

import java.util.List;
import java.util.UUID;

@lombok.Data
public class Sale {
    UUID id;
    List<Transaction> transactions;
    float total;
    UUID clientId;
}
