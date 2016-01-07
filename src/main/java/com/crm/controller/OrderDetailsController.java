package com.crm.controller;

import com.crm.grid.OrderDetailsGrid;
import com.crm.model.Customers;
import com.crm.model.OrderDetails;
import com.crm.model.Orders;
import com.crm.model.Products;
import com.crm.service.OrderDetailsService;
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

@RequestMapping("/orderDetails")
@Controller
public class OrderDetailsController {
    private final Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);

    private OrderDetailsService orderDetailsService;
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        logger.info("Listing orderDetails");

        List<OrderDetails> orderDetails = orderDetailsService.findAll();
        uiModel.addAttribute("orderDetails", orderDetails);

        logger.info("No. of orderDetails: " + orderDetails.size());

        return "orderDetails/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        OrderDetails orderDetails = orderDetailsService.findById(id);
        uiModel.addAttribute("orderDetails", orderDetails);

        return "orderDetails/show";
    }

    @RequestMapping(value = "/delete/{id}", params = "form", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        orderDetailsService.delete(id);
        logger.info("OrderDetails was delete");
        return "redirect:/orderDetails/";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid OrderDetails orderDetails, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        logger.info("Updating orderDetails");
        try{if (bindingResult.hasErrors()) {
            logger.warn("!!!!11111");
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("orderDetails_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("orderDetails", orderDetails);
            return "orderDetails/update";
        }
        logger.info(orderDetails.getOrders().toString());
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("orderDetails_save_success", new Object[]{}, locale)));
        orderDetailsService.save(orderDetails);}
        catch (Exception ex){
            logger.info(ex.toString());
        }
//        return "redirect:/orderDetails/" + UrlUtil.encodeUrlPathSegment(orderDetails.getId().toString(),
//                httpServletRequest);
        return "redirect:/orders/";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("orderDetails", orderDetailsService.findById(id));
        try {
            Set<Map.Entry<Long, String>> products;
            List<Products> productsList = orderDetailsService.getAllProducts();
            final Map<Long, String> productsMap = new HashMap<Long, String>();
            if (productsList != null && !productsList.isEmpty()) {
                for (Products eachProducts : productsList) {
                    if (eachProducts != null) {
                        productsMap.put(eachProducts.getId(), eachProducts.getName());
                    }
                }
            }
            products = productsMap.entrySet();
            uiModel.addAttribute("products", products);
        } catch (Exception ex){
            logger.warn(ex.toString());
        }
        try {
            Set<Map.Entry<Long, String>> orders;
            List<Orders> ordersList = orderDetailsService.getAllOrders();
            final Map<Long, String> ordersMap = new HashMap<Long, String>();
            if (ordersList != null && !ordersList.isEmpty()) {
                for (Orders eachOrders : ordersList) {
                    if (eachOrders != null) {
                        ordersMap.put(eachOrders.getId(), eachOrders.getNumber());
                    }
                }
            }
            orders = ordersMap.entrySet();
            uiModel.addAttribute("orders", orders);
        } catch (Exception ex){
            logger.warn(ex.toString());
        }
        return "orderDetails/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid OrderDetails orderDetails, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Creating orderDetails");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("orderDetails_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("orderDetails", orderDetails);
            return "orderDetails/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("orderDetails_save_success", new Object[]{}, locale)));
        logger.info("OrderDetails id: " + orderDetails.getId());
        orderDetailsService.save(orderDetails);
        return "redirect:/orderDetails/";
    }

    @PreAuthorize("isAuthenticated()") // защита от использовани€ неавторизованным пользователем
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        OrderDetails orderDetails = new OrderDetails();
        uiModel.addAttribute("orderDetails", orderDetails);
        try {
            Set<Map.Entry<Long, String>> products;
            List<Products> productsList = orderDetailsService.getAllProducts();
            final Map<Long, String> productsMap = new HashMap<Long, String>();
            if (productsList != null && !productsList.isEmpty()) {
                for (Products eachProducts : productsList) {
                    if (eachProducts != null) {
                        productsMap.put(eachProducts.getId(), eachProducts.getName());
                    }
                }
            }
            products = productsMap.entrySet();
            uiModel.addAttribute("products", products);
        } catch (Exception ex){
            logger.warn(ex.toString());
        }
        try {
            Set<Map.Entry<Long, String>> orders;
            List<Orders> ordersList = orderDetailsService.getAllOrders();
            final Map<Long, String> ordersMap = new HashMap<Long, String>();
            if (ordersList != null && !ordersList.isEmpty()) {
                for (Orders eachOrders : ordersList) {
                    if (eachOrders != null) {
                        ordersMap.put(eachOrders.getId(), eachOrders.getNumber());
                    }
                }
            }
            orders = ordersMap.entrySet();
            uiModel.addAttribute("orders", orders);
        } catch (Exception ex){
            logger.warn(ex.toString());
        }
        return "orderDetails/create";
    }

    // ѕостроничное разбиение информации в гриде
    @ResponseBody
    @RequestMapping(value = "/listgrid{id}", method = RequestMethod.GET, produces = "application/json")
    public OrderDetailsGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "rows", required = false) Integer rows,
                                  @RequestParam(value = "sidx", required = false) String sortBy,
                                  @RequestParam(value = "sord", required = false) String order,
                                     @PathVariable("id") Long id) {

        logger.info("Listing orderDetails for grid with page: {}, rows: {}", page, rows);
        logger.info("Listing orderDetails for grid with sort: {}, order: {}", sortBy, order);

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

        List<OrderDetails> orderDetailsPage = orderDetailsService.findByOrdersId(id);

        // Construct the grid data that will return as JSON data
        OrderDetailsGrid orderDetailsGrid = new OrderDetailsGrid();

//        orderDetailsGrid.setCurrentPage(orderDetailsPage.getNumber() + 1);
//        orderDetailsGrid.setTotalPages(orderDetailsPage.getTotalPages());
//        orderDetailsGrid.setTotalRecords(orderDetailsPage.getTotalElements());

        orderDetailsGrid.setOrderDetailsData(Lists.newArrayList(orderDetailsPage.iterator()));

        return orderDetailsGrid;
    }

    @Autowired
    public void setOrderDetailsService(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
