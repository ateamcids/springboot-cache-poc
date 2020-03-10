package com.telecom.ateam.minipoc.services;

import com.example.cachelibrary.services.interfaces.ICacheStoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.models.DashboardModel;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Service
public class DashboardServiceImpl implements IDashboardService {

  static String fooResourceUrl = "http://200.61.215.10:8090/dashboard";

  @Autowired ICacheStoreService storeService;

  public DashboardServiceImpl() {}

  public DashboardServiceImpl(ICacheStoreService storeService) {
    this.storeService = storeService;
  }

  public List<DashboardModel> request() throws JsonProcessingException, InterruptedException {

    ResponseEntity<List> response = makeRequest();
    HttpHeaders headers = response.getHeaders();
    String etag = headers.getETag();

    List result = (List) storeService.find(fooResourceUrl, etag, List.class);

    if (result != null && !result.isEmpty()) {
      List<DashboardModel> lista = (List<DashboardModel>) result;
      return lista;
    }

    List<DashboardModel> lista = (List<DashboardModel>) response.getBody();
    if (lista != null && !lista.isEmpty()) {
      storeService.add(lista, fooResourceUrl, headers);
    }

    return lista;
  }

  public Mono<List<DashboardModel>> requestReactive() {
    ResponseEntity<List> response = makeRequest();
    HttpHeaders headers = response.getHeaders();
    String etag = headers.getETag();

    // Leer
    return storeService.findReactive(fooResourceUrl, etag, List.class);
  }

  public Mono<List<DashboardModel>> requestReactivePut() {
    ResponseEntity<List> response = makeRequest();
    HttpHeaders headers = response.getHeaders();
    String etag = headers.getETag();
    List<DashboardModel> lista;
    lista = (List<DashboardModel>) response.getBody();
    if (lista != null && !lista.isEmpty()) {
      try {
        storeService
            .addReactive(lista, fooResourceUrl, headers)
            .subscribe(y -> System.out.println(y.toString()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return Mono.just(lista);
  }

  public List<DashboardModel> requestExpires(int expires)
      throws JsonProcessingException, InterruptedException {

    ResponseEntity<List> response = makeRequest();
    HttpHeaders headers = response.getHeaders();
    String etag = headers.getETag();

    List result = (List) storeService.find(fooResourceUrl, etag, List.class);

    if (result != null && !result.isEmpty()) {
      List<DashboardModel> lista = (List<DashboardModel>) result;
      return lista;
    }

    List<DashboardModel> lista = (List<DashboardModel>) response.getBody();
    if (lista != null && !lista.isEmpty()) {
      storeService.addCollection(fooResourceUrl, etag, lista, expires, TimeUnit.SECONDS);
    }

    return lista;
  }

  public List<DashboardModel> requestStandard(String collection, String hkey) {

    ResponseEntity<List> response = makeRequest();

    List result = (List) storeService.find(collection, hkey, List.class);

    if (result != null && !result.isEmpty()) {
      List<DashboardModel> lista = (List<DashboardModel>) result;
      return lista;
    }
    List<DashboardModel> lista = (List<DashboardModel>) response.getBody();
    if (lista != null && !lista.isEmpty()) {
      storeService.addCollection(collection, hkey, lista);
    }
    return lista;
  }

  private ResponseEntity<List> makeRequest() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<List> response = restTemplate.getForEntity(fooResourceUrl, List.class);
    return response;
  }
}
