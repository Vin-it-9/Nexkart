package org.nexus.nexkartfrontend.checkout;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.nexus.nexkartfrontend.Utility;
import org.nexus.nexkartfrontend.address.Address;
import org.nexus.nexkartfrontend.address.AddressService;
import org.nexus.nexkartfrontend.customer.Customer;
import org.nexus.nexkartfrontend.customer.CustomerService;
import org.nexus.nexkartfrontend.entity.ShippingRate;
import org.nexus.nexkartfrontend.order.Order;
import org.nexus.nexkartfrontend.order.OrderService;
import org.nexus.nexkartfrontend.order.PaymentMethod;
import org.nexus.nexkartfrontend.setting.CurrencySettingBag;
import org.nexus.nexkartfrontend.setting.EmailSettingBag;
import org.nexus.nexkartfrontend.setting.SettingService;
import org.nexus.nexkartfrontend.shipping.ShippingRateService;
import org.nexus.nexkartfrontend.shoppingcart.CartItem;
import org.nexus.nexkartfrontend.shoppingcart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShippingRateService shipService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SettingService settingService;


    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;

        if (defaultAddress != null) {
            model.addAttribute("shippingAddress", defaultAddress.toString());
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        } else {
            model.addAttribute("shippingAddress", customer.toString());
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }

        if (shippingRate == null) {
            return "redirect:/cart";
        }

        List<CartItem> cartItems = cartService.listCartItems(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);

        model.addAttribute("checkoutInfo", checkoutInfo);
        model.addAttribute("cartItems", cartItems);

        return "checkout/checkout";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping("/place_order")
    public String placeOrder(HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {

        String paymentType = request.getParameter("paymentMethod");
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentType);

        Customer customer = getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;

        if (defaultAddress != null) {
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        } else {
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }

        List<CartItem> cartItems = cartService.listCartItems(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);

        Order createdOrder = orderService.createOrder(customer, defaultAddress, cartItems, paymentMethod, checkoutInfo);
        cartService.deleteByCustomer(customer);
        sendOrderConfirmationEmail(request, createdOrder);

        return "checkout/order_completed";

    }

    private void sendOrderConfirmationEmail(HttpServletRequest request, Order order)
            throws UnsupportedEncodingException, MessagingException {

        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
        mailSender.setDefaultEncoding("utf-8");

        String toAddress = order.getCustomer().getEmail();
        String subject = emailSettings.getOrderConfirmationSubject();
        String content = emailSettings.getOrderConfirmationContent();

        subject = subject.replace("[[orderId]]", String.valueOf(order.getId()));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        DateFormat dateFormatter =  new SimpleDateFormat("HH:mm:ss E, dd MMM yyyy");
        String orderTime = dateFormatter.format(order.getOrderTime());

        CurrencySettingBag currencySettings = settingService.getCurrencySettings();
        String totalAmount = Utility.formatCurrency(order.getTotal(), currencySettings);

        content = content.replace("[[name]]", order.getCustomer().getFullName());
        content = content.replace("[[orderId]]", String.valueOf(order.getId()));
        content = content.replace("[[orderTime]]", orderTime);
        content = content.replace("[[shippingAddress]]", order.getShippingAddress());
        content = content.replace("[[total]]", totalAmount);
        content = content.replace("[[paymentMethod]]", order.getPaymentMethod().toString());

        helper.setText(content, true);

        mailSender.send(message);

    }


}