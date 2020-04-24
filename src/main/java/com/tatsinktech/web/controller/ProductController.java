/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.register.Product;
import com.tatsinktech.web.model.register.Promotion;
import com.tatsinktech.web.model.register.ServiceProvider;
import com.tatsinktech.web.repository.ServiceProviderRepository;
import com.tatsinktech.web.repository.PromotionRepository;
import com.tatsinktech.web.service.ProductService;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author olivier.tatsinkou
 */
@Controller
@RequestMapping("products")
public class ProductController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private ProductService productService;

    private HashMap<String, Promotion> HashPomotion = new HashMap<String, Promotion>();
    private HashMap<String, ServiceProvider> HashService = new HashMap<String, ServiceProvider>();

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    private boolean enable_edit = false;
    private boolean enable_visibility = false;

    public boolean isEnable_edit() {
        return enable_edit;
    }

    public void setEnable_edit(boolean enable_edit) {
        this.enable_edit = enable_edit;
    }

    public boolean isEnable_visibility() {
        return enable_visibility;
    }

    public void setEnable_visibility(boolean enable_visibility) {
        this.enable_visibility = enable_visibility;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }

    @GetMapping
    public String index(@NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        return "redirect:/products/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, @NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Page<Product> page = productService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 10);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "products/list";

    }

    @GetMapping("/add")
    public String add(@NotNull Model model, @NotNull Authentication auth) {
        enable_edit = false;
        enable_visibility = false;
        model.addAttribute("enableEdit", enable_edit);
        model.addAttribute("enableVisibility", enable_visibility);

        List<Promotion> listPromotion = promotionRepository.findAll();
        List<ServiceProvider> listService = serviceProviderRepository.findAll();

        HashPomotion.clear();
        for (Promotion promo : listPromotion) {
            HashPomotion.put(promo.getPromotion_name(), promo);
        }

        HashService.clear();
        for (ServiceProvider serv : listService) {
            HashService.put(serv.getService_name(), serv);
        }

        model.addAttribute("listPromotion", listPromotion);
        model.addAttribute("listService", listService);
        model.addAttribute("product", new Product());
        loadMode(model, auth);
        return "products/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @NotNull Model model, @NotNull Authentication auth) {
        List<Promotion> listPromotion = promotionRepository.findAll();
        List<ServiceProvider> listService = serviceProviderRepository.findAll();

        enable_edit = true;
        enable_visibility = true;
        model.addAttribute("enableEdit", enable_edit);
        model.addAttribute("enableVisibility", enable_visibility);
        model.addAttribute("listPromotion", listPromotion);
        model.addAttribute("listService", listService);

        model.addAttribute("product", productService.get(id));
        loadMode(model, auth);
        return "products/form";

    }

    @PostMapping(value = "/save")
    public String save(Product entity, final RedirectAttributes ra, @NotNull Model model, @NotNull Authentication auth, @RequestParam(value = "action", required = true) String action) {
        String result = "/";
        loadMode(model, auth);

        if (action.equals("save")) {
            if (Optional.ofNullable(entity.getStart_time()).isPresent() && Optional.ofNullable(entity.getEnd_time()).isPresent()) {
                if (registerValidator(entity.getValidity()) && registerValidator(entity.getPending_duration())) {
                    String frame_val = entity.getFrame_time_validity();
                    if (checkFrameTime(frame_val)) {
                        try {
                            String pro_name = entity.getPromotion().getPromotion_name();
                            String serv_name = entity.getService().getService_name();

                            Promotion pormo = null;
                            ServiceProvider serv = null;

                            if (!StringUtils.isBlank(pro_name) && !pro_name.equals("NONE")) {
                                pormo = HashPomotion.get(pro_name);
                            }

                            if (!StringUtils.isBlank(serv_name) && !pro_name.equals("NONE")) {
                                serv = HashService.get(serv_name);
                            }

                            entity.setPromotion(pormo);
                            entity.setService(serv);

                            Product save = productService.save(entity);
                            ra.addFlashAttribute("successFlash", "Success Add New Service");
//                            logger.info("Success Add new Promoiton =  " + save);
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Error to Add promotion", e);
                            model.addAttribute("invalidAdd_Promotion", true);
                        }
                        result = "redirect:/products";
                    } else {
                        logger.info("Invalid Frame Time Validity");
                        logger.info("entity =  " + entity);
                        model.addAttribute("errorMessage", "Invalid Frame Time Validity");
                        model.addAttribute("product", entity);
                        result = "redirect:/promotions/add";
                    }

                } else {
                    logger.info("Invalid Validity or Pending Duration Format ");
                    logger.info("entity =  " + entity);
                    model.addAttribute("errorMessage", "Invalid Validity or Pending Duration Format");
                    model.addAttribute("product", entity);
                    result = "redirect:/promotions/add";
                }

            } else {
                logger.info("Invalid Start date or End date format");
                logger.info("entity =  " + entity);
                model.addAttribute("errorMessage", "Invalid Start date or End date format");
                model.addAttribute("product", entity);
                result = "redirect:/promotions/add";
            }
        } else if (action.equals("edit")) {

            enable_edit = false;
            enable_visibility = false;
            model.addAttribute("enableEdit", enable_edit);
            model.addAttribute("enableVisibility", enable_visibility);
            model.addAttribute("product", entity);
            result = "products/form";
        }

        return result;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @NotNull Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        productService.delete(id);
        return "redirect:/products";

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

    private boolean checkFrameTime(String frame_time) {
        if (!StringUtils.isBlank(frame_time)) {
            Pattern ptn = Pattern.compile("\\-");
            List<String> listTime = Arrays.asList(ptn.split(frame_time));
            if (time24HoursValidator(listTime.get(0)) && time24HoursValidator(listTime.get(1))) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean time24HoursValidator(String time_hour) {
        if (!StringUtils.isBlank(time_hour)) {
            String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
            Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
            Matcher matcher = pattern.matcher(time_hour);
            return matcher.matches();
        } else {
            return true;
        }

    }

    private boolean registerValidator(String periode) {
        if (!StringUtils.isBlank(periode)) {
            String regex_string = "^(D|H)([0-9])*";
            Pattern pattern = Pattern.compile(regex_string);
            Matcher matcher = pattern.matcher(periode);
            return matcher.matches();
        } else {
            return true;
        }

    }

}
