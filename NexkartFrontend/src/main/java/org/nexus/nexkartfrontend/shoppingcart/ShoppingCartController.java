package org.nexus.nexkartfrontend.shoppingcart;
import java.util.List;


import jakarta.servlet.http.HttpServletRequest;
import org.nexus.nexkartfrontend.Utility;
import org.nexus.nexkartfrontend.address.Address;
import org.nexus.nexkartfrontend.address.AddressService;
import org.nexus.nexkartfrontend.customer.Customer;
import org.nexus.nexkartfrontend.customer.CustomerService;
import org.nexus.nexkartfrontend.entity.ShippingRate;
import org.nexus.nexkartfrontend.shipping.ShippingRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ShoppingCartController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShippingRateService shipService;

    @GetMapping("/cart")
    public String viewCart(Model model, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);
        List<CartItem> cartItems = cartService.listCartItems(customer);

        float estimatedTotal = 0.0F;
        float totalSavings = 0.0F;


        for (CartItem item : cartItems) {
            estimatedTotal += item.getSubtotal();
        }

        for (CartItem item : cartItems) {

            float originalPrice = item.getProduct().getPrice();
            float discountPrice = item.getProduct().getDiscountPrice();
            int quantity = item.getQuantity();
            totalSavings += (originalPrice - discountPrice) * quantity;

        }

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;
        boolean usePrimaryAddressAsDefault = false;

        if (defaultAddress != null) {
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        } else {
            usePrimaryAddressAsDefault = true;
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }

        model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
        model.addAttribute("shippingSupported", shippingRate != null);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("estimatedTotal", estimatedTotal);
        model.addAttribute("totalSavings", totalSavings);


        return "cart/shopping_cart";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

}