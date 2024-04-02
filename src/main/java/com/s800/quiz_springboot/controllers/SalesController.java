package com.s800.quiz_springboot.controllers;

import com.s800.quiz_springboot.assemblers.SaleAssembler;
import com.s800.quiz_springboot.entities.SaleEntity;
import com.s800.quiz_springboot.entities.TransactionEntity;
import com.s800.quiz_springboot.models.Sale;
import com.s800.quiz_springboot.models.SaleInput;
import com.s800.quiz_springboot.models.TransactionInput;
import com.s800.quiz_springboot.repositories.ClientRepository;
import com.s800.quiz_springboot.repositories.ProductRepository;
import com.s800.quiz_springboot.repositories.SaleRepository;
import com.s800.quiz_springboot.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.IanaLinkRelations.SELF;

@RestController
@RequiredArgsConstructor
public class SalesController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SalesController.class);

    private final SaleRepository repository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper mapper;
    private final SaleAssembler assembler;

    @GetMapping("/sales")
    public ResponseEntity<List<Sale>> getSales() {
        var list = repository.findAll().stream().map(entity -> mapper.map(entity, Sale.class)).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable String id) {
        var sale = repository.findById(UUID.fromString(id)).map(entity -> mapper.map(entity, Sale.class));
        return sale.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sales")
    public ResponseEntity<Sale> postSale(@RequestBody SaleInput input) {
        var client = clientRepository.findById(input.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client with ID " + input.getClientId() + " not found"));

        SaleEntity sale = new SaleEntity();
        sale.setTotal(input.getTotal());
        sale.setClient(client);

        saveSaleTransactions(input.getTransactions(), sale);

        Sale created = mapper.map(sale, Sale.class);
        return ResponseEntity.created(assembler.toModel(created).getRequiredLink(SELF).toUri())
                .body(created);
    }

    @PutMapping("/sales/{id}")
    public ResponseEntity<Sale> putSale(@PathVariable String id, @RequestBody SaleInput input) {
        var existing = repository.findById(UUID.fromString(id));
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var sale = existing.get();

        var client = clientRepository.findById(input.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client with ID " + input.getClientId() + " not found"));

        sale.setTotal(input.getTotal());
        sale.setClient(client);

        sale.getTransactions().clear();
        saveSaleTransactions(input.getTransactions(), sale);

        return ResponseEntity.ok(mapper.map(sale, Sale.class));
    }

    private void saveSaleTransactions(List<TransactionInput> transactions, SaleEntity sale) {
        for (var transaction : transactions) {
            var product = productRepository.findById(transaction.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product with ID " + transaction.getProductId() + " not found"));

            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setProduct(product);
            transactionEntity.setQuantity(transaction.getQuantity());

            transactionRepository.save(transactionEntity);

            LOGGER.info("added transaction of [productId: {}, quantity: {}], for sale with id {}",
                    transaction.getProductId(), transaction.getQuantity(), sale.getId());

            transactionEntity.setSale(sale);
            sale.getTransactions().add(transactionEntity);
        }

        repository.save(sale);
    }
}
