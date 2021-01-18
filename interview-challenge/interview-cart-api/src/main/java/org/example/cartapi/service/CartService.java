package org.example.cartapi.service;

import br.com.techzee.correios.ws.ConsultaCorreios;
import br.com.techzee.correios.ws.dto.CorreiosPrecoPrazo;
import com.google.common.util.concurrent.AtomicDouble;
import org.example.cartapi.exception.NotFoundException;
import org.example.cartapi.model.Cart;
import org.example.cartapi.model.Product;
import org.example.cartapi.persistence.CartRepository;
import org.example.cartapi.service.input.Shipment;
import org.example.cartapi.service.output.ShipmentResult;
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

    /**
     * Add received product to a new cart.
     * Note that you have to save the product and cart to repository
     * @param productReceived Product to be inserted in the cart
     * @return the new cart created in the repository
     */
    public Cart addProduct(Product productReceived) {
        //TODO
        return null;
    }

    /**
     * Add received product to a new cart.
     * Note that you have to save the product and cart to repository
     * @param id Identifier of the cart to insert the product
     * @param productReceived Product to be inserted in the cart
     * @return the cart retrieved from repository
     * @throws NotFoundException
     */
    public Cart addProduct(long id, Product productReceived) throws NotFoundException {
        //TODO
        return null;
    }

    /**
     * Remove the specified product from specified cart
     * @param id Identifier of the cart to remove the product from
     * @param productId Identifier of the product to be removed
     * @return the cart retrieved from repository
     * @throws NotFoundException
     */
    public Cart removeProduct(long id, long productId) throws NotFoundException {
        //TODO
        return null;
    }

    /**
     * Calculates the shipment values and deadlines, based on specified cart, destination and type of delivery service
     * @param id Identifier of the cart to calculate the shipment
     * @param shipment Shipment metadata
     * @return The result of shipment calculation
     * @throws Exception
     */
    public ShipmentResult calculateShipment(long id, Shipment shipment) throws Exception {
        Cart cart = getCart(id);

        AtomicDouble totalWeight = new AtomicDouble(1);
        cart.getProducts().stream().forEach(p -> {
                totalWeight.addAndGet(p.getWeight());
        });
        CorreiosPrecoPrazo result = new ConsultaCorreios().servicos(shipment.getServiceType())
                .peso(totalWeight.get())
                .valorAdicionalDeclarado(0.0)
                .calcularPrecoPrazo("81270050", shipment.getCep())[0];

        ShipmentResult shipmentResult = new ShipmentResult();

        shipmentResult.setDeadline(result.getPrazoEntrega());
        shipmentResult.setValue(result.getPrecoFrete());
        shipmentResult.setTotalWeight(totalWeight.get());

        return shipmentResult;
    }

    /**
     * Update the total value of cart using the sum values from products in the cart
     * @param cart
     */
    protected void updateTotal(Cart cart) {
        //TODO
    }
}
