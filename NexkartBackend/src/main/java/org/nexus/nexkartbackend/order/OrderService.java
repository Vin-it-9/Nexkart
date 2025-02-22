package org.nexus.nexkartbackend.order;

import org.nexus.nexkartbackend.Repository.OrderRepository;
import org.nexus.nexkartbackend.entity.Order;
import org.nexus.nexkartbackend.entity.User;
import org.nexus.nexkartbackend.exception.OrderNotFoundException;
import org.nexus.nexkartbackend.paging.PagingAndSortingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class OrderService {

    public static final int ORDERS_PER_PAGE = 10;

    @Autowired private
    OrderRepository repo;

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


}
