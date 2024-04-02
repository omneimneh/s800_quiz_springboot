package com.s800.quiz_springboot.models;

import java.time.LocalDateTime;
import java.util.UUID;

@lombok.Data
public class Product {
    UUID id;
    String name;
    String description;
    LocalDateTime creationDate;
}
