package com.crm.controller;

import com.crm.grid.CustomersGrid;
import com.crm.model.Customers;
import com.crm.service.CustomersService;
import com.crm.service.UrlUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RequestMapping("/customers")
@Controller
public class CustomersController {
    private final Logger logger = LoggerFactory.getLogger(CustomersController.class);

    private CustomersService customersService;
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        logger.info("Listing customers");

        List<Customers> customers = customersService.findAll();
        uiModel.addAttribute("customers", customers);

        logger.info("No. of customers: " + customers.size());

        return "customers/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Customers customers = customersService.findById(id);
        uiModel.addAttribute("customers", customers);

        return "customers/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Customers customers, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        logger.info("Updating customers");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("customers_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("customers", customers);
            return "customers/update";
        }
        logger.info(customers.getName());
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("customers_save_success", new Object[]{}, locale)));
        customersService.save(customers);
        return "redirect:/customers/" + UrlUtil.encodeUrlPathSegment(customers.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("customers", customersService.findById(id));
        return "customers/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Customers customers, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Creating customers");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("customers_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("customers", customers);
            return "customers/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("customers_save_success", new Object[]{}, locale)));
        logger.info("Customers id: " + customers.getId());
        customersService.save(customers);
        return "redirect:/customers/";
    }

    @PreAuthorize("isAuthenticated()") // защита от использовани€ неавторизованным пользователем
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Customers customers = new Customers();
        uiModel.addAttribute("customers", customers);

        return "customers/create";
    }

    // ѕостроничное разбиение информации в гриде
    @ResponseBody
    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    public CustomersGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "rows", required = false) Integer rows,
                                  @RequestParam(value = "sidx", required = false) String sortBy,
                                  @RequestParam(value = "sord", required = false) String order) {

        logger.info("Listing customers for grid with page: {}, rows: {}", page, rows);
        logger.info("Listing customers for grid with sort: {}, order: {}", sortBy, order);

        // Process order by
        Sort sort = null;
        String orderBy = sortBy;
//        if (orderBy != null && orderBy.equals("birthDateString"))
//            orderBy = "birthDate";

        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0, while jqGrid starts with 1
        PageRequest pageRequest = null;

        if (sort != null) {
            pageRequest = new PageRequest(page - 1, rows, sort);
        } else {
            pageRequest = new PageRequest(page - 1, rows);
        }

        Page<Customers> customersPage = customersService.findAllByPage(pageRequest);

        // Construct the grid data that will return as JSON data
        CustomersGrid customersGrid = new CustomersGrid();

        customersGrid.setCurrentPage(customersPage.getNumber() + 1);
        customersGrid.setTotalPages(customersPage.getTotalPages());
        customersGrid.setTotalRecords(customersPage.getTotalElements());

        customersGrid.setCustomersData(Lists.newArrayList(customersPage.iterator()));

        return customersGrid;
    }


    @Autowired
    public void setCustomersService(CustomersService customersService) {
        this.customersService = customersService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
