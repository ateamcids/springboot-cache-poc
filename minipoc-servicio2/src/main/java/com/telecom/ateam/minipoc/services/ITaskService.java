package com.telecom.ateam.minipoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.models.TaskModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITaskService {
    List<TaskModel> request() throws JsonProcessingException, InterruptedException;

    List<TaskModel> requestReactive() throws JsonProcessingException, InterruptedException;

    Mono<List<TaskModel>> requestReactivePut();

   /* List<TaskModel> requestReactiveExpires() throws JsonProcessingException, InterruptedException;*/

    List<TaskModel> requestExpires();

    List<TaskModel> requestExpiresWithParams(int expires) throws JsonProcessingException, InterruptedException;

    List<TaskModel> requestStandard(String collection, String hkey);


}
