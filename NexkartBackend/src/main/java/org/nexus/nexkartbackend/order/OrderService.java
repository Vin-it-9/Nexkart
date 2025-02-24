package org.nexus.nexkartbackend.order;

import org.nexus.nexkartbackend.Repository.CountryRepository;
import org.nexus.nexkartbackend.Repository.OrderRepository;
import org.nexus.nexkartbackend.entity.*;
import org.nexus.nexkartbackend.exception.OrderNotFoundException;
import org.nexus.nexkartbackend.paging.PagingAndSortingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class OrderService {

    public static final int ORDERS_PER_PAGE = 10;

    @Autowired
    private OrderRepository repo;

    @Autowired
    private CountryRepository countryRepo;

    public Page<Order> listByPage(int pageNum, String keyword) {

        Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE);

        if (keyword != null && !keyword.isEmpty()) {
            return repo.findAll(keyword, pageable);
        }

        return repo.findAll(pageable);

    }

    public void delete(Integer id) throws OrderNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new OrderNotFoundException("Could not find any orders with ID " + id);
        }

        repo.deleteById(id);
    }

    public Order get(Integer id) throws OrderNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new OrderNotFoundException("Could not find any orders with ID " + id);
        }
    }

    public List<Country> listAllCountries() {
        return countryRepo.findAllByOrderByNameAsc();
    }

    public void save(Order orderInForm) {
        Order orderInDB = repo.findById(orderInForm.getId()).get();
        orderInForm.setOrderTime(orderInDB.getOrderTime());
        orderInForm.setCustomer(orderInDB.getCustomer());
        repo.save(orderInForm);
    }

    public void updateStatus(Integer orderId, String status) {
        Order orderInDB = repo.findById(orderId).get();
        OrderStatus statusToUpdate = OrderStatus.valueOf(status);

        if (!orderInDB.hasStatus(statusToUpdate)) {
            List<OrderTrack> orderTracks = orderInDB.getOrderTracks();

            OrderTrack track = new OrderTrack();
            track.setOrder(orderInDB);
            track.setStatus(statusToUpdate);
            track.setUpdatedTime(new Date());
            track.setNotes(statusToUpdate.defaultDescription());

            orderTracks.add(track);

            orderInDB.setStatus(statusToUpdate);

            repo.save(orderInDB);
        }

    }

}
