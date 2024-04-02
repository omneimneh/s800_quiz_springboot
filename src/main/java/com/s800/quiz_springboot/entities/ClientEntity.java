package com.s800.quiz_springboot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Entity
public class ClientEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String lastName;
    private String mobile;
}
