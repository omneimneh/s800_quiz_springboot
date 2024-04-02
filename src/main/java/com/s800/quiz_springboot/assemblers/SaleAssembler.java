package com.s800.quiz_springboot.assemblers;

import com.s800.quiz_springboot.controllers.SalesController;
import com.s800.quiz_springboot.models.Sale;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SaleAssembler implements RepresentationModelAssembler<Sale, EntityModel<Sale>> {

    @Override
    @NonNull
    public EntityModel<Sale> toModel(@NonNull Sale product) {
        return EntityModel.of(product,
                linkTo(methodOn(SalesController.class).getSaleById(product.getId().toString())).withSelfRel(),
                linkTo(methodOn(SalesController.class).getSales()).withRel("sales"));
    }
}
