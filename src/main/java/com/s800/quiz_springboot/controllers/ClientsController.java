package com.s800.quiz_springboot.controllers;

import com.s800.quiz_springboot.assemblers.ClientAssembler;
import com.s800.quiz_springboot.entities.ClientEntity;
import com.s800.quiz_springboot.models.Client;
import com.s800.quiz_springboot.models.ClientInput;
import com.s800.quiz_springboot.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.IanaLinkRelations.SELF;

@RestController
@RequiredArgsConstructor
public class ClientsController {
    private final ClientRepository repository;
    private final ModelMapper mapper;
    private final ClientAssembler assembler;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients() {
        var list = repository.findAll().stream().map(entity -> mapper.map(entity, Client.class)).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable String id) {
        var client = repository.findById(UUID.fromString(id)).map(entity -> mapper.map(entity, Client.class));
        return client.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> postClient(@RequestBody ClientInput input) {
        var entity = repository.save(mapper.map(input, ClientEntity.class));
        var client = mapper.map(entity, Client.class);
        var link = assembler.toModel(client).getRequiredLink(SELF).toUri();
        return ResponseEntity.created(link).body(client);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> putClient(@PathVariable String id, @RequestBody ClientInput input) {
        var existing = repository.findById(UUID.fromString(id));
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        mapper.map(input, existing.get());
        var entity = repository.save(existing.get());
        var client = mapper.map(entity, Client.class);
        return ResponseEntity.ok(client);
    }
}
