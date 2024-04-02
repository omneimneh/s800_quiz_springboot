package com.s800.quiz_springboot.models;

import java.util.UUID;

@lombok.Data
public class Client {
    UUID id;
    String name;
    String lastName;
    String mobile;
}
