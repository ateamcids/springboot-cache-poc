package com.telecom.ateam.minipoc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.models.TaskModel;
import com.telecom.ateam.minipoc.services.ITaskService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/microServicio2")
public class TaskController {
  private final ITaskService taskService;

  public TaskController(ITaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public List<TaskModel> listar() throws JsonProcessingException, InterruptedException {
    return taskService.request();
  }

  @GetMapping("/maxAge")
  public List<TaskModel> listarMaxAge() throws JsonProcessingException, InterruptedException {
    return taskService.requestExpires();
  }

  @GetMapping("/reactiveMaxAge")
  public List<TaskModel> listarReactiveMaxAge()
      throws JsonProcessingException, InterruptedException {
    return taskService.requestReactiveExpires();
  }

  @GetMapping("/maxAgeReactive")
  public List<TaskModel> listarMaxAgeReactive()
      throws JsonProcessingException, InterruptedException {
    return taskService.requestReactiveExpires();
  }

  @GetMapping("/reactivePut")
  public Mono<List<TaskModel>> saveReactivePut() {
    // taskService.requestReactivePut().subscribe( (x) -> x  );
    return taskService.requestReactivePut();
  }

  @GetMapping("/reactiveTasks")
  public List<TaskModel> getReactiveTasks() throws JsonProcessingException, InterruptedException {
    return taskService.requestReactive();
  }
}
