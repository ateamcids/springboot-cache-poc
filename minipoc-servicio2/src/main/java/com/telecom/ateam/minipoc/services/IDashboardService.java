package com.telecom.ateam.minipoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.models.DashboardModel;
import java.util.List;
import reactor.core.publisher.Mono;

public interface IDashboardService {
  List<DashboardModel> request() throws JsonProcessingException, InterruptedException;

  Mono<List<DashboardModel>> requestReactive();

  Mono<List<DashboardModel>> requestReactivePut();

  List<DashboardModel> requestExpires(int expires)
      throws JsonProcessingException, InterruptedException;

  List<DashboardModel> requestStandard(String collection, String hkey);
}
