package org.example.cartapi.api;

import org.example.cartapi.exception.NotFoundException;
import org.example.cartapi.model.Cart;
import org.example.cartapi.model.Product;
import org.example.cartapi.service.CartService;
import org.example.cartapi.service.ProductService;
import org.example.cartapi.service.input.Shipment;
import org.example.cartapi.service.output.ShipmentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cart", name = "Cart")
public class CartController {

    private CartService cartService;
    private ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable(required = true) long id) throws NotFoundException {
        return cartService.getCart(id);
    }

    @GetMapping
    public List<Cart> getCarts() {
        return cartService.getCarts();
    }

    @GetMapping("/{id}/products")
    public List<Product> getProducts(@PathVariable(required = true) long id) throws NotFoundException {
        return cartService.getCart(id).getProducts();
    }

    @PutMapping(path = "/product", consumes={MediaType.APPLICATION_JSON_VALUE})
    public Cart addProduct(@Valid @RequestBody Product product) {
        return cartService.addProduct(product);
    }

    @PutMapping(path = "/{id}/product", consumes={MediaType.APPLICATION_JSON_VALUE})
    public Cart addProduct(@PathVariable(name = "id", required = true) long id,
                           @Valid @RequestBody Product product) throws NotFoundException {
        return cartService.addProduct(id, product);
    }

    @DeleteMapping("/{id}/product/{productId}")
    public Cart removeProduct(@PathVariable(name = "id", required = true) long id,
                              @PathVariable(name = "productId", required = true) long productId) throws NotFoundException {
        return cartService.removeProduct(id, productId);
    }

    @PostMapping("/{id}/shipment")
    public ShipmentResult calculateShipment(@PathVariable(name = "id", required = true) long id,
                                            @Valid @RequestBody Shipment shipment) throws Exception {
        return cartService.calculateShipment(id, shipment);
    }
}
