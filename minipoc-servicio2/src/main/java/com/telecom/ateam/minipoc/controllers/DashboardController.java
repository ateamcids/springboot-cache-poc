package com.telecom.ateam.minipoc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.services.IDashboardService;
import com.telecom.ateam.minipoc.models.DashboardModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/microservicio2/dashboard")
public class DashboardController {
    final IDashboardService dashboardService;

    public DashboardController( IDashboardService dashboardService) {
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
    @GetMapping("/reactivePut")
    public Mono<List<DashboardModel>> listarReactivePut() {
        return dashboardService.requestReactivePut();
    }

    @GetMapping("/expires/{expires}")
    public List<DashboardModel> listarExpires(@PathVariable int expires) throws JsonProcessingException, InterruptedException {
        return dashboardService.requestExpires(expires);
    }

    @GetMapping("/standard")
    public List<DashboardModel> dashboard() {
        return dashboardService.requestStandard();
    }

}
