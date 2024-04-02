package com.s800.quiz_springboot.controllers;

import com.s800.quiz_springboot.assemblers.ProductAssembler;
import com.s800.quiz_springboot.entities.ProductEntity;
import com.s800.quiz_springboot.models.Product;
import com.s800.quiz_springboot.models.ProductInput;
import com.s800.quiz_springboot.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.IanaLinkRelations.SELF;

@RestController
@RequiredArgsConstructor
public class ProductsController {
    private final ProductRepository repository;
    private final ModelMapper mapper;
    private final ProductAssembler assembler;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        var list = repository.findAll().stream().map(entity -> mapper.map(entity, Product.class)).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        var product = repository.findById(UUID.fromString(id)).map(entity -> mapper.map(entity, Product.class));
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    public ResponseEntity<Product> postProduct(@RequestBody ProductInput input) {
        var entity = repository.save(mapper.map(input, ProductEntity.class));
        var product = mapper.map(entity, Product.class);
        var link = assembler.toModel(product).getRequiredLink(SELF).toUri();
        return ResponseEntity.created(link).body(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> putProduct(@PathVariable String id, @RequestBody ProductInput input) {
        var existing = repository.findById(UUID.fromString(id));
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        mapper.map(input, existing.get());
        var entity = repository.save(existing.get());
        var product = mapper.map(entity, Product.class);
        return ResponseEntity.ok(product);
    }
}
