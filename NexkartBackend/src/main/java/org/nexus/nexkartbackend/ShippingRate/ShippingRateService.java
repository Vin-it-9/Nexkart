package org.nexus.nexkartbackend.ShippingRate;


import jakarta.transaction.Transactional;
import org.nexus.nexkartbackend.Repository.CountryRepository;
import org.nexus.nexkartbackend.Repository.ProductRepository;
import org.nexus.nexkartbackend.Repository.ShippingRateRepository;
import org.nexus.nexkartbackend.entity.Country;
import org.nexus.nexkartbackend.entity.Order;
import org.nexus.nexkartbackend.entity.Product;
import org.nexus.nexkartbackend.entity.ShippingRate;
import org.nexus.nexkartbackend.exception.*;
import org.nexus.nexkartbackend.paging.PagingAndSortingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static org.nexus.nexkartbackend.order.OrderService.ORDERS_PER_PAGE;


@Service
@Transactional
public class ShippingRateService {

    public static final int RATES_PER_PAGE = 10;
    public static final int DIM_DIVISOR = 139;


    @Autowired
    private ShippingRateRepository shipRepo;

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private ProductRepository productRepo;


    public float calculateShippingCost(Integer productId, Integer countryId, String state)
            throws ShippingRateNotFoundException {

        ShippingRate shippingRate = shipRepo.findByCountryAndState(countryId, state);

        if (shippingRate == null) {
            throw new ShippingRateNotFoundException("No shipping rate found for the given "
                    + "destination. You have to enter shipping cost manually.");
        }

        Product product = productRepo.findById(productId).get();

        float dimWeight = (product.getLength() * product.getWidth() * product.getHeight()) / DIM_DIVISOR;
        float finalWeight = product.getWeight() > dimWeight ? product.getWeight() : dimWeight;

        return finalWeight * shippingRate.getRate();
    }



    public Page<ShippingRate> listByPage(int pageNum, String keyword) {

        Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE);

        if (keyword != null && !keyword.isEmpty()) {
            return shipRepo.findAll(keyword, pageable);
        }

        return shipRepo.findAll(pageable);

    }

    public List<Country> listAllCountries() {
        return countryRepo.findAllByOrderByNameAsc();
    }

    public void save(ShippingRate rateInForm) throws ShippingRateAlreadyExistsException {
        ShippingRate rateInDB = shipRepo.findByCountryAndState(
                rateInForm.getCountry().getId(), rateInForm.getState());
        boolean foundExistingRateInNewMode = rateInForm.getId() == null && rateInDB != null;
        boolean foundDifferentExistingRateInEditMode = rateInForm.getId() != null && rateInDB != null && !rateInDB.equals(rateInForm);

        if (foundExistingRateInNewMode || foundDifferentExistingRateInEditMode) {
            throw new ShippingRateAlreadyExistsException("There's already a rate for the destination "
                    + rateInForm.getCountry().getName() + ", " + rateInForm.getState());
        }
        shipRepo.save(rateInForm);
    }

    public ShippingRate get(Integer id) throws ShippingRateNotFoundException {
        try {
            return shipRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
        }
    }

    public void updateCODSupport(Integer id, boolean codSupported) throws ShippingRateNotFoundException {
        Long count = shipRepo.countById(id);
        if (count == null || count == 0) {
            throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
        }

        shipRepo.updateCODSupport(id, codSupported);
    }

    public void delete(Integer id) throws ShippingRateNotFoundException {
        Long count = shipRepo.countById(id);
        if (count == null || count == 0) {
            throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);

        }
        shipRepo.deleteById(id);
    }
}