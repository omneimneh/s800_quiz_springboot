package com.s800.quiz_springboot.assemblers;

import com.s800.quiz_springboot.controllers.ClientsController;
import com.s800.quiz_springboot.models.Client;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClientAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>> {

    @Override
    @NonNull
    public EntityModel<Client> toModel(@NonNull Client product) {
        return EntityModel.of(product,
                linkTo(methodOn(ClientsController.class).getClientById(product.getId().toString())).withSelfRel(),
                linkTo(methodOn(ClientsController.class).getClients()).withRel("clients"));
    }
}
