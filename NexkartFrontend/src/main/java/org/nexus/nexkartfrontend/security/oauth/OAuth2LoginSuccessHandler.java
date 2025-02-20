package org.nexus.nexkartfrontend.security.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nexus.nexkartfrontend.customer.Customer;
import org.nexus.nexkartfrontend.customer.CustomerService;
import org.nexus.nexkartfrontend.entity.AuthenticationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private CustomerService customerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomerOAuth2User oauth2User = (CustomerOAuth2User) authentication.getPrincipal();

        String name = oauth2User.getName();
        String email = oauth2User.getEmail();
        String countryCode = request.getLocale().getCountry();

        System.out.println("OAuth2LoginSuccessHandler: " + name + " | " + email);

        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            customerService.addNewCustomerUponOAuthLogin(name, email, countryCode);
        } else {
            oauth2User.setFullName(customer.getFullName());
            customerService.updateAuthenticationType(customer, AuthenticationType.GOOGLE);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}