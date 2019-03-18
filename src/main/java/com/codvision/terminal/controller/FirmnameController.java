package com.codvision.terminal.controller;

import com.codvision.terminal.service.FirmnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iot/iotfirmmgr")
public class FirmnameController {
    @Autowired
    FirmnameService firmnameService;
}
