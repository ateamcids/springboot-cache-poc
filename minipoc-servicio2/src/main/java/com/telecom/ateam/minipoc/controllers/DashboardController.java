package com.telecom.ateam.minipoc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.core.interfaces.IDashboardService;
import com.telecom.ateam.minipoc.core.interfaces.ITestService;
import com.telecom.ateam.minipoc.models.DashboardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final IDashboardService dashboardService;
    final ITestService testService;

    public DashboardController(IDashboardService dashboardService, ITestService testService) {
        this.dashboardService = dashboardService;
        this.testService = testService;
    }

    @GetMapping
    public List<DashboardModel> listar(){
        return dashboardService.request();
    }

    @GetMapping("listar-cache-repository")
    public List<DashboardModel> testCacheRepository() throws JsonProcessingException, InterruptedException {
        return testService.request();
    }
}
