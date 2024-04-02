package com.s800.quiz_springboot.assemblers;

import com.s800.quiz_springboot.controllers.ProductsController;
import com.s800.quiz_springboot.models.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    @NonNull
    public EntityModel<Product> toModel(@NonNull Product product) {
        return EntityModel.of(product,
                linkTo(methodOn(ProductsController.class).getProductById(product.getId().toString())).withSelfRel(),
                linkTo(methodOn(ProductsController.class).getProducts()).withRel("products"));
    }
}
