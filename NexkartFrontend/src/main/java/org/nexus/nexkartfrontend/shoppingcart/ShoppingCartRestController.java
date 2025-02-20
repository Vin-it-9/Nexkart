package org.nexus.nexkartfrontend.shoppingcart;

import jakarta.servlet.http.HttpServletRequest;
import org.nexus.nexkartfrontend.Utility;
import org.nexus.nexkartfrontend.customer.Customer;
import org.nexus.nexkartfrontend.customer.CustomerNotFoundException;
import org.nexus.nexkartfrontend.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartRestController {

    @Autowired private
    ShoppingCartService cartService;

    @Autowired private
    CustomerService customerService;

    @PostMapping("/cart/add/{productId}/{quantity}")
    public String addProductToCart(@PathVariable("productId") Integer productId,
                                   @PathVariable("quantity") Integer quantity, HttpServletRequest request) {

        try {
            Customer customer = getAuthenticatedCustomer(request);
            Integer updatedQuantity = cartService.addProduct(productId, quantity, customer);

            return updatedQuantity + " item(s) of this product were added to your shopping cart.";
        } catch (CustomerNotFoundException ex) {
            return "You must login to add this product to cart.";
        } catch (ShoppingCartException ex) {
            return ex.getMessage();
        }

    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request)
            throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No authenticated customer");
        }

        return customerService.getCustomerByEmail(email);
    }
}