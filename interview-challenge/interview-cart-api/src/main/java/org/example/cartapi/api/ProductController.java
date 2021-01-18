package org.example.cartapi.api;

import org.example.cartapi.exception.NotFoundException;
import org.example.cartapi.model.Product;
import org.example.cartapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
