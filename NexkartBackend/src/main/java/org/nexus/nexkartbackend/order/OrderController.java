package org.nexus.nexkartbackend.order;


import jakarta.servlet.http.HttpServletRequest;
import org.nexus.nexkartbackend.entity.Country;
import org.nexus.nexkartbackend.entity.Order;
import org.nexus.nexkartbackend.exception.OrderNotFoundException;
import org.nexus.nexkartbackend.setting.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


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



}