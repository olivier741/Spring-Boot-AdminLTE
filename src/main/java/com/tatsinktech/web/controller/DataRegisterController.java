/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.beans.SearchForm;
import com.tatsinktech.web.model.register.Register;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author olivier
 */
@Controller
@RequestMapping("registers")
public class DataRegisterController {

    private boolean enableEdit = true;

    public boolean isEnableEdit() {
        return enableEdit;
    }

    public void setEnableEdit(boolean enableEdit) {
        this.enableEdit = enableEdit;
    }

    @Autowired
    private RegisterRepository registerRepo;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }

    @GetMapping("/download")
    public ModelMap download(@PageableDefault(size = 10) Pageable pageable, @Valid @ModelAttribute("searchForm") SearchForm searchForm, Model model, @NotNull Authentication auth) {

        loadMode(model, auth);
        ModelMap result = new ModelMap().addAttribute("registers", registerRepo.findAll(pageable));
        if (searchForm != null) {
            String msisdn = searchForm.getMsisdn();
            String transID = searchForm.getTransactionId();
            Date dateFrom = searchForm.getDateFrom();
            Date dateTo = searchForm.getDateTo();
            int trans_option = searchForm.getTrans_option();
            int msisdn_option = searchForm.getMsisdn_option();

            if (!StringUtils.isBlank(msisdn) && dateFrom == null && dateTo == null) {
                model.addAttribute("searchForm", searchForm);
                if (msisdn_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnIgnoreCase(msisdn, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnContainingIgnoreCase(msisdn, pageable));
                }

            }

            if (!StringUtils.isBlank(transID) && dateFrom == null && dateTo == null) {
                model.addAttribute("searchForm", searchForm);
                if (trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByTransactionIdIgnoreCase(transID, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByTransactionIdContainingIgnoreCase(transID, pageable));
                }

            }

            if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetween(dateFrom, dateTo, pageable));
            }

            if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                if (msisdn_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndMsisdnIgnoreCase(dateFrom, dateTo, msisdn, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndMsisdnContainingIgnoreCase(dateFrom, dateTo, msisdn, pageable));
                }

            }

            if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);

                if (trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCase(dateFrom, dateTo, transID, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCase(dateFrom, dateTo, transID, pageable));
                }
            }

            if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);

                if (msisdn_option == 2 && trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                } else if (msisdn_option == 2 && trans_option != 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                } else if (msisdn_option != 2 && trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                }
            }

        } else {
            SearchForm searchForm1 = new SearchForm();
            searchForm1.setDateFrom(new Date());
            searchForm1.setDateTo(new Date());
            model.addAttribute("searchForm", searchForm1);
        }

        return result;
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Register entity = registerRepo.findRegisterById(id);
        enableEdit = true;

        String productCode = "";
        if (entity.getProduct() != null) {
            productCode = entity.getProduct().getProductCode();
        }

        model.addAttribute("product_code", productCode);
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("reg", entity);
    }

    @GetMapping("/list")
    public ModelMap getlist(@PageableDefault(size = 10) Pageable pageable, @Valid @ModelAttribute("searchForm") SearchForm searchForm, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        ModelMap result = new ModelMap().addAttribute("registers", registerRepo.findAll(pageable));
        if (searchForm != null) {
            String msisdn = searchForm.getMsisdn();
            String transID = searchForm.getTransactionId();
            Date dateFrom = searchForm.getDateFrom();
            Date dateTo = searchForm.getDateTo();
            int trans_option = searchForm.getTrans_option();
            int msisdn_option = searchForm.getMsisdn_option();

            if (!StringUtils.isBlank(msisdn) && dateFrom == null && dateTo == null) {
                model.addAttribute("searchForm", searchForm);
                if (msisdn_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnIgnoreCase(msisdn, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnContainingIgnoreCase(msisdn, pageable));
                }

            }

            if (!StringUtils.isBlank(transID) && dateFrom == null && dateTo == null) {
                model.addAttribute("searchForm", searchForm);
                if (trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByTransactionIdIgnoreCase(transID, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByTransactionIdContainingIgnoreCase(transID, pageable));
                }

            }

            if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetween(dateFrom, dateTo, pageable));
            }

            if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                if (msisdn_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndMsisdnIgnoreCase(dateFrom, dateTo, msisdn, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndMsisdnContainingIgnoreCase(dateFrom, dateTo, msisdn, pageable));
                }

            }

            if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);

                if (trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCase(dateFrom, dateTo, transID, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCase(dateFrom, dateTo, transID, pageable));
                }
            }

            if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);

                if (msisdn_option == 2 && trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                } else if (msisdn_option == 2 && trans_option != 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                } else if (msisdn_option != 2 && trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                }
            }

        } else {
            SearchForm searchForm1 = new SearchForm();
            searchForm1.setDateFrom(new Date());
            searchForm1.setDateTo(new Date());
            model.addAttribute("searchForm", searchForm1);
        }

        return result;
    }

    @PostMapping("/list")
    public ModelMap postList(@PageableDefault(size = 10) Pageable pageable, @Valid @ModelAttribute("searchForm") SearchForm searchForm, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        ModelMap result = new ModelMap().addAttribute("registers", registerRepo.findAll(pageable));
        if (searchForm != null) {
            String msisdn = searchForm.getMsisdn();
            String transID = searchForm.getTransactionId();
            Date dateFrom = searchForm.getDateFrom();
            Date dateTo = searchForm.getDateTo();
            int trans_option = searchForm.getTrans_option();
            int msisdn_option = searchForm.getMsisdn_option();

            if (!StringUtils.isBlank(msisdn) && dateFrom == null && dateTo == null) {
                model.addAttribute("searchForm", searchForm);
                if (msisdn_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnIgnoreCase(msisdn, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnContainingIgnoreCase(msisdn, pageable));
                }

            }

            if (!StringUtils.isBlank(transID) && dateFrom == null && dateTo == null) {
                model.addAttribute("searchForm", searchForm);
                if (trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByTransactionIdIgnoreCase(transID, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByTransactionIdContainingIgnoreCase(transID, pageable));
                }

            }

            if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetween(dateFrom, dateTo, pageable));
            }

            if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);
                if (msisdn_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndMsisdnIgnoreCase(dateFrom, dateTo, msisdn, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndMsisdnContainingIgnoreCase(dateFrom, dateTo, msisdn, pageable));
                }

            }

            if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);

                if (trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCase(dateFrom, dateTo, transID, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCase(dateFrom, dateTo, transID, pageable));
                }
            }

            if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID) && dateFrom != null && dateTo != null) {
                model.addAttribute("searchForm", searchForm);

                if (msisdn_option == 2 && trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                } else if (msisdn_option == 2 && trans_option != 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                } else if (msisdn_option != 2 && trans_option == 2) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                } else {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(dateFrom, dateTo, transID, msisdn, pageable));
                }
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
