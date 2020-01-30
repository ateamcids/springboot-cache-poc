package com.example.minipocservicio1.controllers;

import com.example.minipocservicio1.models.DashboardModel;

import com.example.minipocservicio1.repository.IDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/micro-servicio1/dashboard")
public class DashboardController {
    private final IDashboardService dashboardService;


    public DashboardController(IDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public List<DashboardModel> listar(){
        System.out.println("Controller");
        return dashboardService.request();
    }
}
