package org.nexus.nexkartbackend.Repository;

import java.util.Date;
import java.util.List;

import org.nexus.nexkartbackend.entity.OrderDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {

    @Query("SELECT NEW org.nexus.nexkartbackend.entity.OrderDetail(d.product.category.name, d.quantity,"
            + " d.productCost, d.shippingCost, d.subtotal)"
            + " FROM OrderDetail d WHERE d.order.orderTime BETWEEN ?1 AND ?2")
    public List<OrderDetail> findWithCategoryAndTimeBetween(Date startTime, Date endTime);

    @Query("SELECT NEW org.nexus.nexkartbackend.entity.OrderDetail(d.quantity, d.product.name,"
            + " d.productCost, d.shippingCost, d.subtotal)"
            + " FROM OrderDetail d WHERE d.order.orderTime BETWEEN ?1 AND ?2")
    public List<OrderDetail> findWithProductAndTimeBetween(Date startTime, Date endTime);

}