package org.nexus.nexkartbackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nexus.nexkartbackend.Repository.ProductRepository;
import org.nexus.nexkartbackend.Repository.ShippingRateRepository;
import org.nexus.nexkartbackend.ShippingRate.ShippingRateService;
import org.nexus.nexkartbackend.entity.Product;
import org.nexus.nexkartbackend.entity.ShippingRate;
import org.nexus.nexkartbackend.exception.ShippingRateNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ShippingRateServiceTests {

    @MockitoBean
    private ShippingRateRepository shipRepo;

    @MockitoBean
    private ProductRepository productRepo;

    @InjectMocks
    private ShippingRateService shipService;

    @Test
    public void testCalculateShippingCost_NoRateFound() {
        Integer productId = 1;
        Integer countryId = 1;
        String state = "Maharashtra";

        Mockito.when(shipRepo.findByCountryAndState(countryId, state)).thenReturn(null);

        assertThrows(ShippingRateNotFoundException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                shipService.calculateShippingCost(productId, countryId, state);
            }
        });

    }

    @Test
    public void testCalculateShippingCost_RateFound() throws ShippingRateNotFoundException {

        Integer productId = 7;
        Integer countryId = 1;
        String state = "New York";

        ShippingRate shippingRate = new ShippingRate();
        shippingRate.setRate(10);

        Mockito.when(shipRepo.findByCountryAndState(countryId, state)).thenReturn(shippingRate);

        Product product = new Product();
        product.setWeight(5);
        product.setWidth(4);
        product.setHeight(3);
        product.setLength(8);

        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));

        float shippingCost = shipService.calculateShippingCost(productId, countryId, state);

        assertEquals(50, shippingCost);
    }
}