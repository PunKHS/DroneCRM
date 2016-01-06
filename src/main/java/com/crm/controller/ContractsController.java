package com.crm.controller;

import com.crm.grid.ContractsGrid;
import com.crm.model.Contracts;
import com.crm.model.Orders;
import com.crm.service.ContractsService;
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
import java.util.*;

@RequestMapping("/contracts")
@Controller
public class ContractsController {
    private final Logger logger = LoggerFactory.getLogger(ContractsController.class);

    private ContractsService contractsService;
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        logger.info("Listing contracts");

        List<Contracts> contracts = contractsService.findAll();
        uiModel.addAttribute("contracts", contracts);

        logger.info("No. of contracts: " + contracts.size());

        return "contracts/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Contracts contracts = contractsService.findById(id);
        uiModel.addAttribute("contracts", contracts);

        return "contracts/show";
    }

    @RequestMapping(value = "/delete/{id}", params = "form", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        contractsService.delete(id);
        logger.info("Contracts was delete");
        return "redirect:/contracts/";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Contracts contracts, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        logger.info("Updating contracts");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("contracts_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("contracts", contracts);
            return "contracts/update";
        }
        logger.info(contracts.getNumber());
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("contracts_save_success", new Object[]{}, locale)));
        contractsService.save(contracts);
        return "redirect:/contracts/" + UrlUtil.encodeUrlPathSegment(contracts.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("contracts", contractsService.findById(id));
        try {
            Set<Map.Entry<Long, String>> orders;
            List<Orders> ordersList = contractsService.getAllOrders();
            final Map<Long, String> ordersMap = new HashMap<Long, String>();
            if (ordersList != null && !ordersList.isEmpty()) {
                for (Orders eachOrders : ordersList) {
                    if (eachOrders != null) {
                        ordersMap.put(eachOrders.getId(), eachOrders.getNumber() + " от " + eachOrders.getDate().toString());
                    }
                }
            }
            orders = ordersMap.entrySet();
            uiModel.addAttribute("orders", orders);
        } catch (Exception ex){
            logger.warn(ex.toString());
        }
        return "contracts/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Contracts contracts, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Creating contracts");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("contracts_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("contracts", contracts);
            return "contracts/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("contracts_save_success", new Object[]{}, locale)));
        logger.info("Contracts id: " + contracts.getId());
        contractsService.save(contracts);
        return "redirect:/contracts/";
    }

    @PreAuthorize("isAuthenticated()") // защита от использовани€ неавторизованным пользователем
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Contracts contracts = new Contracts();
        uiModel.addAttribute("contracts", contracts);
        try {
            Set<Map.Entry<Long, String>> orders;
            List<Orders> ordersList = contractsService.getAllOrders();
            final Map<Long, String> ordersMap = new HashMap<Long, String>();
            if (ordersList != null && !ordersList.isEmpty()) {
                for (Orders eachOrders : ordersList) {
                    if (eachOrders != null) {
                        ordersMap.put(eachOrders.getId(), eachOrders.getNumber() + " от " + eachOrders.getDate().toString());
                    }
                }
            }
            orders = ordersMap.entrySet();
            uiModel.addAttribute("orders", orders);
        } catch (Exception ex){
            logger.warn(ex.toString());
        }
        return "contracts/create";
    }

    // ѕостроничное разбиение информации в гриде
    @ResponseBody
    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    public ContractsGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "rows", required = false) Integer rows,
                                  @RequestParam(value = "sidx", required = false) String sortBy,
                                  @RequestParam(value = "sord", required = false) String order) {

        logger.info("Listing contracts for grid with page: {}, rows: {}", page, rows);
        logger.info("Listing contracts for grid with sort: {}, order: {}", sortBy, order);

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

        Page<Contracts> contractsPage = contractsService.findAllByPage(pageRequest);

        // Construct the grid data that will return as JSON data
        ContractsGrid contractsGrid = new ContractsGrid();

        contractsGrid.setCurrentPage(contractsPage.getNumber() + 1);
        contractsGrid.setTotalPages(contractsPage.getTotalPages());
        contractsGrid.setTotalRecords(contractsPage.getTotalElements());

        contractsGrid.setContractsData(Lists.newArrayList(contractsPage.iterator()));

        return contractsGrid;
    }

    @Autowired
    public void setContractsService(ContractsService contractsService) {
        this.contractsService = contractsService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
