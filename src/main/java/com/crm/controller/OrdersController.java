package com.crm.controller;

import com.crm.grid.OrdersGrid;
import com.crm.model.Customers;
import com.crm.model.Orders;
import com.crm.service.CustomersService;
import com.crm.service.OrdersService;
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

@RequestMapping("/orders")
@Controller
public class OrdersController {
    private final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    private OrdersService ordersService;
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        logger.info("Listing orders");

        List<Orders> orders = ordersService.findAll();
        uiModel.addAttribute("orders", orders);

        logger.info("No. of orders: " + orders.size());

        return "orders/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Orders orders = ordersService.findById(id);
        uiModel.addAttribute("orders", orders);

        return "orders/show";
    }

    @RequestMapping(value = "/delete/{id}", params = "form", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        ordersService.delete(id);
        logger.info("Orders was delete");
        return "redirect:/orders/";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Orders orders, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        logger.info("Updating orders");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("orders_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("orders", orders);
            return "orders/update";
        }
        logger.info(orders.getNumber());
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("orders_save_success", new Object[]{}, locale)));
        ordersService.save(orders);
        return "redirect:/orders/" + UrlUtil.encodeUrlPathSegment(orders.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("orders", ordersService.findById(id));
        try {
            Set<Map.Entry<Long, String>> customers;
            //uiModel.addAttribute("customers", customersService.findById(id));
            List<Customers> customersList = ordersService.getAllCustomers();
            final Map<Long, String> customersMap = new HashMap<Long, String>();
            if (customersList != null && !customersList.isEmpty()) {
                for (Customers eachCustomer : customersList) {
                    if (eachCustomer != null) {
                        customersMap.put(eachCustomer.getId(), eachCustomer.getName());
                    }
                }
            }
            customers = customersMap.entrySet();
            uiModel.addAttribute("customers", customers);
        } catch (Exception ex){
            logger.warn(ex.toString());
        }


        return "orders/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Orders orders, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Creating orders");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("orders_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("orders", orders);
            return "orders/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("orders_save_success", new Object[]{}, locale)));
        logger.info("Orders id: " + orders.getId());
        ordersService.save(orders);
        return "redirect:/orders/";
    }

    @PreAuthorize("isAuthenticated()") // защита от использовани€ неавторизованным пользователем
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Orders orders = new Orders();
        uiModel.addAttribute("orders", orders);

        return "orders/create";
    }

    // ѕостроничное разбиение информации в гриде
    @ResponseBody
    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    public OrdersGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "rows", required = false) Integer rows,
                               @RequestParam(value = "sidx", required = false) String sortBy,
                               @RequestParam(value = "sord", required = false) String order) {

        logger.info("Listing orders for grid with page: {}, rows: {}", page, rows);
        logger.info("Listing orders for grid with sort: {}, order: {}", sortBy, order);

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

        Page<Orders> ordersPage = ordersService.findAllByPage(pageRequest);

        // Construct the grid data that will return as JSON data
        OrdersGrid ordersGrid = new OrdersGrid();

        ordersGrid.setCurrentPage(ordersPage.getNumber() + 1);
        ordersGrid.setTotalPages(ordersPage.getTotalPages());
        ordersGrid.setTotalRecords(ordersPage.getTotalElements());

        ordersGrid.setOrdersData(Lists.newArrayList(ordersPage.iterator()));

        return ordersGrid;
    }

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
