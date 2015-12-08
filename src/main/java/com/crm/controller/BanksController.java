package com.crm.controller;

import com.crm.model.Banks;
import com.crm.service.BanksService;
import com.crm.service.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
        logger.info("!");
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("banks_save_success", new Object[]{}, locale)));
        banksService.save(banks);
        return "redirect:/banks/" + UrlUtil.encodeUrlPathSegment(banks.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("banks", banksService.findById(id));
        return "banks/update";
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
