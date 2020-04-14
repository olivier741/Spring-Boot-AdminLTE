package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.Customers;
import com.tatsinktech.web.service.CustomersService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("customers")
public class CustomerController {

    private CustomersService customerService;

    @Autowired
    public void setCustomerService(CustomersService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String index(@NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        return "redirect:/customers/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, @NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Page<Customers> page = customerService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "customers/list";

    }

    @GetMapping("/add")
    public String add(@NotNull Model model, @NotNull Authentication auth) {
        model.addAttribute("customer", new Customers());
        loadMode(model, auth);
        return "customers/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @NotNull Model model, @NotNull Authentication auth) {
        model.addAttribute("customer", customerService.get(id));
        loadMode(model, auth);
        return "customers/form";

    }

    @PostMapping(value = "/save")
    public String save(Customers customer, final RedirectAttributes ra, @NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Customers save = customerService.save(customer);
        ra.addFlashAttribute("successFlash", "Cliente foi salvo com sucesso.");
        return "redirect:/customers";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        customerService.delete(id);
        return "redirect:/customers";

    }

    private void loadMode(Model model, Authentication auth) {
        AccessToken acceToken = ((SimpleKeycloakAccount) auth.getDetails())
                .getKeycloakSecurityContext()
                .getToken();
        String id = acceToken.getId();
        String username = acceToken.getPreferredUsername();
        String birtdate = acceToken.getBirthdate();
        String name = acceToken.getName();
        String email = acceToken.getEmail();
        String firstName = acceToken.getFamilyName();
        String genre = acceToken.getGender();
        String givenName = acceToken.getGivenName();
        String midleName = acceToken.getMiddleName();
        String nickName = acceToken.getNickName();
        String phone_number = acceToken.getPhoneNumber();

        model.addAttribute("keycloak_username", username);
        model.addAttribute("keycloak_id", id);
        model.addAttribute("keycloak_lastname", firstName);
        model.addAttribute("keycloak_name", name);
        model.addAttribute("keycloak_email", email);
        model.addAttribute("keycloak_firstname", givenName);
        model.addAttribute("keycloak_genre", genre);
        model.addAttribute("keycloak_midlename", midleName);
        model.addAttribute("keycloak_nickname", nickName);
        model.addAttribute("keycloak_phone", phone_number);
    }

}
