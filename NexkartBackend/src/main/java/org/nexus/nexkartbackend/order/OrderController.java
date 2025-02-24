package org.nexus.nexkartbackend.order;


import jakarta.servlet.http.HttpServletRequest;
import org.nexus.nexkartbackend.entity.*;
import org.nexus.nexkartbackend.exception.OrderNotFoundException;
import org.nexus.nexkartbackend.setting.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;


@Controller
public class OrderController {

    public String defaultRedirectURL = "redirect:/orders";


    @Autowired
    private OrderService orderService;

    @Autowired
    private SettingService settingService;

    @GetMapping("/orders")
    public String ListFirstPage(Model model) {
        return listPage(1,model,null);
    }

    @GetMapping("/orders/page/{pageNum}")
    public String listPage(@PathVariable (name = "pageNum") int pageNum, Model model , @Param("keyword") String keyword ) {

        Page<Order> page = orderService.listByPage(pageNum,keyword);

        List<Order> listOrders = page.getContent();

        long startcount = (pageNum - 1) * orderService.ORDERS_PER_PAGE  + 1;

        long endcount = startcount + orderService.ORDERS_PER_PAGE - 1;


        if(endcount > page.getTotalElements()) {
            endcount = page.getTotalElements();
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("startcount", startcount);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentpage", pageNum);
        model.addAttribute("endcount", endcount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOrders" , listOrders);


        return "orders/orders" ;

    }

    @GetMapping("/orders/detail/{id}")
    public String viewOrderDetails(@PathVariable("id") Integer id, Model model,
                                   RedirectAttributes ra, HttpServletRequest request) {
        try {

            Order order = orderService.get(id);
            model.addAttribute("order", order);
            return "orders/order_details";

        } catch (OrderNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/orders";
        }

    }


    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            orderService.delete(id);;
            ra.addFlashAttribute("message", "The order ID " + id + " has been deleted.");
        } catch (OrderNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra,
                            HttpServletRequest request) {
        try {
            Order order = orderService.get(id);;

            List<Country> listCountries = orderService.listAllCountries();

            model.addAttribute("pageTitle", "Edit Order (ID: " + id + ")");
            model.addAttribute("order", order);
            model.addAttribute("listCountries", listCountries);

            return "orders/order_form";

        } catch (OrderNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return defaultRedirectURL;
        }

    }

    @PostMapping("/order/save")
    public String saveOrder(Order order, HttpServletRequest request, RedirectAttributes ra) {

        String countryName = request.getParameter("countryName");

        order.setCountry(countryName);

        updateProductDetails(order, request);
        updateOrderTracks(order, request);

        orderService.save(order);

        ra.addFlashAttribute("message", "The order ID " + order.getId() + " has been updated successfully");

        return defaultRedirectURL;
    }

    private void updateOrderTracks(Order order, HttpServletRequest request) {

        String[] trackIds = request.getParameterValues("trackId");
        String[] trackStatuses = request.getParameterValues("trackStatus");
        String[] trackDates = request.getParameterValues("trackDate");
        String[] trackNotes = request.getParameterValues("trackNotes");

        List<OrderTrack> orderTracks = order.getOrderTracks();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

        for (int i = 0; i < trackIds.length; i++) {
            OrderTrack trackRecord = new OrderTrack();

            Integer trackId = Integer.parseInt(trackIds[i]);
            if (trackId > 0) {
                trackRecord.setId(trackId);
            }

            trackRecord.setOrder(order);
            trackRecord.setStatus(OrderStatus.valueOf(trackStatuses[i]));
            trackRecord.setNotes(trackNotes[i]);

            try {
                trackRecord.setUpdatedTime(dateFormatter.parse(trackDates[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                throw new RuntimeException(e);
            }

            orderTracks.add(trackRecord);
        }
    }

    private void updateProductDetails(Order order, HttpServletRequest request) {

        String[] detailIds = request.getParameterValues("detailId");
        String[] productIds = request.getParameterValues("productId");
        String[] productPrices = request.getParameterValues("productPrice");
        String[] productDetailCosts = request.getParameterValues("productDetailCost");
        String[] quantities = request.getParameterValues("quantity");
        String[] productSubtotals = request.getParameterValues("productSubtotal");
        String[] productShipCosts = request.getParameterValues("productShipCost");

        Set<OrderDetail> orderDetails = order.getOrderDetails();

        for (int i = 0; i < detailIds.length; i++) {
            System.out.println("Detail ID: " + detailIds[i]);
            System.out.println("\t Prodouct ID: " + productIds[i]);
            System.out.println("\t Cost: " + productDetailCosts[i]);
            System.out.println("\t Quantity: " + quantities[i]);
            System.out.println("\t Subtotal: " + productSubtotals[i]);
            System.out.println("\t Ship cost: " + productShipCosts[i]);

            OrderDetail orderDetail = new OrderDetail();
            Integer detailId = Integer.parseInt(detailIds[i]);
            if (detailId > 0) {
                orderDetail.setId(detailId);
            }

            orderDetail.setOrder(order);
            orderDetail.setProduct(new Product(Integer.parseInt(productIds[i])));
            orderDetail.setProductCost(Float.parseFloat(productDetailCosts[i]));
            orderDetail.setSubtotal(Float.parseFloat(productSubtotals[i]));
            orderDetail.setShippingCost(Float.parseFloat(productShipCosts[i]));
            orderDetail.setQuantity(Integer.parseInt(quantities[i]));
            orderDetail.setUnitPrice(Float.parseFloat(productPrices[i]));

            orderDetails.add(orderDetail);

        }

    }




}