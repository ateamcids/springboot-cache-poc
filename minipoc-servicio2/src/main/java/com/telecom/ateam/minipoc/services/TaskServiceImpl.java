package com.telecom.ateam.minipoc.services;

import com.example.cachelibrary.model.CacheResponseStatus;
import com.example.cachelibrary.services.interfaces.ICacheStoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.ateam.minipoc.models.TaskModel;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Service
public class TaskServiceImpl implements ITaskService {

  private static final ObjectMapper OBJECT_MAPPER;
  private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");

  static {
    OBJECT_MAPPER = new ObjectMapper();
    OBJECT_MAPPER.setTimeZone(DEFAULT_TIMEZONE);
  }

  static String fooResourceUrl = "http://localhost:3000/tasks";

  private final ICacheStoreService storeService;

  public TaskServiceImpl(ICacheStoreService storeService) {

    this.storeService = storeService;
  }

  public List<TaskModel> request() throws JsonProcessingException, InterruptedException {

    ResponseEntity<List> response = makeRequestSendParams();
    HttpHeaders headers = response.getHeaders();
    String etag = headers.getETag();
    List<TaskModel> lista = (List<TaskModel>) response.getBody();
    if (lista != null && !lista.isEmpty()) {
      storeService.add(lista, fooResourceUrl, headers);
    }

    if (response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
      // todo recomendado enviar un 304
      lista = (List) storeService.find(fooResourceUrl, etag, List.class);
    }

    return lista;
  }

  public List<TaskModel> requestReactive() throws JsonProcessingException, InterruptedException {

    ResponseEntity<List> response = makeRequestSendParams();
    HttpHeaders headers = response.getHeaders();
    String etag = headers.getETag();

    List<TaskModel> lista = response.getBody();

    if (lista != null && !lista.isEmpty()) {
      storeService.addReactiveCollection(lista, fooResourceUrl, headers).block();
    }

    if (response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
      lista = (List<TaskModel>) storeService.findReactive(fooResourceUrl, etag, List.class).block();
    }

    return lista;
  }

  public Mono<List<TaskModel>> requestReactivePut() {
    ResponseEntity<List> response = makeRequest();
    HttpHeaders headers = response.getHeaders();
    List<TaskModel> lista;
    lista = (List<TaskModel>) response.getBody();
    if (lista != null && !lista.isEmpty()) {
      try {
        storeService
            .addReactiveCollection(lista, fooResourceUrl, headers)
            .subscribe(y -> System.out.println(y.toString()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return Mono.just(lista);
  }

  public List<TaskModel> requestExpires() throws JsonProcessingException, InterruptedException {

    List result = (List) storeService.find(fooResourceUrl, fooResourceUrl, List.class);

    if (result != null && !result.isEmpty()) {
      List<TaskModel> lista = (List<TaskModel>) result;
      return lista;
    }

    ResponseEntity<List> response = makeRequest();
    HttpHeaders headers = response.getHeaders();
    String etag = headers.getETag();

    List<TaskModel> lista = (List<TaskModel>) response.getBody();
    if (lista != null && !lista.isEmpty()) {
      storeService.add(lista, fooResourceUrl, headers);
    }

    return lista;
  }

  public List<TaskModel> requestReactiveExpires()
      throws JsonProcessingException, InterruptedException {
    List result =
        (List) storeService.findReactive(fooResourceUrl, fooResourceUrl, List.class).block();

    if (result != null && !result.isEmpty()) {
      List<TaskModel> lista = (List<TaskModel>) result;
      return lista;
    }

    ResponseEntity<List> response = makeRequest();
    HttpHeaders headers = response.getHeaders();
    String etag = headers.getETag();

    List<TaskModel> lista = (List<TaskModel>) response.getBody();
    if (lista != null && !lista.isEmpty()) {
      storeService.addReactive(lista, fooResourceUrl, headers).block();
    }
    return lista;
  }

  public List<TaskModel> requestExpiresWithParams(int expires)
      throws JsonProcessingException, InterruptedException {

    ResponseEntity<List> response = makeRequest();
    HttpHeaders headers = response.getHeaders();
    String etag = headers.getETag();

    List result = (List) storeService.find(fooResourceUrl, List.class);

    if (result != null && !result.isEmpty()) {
      List<TaskModel> lista = (List<TaskModel>) result;
      return lista;
    }

    List<TaskModel> lista = (List<TaskModel>) response.getBody();
    if (lista != null && !lista.isEmpty()) {
      storeService.addCollection(fooResourceUrl, etag, lista, expires, TimeUnit.SECONDS);
    }

    return lista;
  }

  public List<TaskModel> requestStandard(String collection, String hkey) {

    ResponseEntity<List> response = makeRequest();

    List result = (List) storeService.find(collection, hkey, List.class);

    if (result != null && !result.isEmpty()) {
      List<TaskModel> lista = (List<TaskModel>) result;
      return lista;
    }
    List<TaskModel> lista = (List<TaskModel>) response.getBody();
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

  private ResponseEntity<List> makeRequestSendParams() {
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<List> response = null;
    String result;

    try {
      result = (String) storeService.find(fooResourceUrl, String.class);
      if (result != null && !result.isEmpty()) {
        final String eTag = storeService.first(fooResourceUrl);
        headers.set("If-None-Match", eTag);
        HttpEntity entity = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, entity, List.class);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return response;
  }
}
