package org.nexus.nexkartbackend.controller;


import org.hibernate.tool.schema.spi.Exporter;
import org.nexus.nexkartbackend.Exporter.UserCsvExporter;
import org.nexus.nexkartbackend.Exporter.UserExcelExporter;
import org.nexus.nexkartbackend.Exporter.UserPdfExporter;
import org.nexus.nexkartbackend.entity.Role;
import org.nexus.nexkartbackend.entity.User;
import org.nexus.nexkartbackend.exception.UserNotFoundException;
import org.nexus.nexkartbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonPath;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;



@Controller
public class UserController {

    @Autowired
    private UserService service ;

    @GetMapping("/Users")
    public String listAll(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers" , listUsers);

        return "Users" ;
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> listRoles = service.listRoles();
        User user = new User();

        user.setEnabled(true);

        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle","Create New User");


        return "user_form";

    }

    @PostMapping("/users/save")
    public String saveUser(User user ,RedirectAttributes redirectAttributes) {

        System.out.println(user);
        service.save(user);

        redirectAttributes.addFlashAttribute("message" , "User added successfully ");
        return "redirect:/Users";

    }


    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id ,
                           Model model ,
                           RedirectAttributes redirectAttributes ){
        try {
            List<Role> listRoles = service.listRoles();

            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle","Edit User (ID:" + id + ")");
            model.addAttribute("listRoles", listRoles);

            return "user_form" ;
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message" , ex.getMessage());

        }
        return "redirect:/Users";

    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id ,
                             Model model ,
                             RedirectAttributes redirectAttributes ){
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message" , "The user with ID" + id + " deleted successfully");

        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message" , ex.getMessage());

        }
        return "redirect:/Users";
    }


    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List <User> listUsers =	service.listAll();
        UserCsvExporter exporter = new UserCsvExporter ();
        exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List <User> listUsers =	service.listAll();
        UserExcelExporter exporter = new UserExcelExporter ();
        exporter.export(listUsers, response);

    }
    @GetMapping("users/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        List<User> listUsers = service.listAll();
        UserPdfExporter exporter = new UserPdfExporter();
        exporter.export(listUsers, response);
    }


}