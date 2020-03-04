package com.telecom.ateam.minipoc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.models.DashboardModel;
import com.telecom.ateam.minipoc.services.IDashboardService;
import com.telecom.ateam.minipoc.services.ITaskService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
@RestController
@RequestMapping("/api/v1/microServicio2/dashboard")
public class DashboardController {

    private final IDashboardService dashboardService;


    public DashboardController(IDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public List<DashboardModel> listar() throws JsonProcessingException, InterruptedException {
        return dashboardService.request();
    }


    @GetMapping("/reactive")
    public Mono<List<DashboardModel>> listarReactive() {
        return dashboardService.requestReactive();
    }


    @GetMapping("/expires/{expires}")
    public List<DashboardModel> listarExpires(@PathVariable int expires) throws JsonProcessingException, InterruptedException {
        return dashboardService.requestExpires(expires);
    }

    @GetMapping("/standard")
    public List<DashboardModel> dashboard(@RequestParam(name = "collection") String collection, @RequestParam String hkey) {
        return dashboardService.requestStandard(collection, hkey);
    }

}
