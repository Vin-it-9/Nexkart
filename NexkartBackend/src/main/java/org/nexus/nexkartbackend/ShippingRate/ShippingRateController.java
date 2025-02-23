package org.nexus.nexkartbackend.ShippingRate;

import java.util.List;

import org.nexus.nexkartbackend.entity.Country;
import org.nexus.nexkartbackend.entity.Order;
import org.nexus.nexkartbackend.entity.ShippingRate;
import org.nexus.nexkartbackend.exception.ShippingRateAlreadyExistsException;
import org.nexus.nexkartbackend.exception.ShippingRateNotFoundException;
import org.nexus.nexkartbackend.paging.PagingAndSortingHelper;
import org.nexus.nexkartbackend.paging.PagingAndSortingParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class ShippingRateController {

    private String defaultRedirectURL = "redirect:/shipping_rates";

    @Autowired private ShippingRateService service;

    @GetMapping("/shipping_rates")
    public String ListFirstPage(Model model) {
        return listPage(1,model,null);
    }

    @GetMapping("/shipping_rates/page/{pageNum}")
    public String listPage(@PathVariable (name = "pageNum") int pageNum, Model model , @Param("keyword") String keyword ) {

        Page<ShippingRate> page = service.listByPage(pageNum,keyword);

        List<ShippingRate> shipping_rates = page.getContent();

        long startcount = (pageNum - 1) * service.RATES_PER_PAGE  + 1;

        long endcount = startcount + service.RATES_PER_PAGE - 1;


        if(endcount > page.getTotalElements()) {
            endcount = page.getTotalElements();
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("startcount", startcount);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentpage", pageNum);
        model.addAttribute("endcount", endcount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("shipping_rates" , shipping_rates);


        return "shipping_rates/shipping_rates" ;

    }

    @GetMapping("/shipping_rates/new")
    public String newRate(Model model) {
        List<Country> listCountries = service.listAllCountries();

        model.addAttribute("rate", new ShippingRate());
        model.addAttribute("listCountries", listCountries);
        model.addAttribute("pageTitle", "New Rate");

        return "shipping_rates/shipping_rate_form";
    }

    @PostMapping("/shipping_rates/save")
    public String saveRate(ShippingRate rate, RedirectAttributes ra) {
        try {
            service.save(rate);
            ra.addFlashAttribute("message", "The shipping rate has been saved successfully.");
        } catch (ShippingRateAlreadyExistsException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
        }
        return defaultRedirectURL;
    }

    @GetMapping("/shipping_rates/edit/{id}")
    public String editRate(@PathVariable(name = "id") Integer id,
                           Model model, RedirectAttributes ra) {
        try {
            ShippingRate rate = service.get(id);
            List<Country> listCountries = service.listAllCountries();

            model.addAttribute("listCountries", listCountries);
            model.addAttribute("rate", rate);
            model.addAttribute("pageTitle", "Edit Rate (ID: " + id + ")");

            return "shipping_rates/shipping_rate_form";
        } catch (ShippingRateNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return defaultRedirectURL;
        }
    }

    @GetMapping("/shipping_rates/cod/{id}/enabled/{supported}")
    public String updateCODSupport(@PathVariable(name = "id") Integer id,
                                   @PathVariable(name = "supported") Boolean supported,
                                   Model model, RedirectAttributes ra) {
        try {
            service.updateCODSupport(id, supported);
            ra.addFlashAttribute("message", "COD support for shipping rate ID " + id + " has been updated.");
        } catch (ShippingRateNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
        }
        return defaultRedirectURL;
    }

    @GetMapping("/shipping_rates/delete/{id}")
    public String deleteRate(@PathVariable(name = "id") Integer id,
                             Model model, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The shipping rate ID " + id + " has been deleted.");
        } catch (ShippingRateNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
        }
        return defaultRedirectURL;
    }


}