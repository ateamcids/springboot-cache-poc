package com.example.minipocservicio1.controllers;

import com.example.minipocservicio1.models.DashboardModel;

import com.example.minipocservicio1.models.TaskModel;
import com.example.minipocservicio1.services.interfaces.IDashboardService;
import com.example.minipocservicio1.services.interfaces.ITaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/microServicio1/dashboard")
public class DashboardController {
    private final IDashboardService dashboardService;
    private final ITaskService taskService;


    public DashboardController(IDashboardService dashboardService, ITaskService taskService) {

        this.dashboardService = dashboardService;
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskModel> listar() throws JsonProcessingException, InterruptedException {
        return taskService.request();
    }

    @GetMapping("/test2")
    public List<DashboardModel> listar2() {
        return dashboardService.request();
    }


}
