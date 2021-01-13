package org.example.tatrainingapi.service;

import br.com.techzee.correios.ws.ConsultaCorreios;
import br.com.techzee.correios.ws.dto.CorreiosPrecoPrazo;
import com.google.common.util.concurrent.AtomicDouble;
import org.example.tatrainingapi.exception.NotFoundException;
import org.example.tatrainingapi.model.Cart;
import org.example.tatrainingapi.model.Product;
import org.example.tatrainingapi.persistence.CartRepository;
import org.example.tatrainingapi.persistence.ProductRepository;
import org.example.tatrainingapi.service.input.Shipment;
import org.example.tatrainingapi.service.output.ShipmentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    public Cart getCart(long id) throws NotFoundException {
        Optional<Cart> optional = cartRepository.findById(id);
        return optional.orElseThrow(() -> new NotFoundException("Cart Id:" + id + " not found"));
    }

    public Cart createCart() {
        return cartRepository.save(new Cart());
    }

    public Cart addProduct(Product productReceived) {
        Cart cart = cartRepository.save(new Cart());
        productReceived.setCart(cart);
        Product product = productService.saveProduct(productReceived);
        cart.getProducts().add(product);
        updateTotal(cart);
        return cartRepository.save(cart);
    }

    public Cart addProduct(long id, Product productReceived) throws NotFoundException {
        Cart cart = getCart(id);
        productReceived.setCart(cart);
        Product product = productService.saveProduct(productReceived);
        cart.getProducts().add(product);
        updateTotal(cart);
        return cartRepository.save(cart);
    }

    public Cart removeProduct(long id, long productId) throws NotFoundException {
        Cart cart = getCart(id);
        Product product = productService.getProduct(productId);

        cart.getProducts().remove(product);
        updateTotal(cart);
        return cartRepository.save(cart);
    }

    public ShipmentResult calculateShipment(long id, Shipment shipment) throws Exception {
        Cart cart = getCart(id);

        AtomicDouble totalWeight = new AtomicDouble(1);
        cart.getProducts().stream().forEach(p -> {
                totalWeight.addAndGet(p.getWeight());
        });
        CorreiosPrecoPrazo result = new ConsultaCorreios().servicos(shipment.getServiceType())
                .peso(totalWeight.get())
                .valorAdicionalDeclarado(0.0)
                .calcularPrecoPrazo("80215432", shipment.getCep())[0];

        ShipmentResult shipmentResult = new ShipmentResult();

        shipmentResult.setDeadline(result.getPrazoEntrega());
        shipmentResult.setValue(result.getPrecoFrete());
        shipmentResult.setTotalWeight(totalWeight.get());

        return shipmentResult;
    }

    protected void updateTotal(Cart cart) {
        AtomicDouble total = new AtomicDouble(0);
        cart.getProducts().stream().forEach(p -> {
            total.addAndGet(p.getValue());
        });
        cart.setTotal(total.get());
    }
}
