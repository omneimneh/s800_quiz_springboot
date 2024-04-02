package com.s800.quiz_springboot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
public class ProductEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
    private LocalDateTime creationDate = LocalDateTime.now();
}
