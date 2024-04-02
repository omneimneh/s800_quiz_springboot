package com.s800.quiz_springboot.models;

import java.util.List;
import java.util.UUID;

@lombok.Data
public class SaleInput {
    List<TransactionInput> transactions;
    float total;
    UUID clientId;
}
