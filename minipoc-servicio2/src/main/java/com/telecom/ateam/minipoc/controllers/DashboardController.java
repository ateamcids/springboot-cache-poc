package com.telecom.ateam.minipoc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.models.TaskModel;
import com.telecom.ateam.minipoc.services.IDashboardService;
import com.telecom.ateam.minipoc.models.DashboardModel;
import com.telecom.ateam.minipoc.services.ITaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/microServicio2/dashboard")
public class DashboardController {
    final IDashboardService dashboardService;
    final ITaskService taskService;

    public DashboardController(IDashboardService dashboardService, ITaskService taskService) {
        this.dashboardService = dashboardService;
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskModel> listar() throws JsonProcessingException, InterruptedException {
        return taskService.request();
    }

    @GetMapping("/reactive")
    public Mono<List<DashboardModel>> listarReactive() {
        return dashboardService.requestReactive();
    }

    @GetMapping("/reactivePut")
    public Mono<List<TaskModel>> listarReactivePut() {
       // taskService.requestReactivePut().subscribe( (x) -> x  );
        return taskService.requestReactivePut();
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
