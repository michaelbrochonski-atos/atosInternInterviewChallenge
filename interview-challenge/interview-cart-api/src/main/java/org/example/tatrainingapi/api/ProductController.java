package org.example.tatrainingapi.api;

import org.example.tatrainingapi.exception.NotFoundException;
import org.example.tatrainingapi.model.Product;
import org.example.tatrainingapi.service.CartService;
import org.example.tatrainingapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/product",name = "Products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable(required = true) long id) throws NotFoundException {
        return productService.getProduct(id);
    }

    @PatchMapping(path = "/{id}", consumes={MediaType.APPLICATION_JSON_VALUE})
    public void patchProduct(@PathVariable(required = true) long id, Map.Entry<String, Integer> entry) {
        productService.updateProduct(id, entry);
    }
}
