package com.telecom.ateam.minipoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.models.TaskModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITaskService {
    List<TaskModel> request() throws JsonProcessingException, InterruptedException;

    List<TaskModel> requestReactive() throws JsonProcessingException, InterruptedException;

   // Flux<List<TaskModel>> requestReactiveFlux() throws JsonProcessingException, InterruptedException;

    Mono<List<TaskModel>> requestReactivePut();


    List<TaskModel> requestExpires(int expires) throws JsonProcessingException, InterruptedException;

    List<TaskModel> requestExpires2();

    List<TaskModel> requestStandard(String collection, String hkey);


}
