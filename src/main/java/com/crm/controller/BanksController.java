package com.crm.controller;

import com.crm.grid.BanksGrid;
import com.crm.localization.Message;
import com.crm.model.Banks;
import com.crm.service.BanksService;
import com.crm.url.UrlUtil;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
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
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

@RequestMapping("/banks")
@Controller
public class BanksController {
    private final Logger logger = LoggerFactory.getLogger(BanksController.class);

    private BanksService banksService;
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        logger.info("Listing banks");

        List<Banks> banks = banksService.findAll();
        uiModel.addAttribute("banks", banks);

        logger.info("No. of banks: " + banks.size());

        return "banks/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Banks banks = banksService.findById(id);
        uiModel.addAttribute("banks", banks);

        return "banks/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Banks banks, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        logger.info("Updating banks");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("banks_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("banks", banks);
            return "banks/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("banks_save_success", new Object[]{}, locale)));
        banksService.save(banks);
        return "redirect:/banks/" + UrlUtil.encodeUrlPathSegment(banks.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("banks", banksService.findById(id));
        return "banks/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Banks banks, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, @RequestParam(value="file", required=false) Part file) {
        logger.info("Creating banks");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("banks_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("banks", banks);
            return "banks/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("banks_save_success", new Object[]{}, locale)));

        logger.info("Banks id: " + banks.getId());

        banksService.save(banks);
        return "redirect:/banks/";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Banks banks = new Banks();
        uiModel.addAttribute("banks", banks);

        return "banks/create";
    }

    @ResponseBody
    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces="application/json")
    public BanksGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "rows", required = false) Integer rows,
                                @RequestParam(value = "sidx", required = false) String sortBy,
                                @RequestParam(value = "sord", required = false) String order) {

        logger.info("Listing banks for grid with page: {}, rows: {}", page, rows);
        logger.info("Listing banks for grid with sort: {}, order: {}", sortBy, order);

        // Process order by
        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && orderBy.equals("name"))
            orderBy = "name";

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

        Page<Banks> banksPage = banksService.findAllByPage(pageRequest);

        // Construct the grid data that will return as JSON data
        BanksGrid banksGrid = new BanksGrid();

        banksGrid.setCurrentPage(banksPage.getNumber() + 1);
        banksGrid.setTotalPages(banksPage.getTotalPages());
        banksGrid.setTotalRecords(banksPage.getTotalElements());

        banksGrid.setBanksData(Lists.newArrayList(banksPage.iterator()));

        return banksGrid;
    }

    @Autowired
    public void setBanksService(BanksService banksService) {
        this.banksService = banksService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
