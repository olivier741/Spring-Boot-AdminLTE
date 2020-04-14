/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.Customers;
import com.tatsinktech.web.model.register.Action;
import com.tatsinktech.web.service.ActionService;
import javax.validation.constraints.NotNull;
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

/**
 *
 * @author olivier
 */
@Controller
@RequestMapping("action")
public class ActionController {
    
    private ActionService actionService;

    @Autowired
    public void setActionService(ActionService actionService) {
        this.actionService = actionService;
    }
    
    @GetMapping
    public String index(@NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        return "redirect:/action/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, @NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Page<Action> page = actionService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "action/list";

    }

    @GetMapping("/add")
    public String add(@NotNull Model model, @NotNull Authentication auth) {
        model.addAttribute("act", new Action());
        loadMode(model, auth);
        return "action/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @NotNull Model model, @NotNull Authentication auth) {
        model.addAttribute("act", actionService.get(id));
        loadMode(model, auth);
        return "action/form";

    }

    @PostMapping(value = "/save")
    public String save(Action act, final RedirectAttributes ra, @NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Action save = actionService.save(act);
        ra.addFlashAttribute("successFlash", "New action is save with Success.");
        return "redirect:/action";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        actionService.delete(id);
        return "redirect:/action";

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
