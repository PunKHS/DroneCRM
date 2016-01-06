package com.crm.controller;

import com.crm.grid.ContactsGrid;
import com.crm.model.Contacts;
import com.crm.model.Customers;
import com.crm.service.ContactsService;
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

@RequestMapping("/contacts")
@Controller
public class ContactsController {
    private final Logger logger = LoggerFactory.getLogger(ContactsController.class);

    private ContactsService contactsService;
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        logger.info("Listing contacts");
        List<Contacts> contacts = contactsService.findAll();
        uiModel.addAttribute("contacts", contacts);
        logger.info("No. of contacts: " + contacts.size());

        return "contacts/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Contacts contacts = contactsService.findById(id);
        uiModel.addAttribute("contacts", contacts);
        return "contacts/show";
    }

    @RequestMapping(value = "/delete/{id}", params = "form", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        contactsService.delete(id);
        logger.info("Contacts was delete");
        return "redirect:/contacts/";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Contacts contacts, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        logger.info("Updating contacts");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("contacts_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("contacts", contacts);
            return "contacts/update";
        }
        logger.info(contacts.getName());
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("contacts_save_success", new Object[]{}, locale)));
        contactsService.save(contacts);
        return "redirect:/contacts/" + UrlUtil.encodeUrlPathSegment(contacts.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("contacts", contactsService.findById(id));
        try {
            Set<Map.Entry<Long, String>> customers;
            List<Customers> customersList = contactsService.getAllCustomers();
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
        return "contacts/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Contacts contacts, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Creating contacts");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("contacts_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("contacts", contacts);
            return "contacts/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("contacts_save_success", new Object[]{}, locale)));
        logger.info("Contacts id: " + contacts.getId());
        contactsService.save(contacts);
        return "redirect:/contacts/";
    }

    @PreAuthorize("isAuthenticated()") // ?????? ?? ????????????? ???????????????? ?????????????
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Contacts contacts = new Contacts();
        uiModel.addAttribute("contacts", contacts);
        try {
            Set<Map.Entry<Long, String>> customers;
            List<Customers> customersList = contactsService.getAllCustomers();
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
        return "contacts/create";
    }

    // ???????????? ????????? ?????????? ? ?????
    @ResponseBody
    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    public ContactsGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "rows", required = false) Integer rows,
                                 @RequestParam(value = "sidx", required = false) String sortBy,
                                 @RequestParam(value = "sord", required = false) String order) {

        logger.info("Listing contacts for grid with page: {}, rows: {}", page, rows);
        logger.info("Listing contacts for grid with sort: {}, order: {}", sortBy, order);

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

        Page<Contacts> contactsPage = contactsService.findAllByPage(pageRequest);

        // Construct the grid data that will return as JSON data
        ContactsGrid contactsGrid = new ContactsGrid();

        contactsGrid.setCurrentPage(contactsPage.getNumber() + 1);
        contactsGrid.setTotalPages(contactsPage.getTotalPages());
        contactsGrid.setTotalRecords(contactsPage.getTotalElements());

        contactsGrid.setContactsData(Lists.newArrayList(contactsPage.iterator()));

        return contactsGrid;
    }

    @Autowired
    public void setContactsService(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
