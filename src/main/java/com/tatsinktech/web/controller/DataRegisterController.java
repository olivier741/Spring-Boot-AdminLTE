/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.beans.SearchForm;
import com.tatsinktech.web.repository.RegisterRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author olivier
 */
@Controller
@RequestMapping("registers")
public class DataRegisterController {

    @Autowired
    private RegisterRepository registerRepo;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }

    @GetMapping("/list")
    public ModelMap getlist(@PageableDefault(size = 10) Pageable pageable, @Valid @ModelAttribute("searchForm") SearchForm searchForm, Model model, @NotNull Authentication auth) {
        ModelMap result = new ModelMap().addAttribute("registers", registerRepo.findAll(pageable));
        loadMode(model, auth);

        if (searchForm != null) {
            String msisdn = searchForm.getMsisdn();
            String transID = searchForm.getTransactionId();
            Date dateFrom = searchForm.getDateFrom();
            Date dateTo = searchForm.getDateTo();
            if (!StringUtils.isBlank(msisdn) && dateFrom == null && dateTo == null) {
                model.addAttribute("searchForm", searchForm);
                result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnContainingIgnoreCase(msisdn, pageable));
            }

            if (!StringUtils.isBlank(transID) && dateFrom == null && dateTo == null) {
                model.addAttribute("searchForm", searchForm);
                result = new ModelMap().addAttribute("registers", registerRepo.findByTransactionIdContainingIgnoreCase(transID, pageable));

            }

            if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetween(dateTo, dateFrom, pageable));
            }

            if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndMsisdnContainingIgnoreCase(dateTo, dateFrom, msisdn, pageable));
            }

            if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCase(dateTo, dateFrom, transID, pageable));
            }

            if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(dateTo, dateFrom, transID, msisdn, pageable));
            }

        } else {
            SearchForm searchForm1 = new SearchForm();
            searchForm1.setDateFrom(new Date());
            searchForm1.setDateTo(new Date());
            model.addAttribute("searchForm", searchForm1);
        }
        return result;
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
