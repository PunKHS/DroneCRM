package com.crm.controller;

import com.crm.model.Banks;
import com.crm.service.BanksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/banks")
@Controller
public class BanksController {
    private final Logger logger = LoggerFactory.getLogger(BanksController.class);

    private BanksService banksService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        logger.info("Listing banks");

        List<Banks> banks = banksService.findAll();
        uiModel.addAttribute("banks", banks);

        logger.info("No. of banks: " + banks.size());

        return "banks/list";
    }

    @Autowired
    public void setBanksService(BanksService banksService) {
        this.banksService = banksService;
    }
}
