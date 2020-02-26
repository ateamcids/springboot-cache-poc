package com.telecom.ateam.minipoc.services;

import com.example.cacheLibrary.services.impl.CacheStoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cacheLibrary.model.CacheResponseStatus;
import com.example.cacheLibrary.services.interfaces.ICacheStoreService;
import com.telecom.ateam.minipoc.models.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Service
public class TaskServiceImpl implements ITaskService {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setTimeZone(DEFAULT_TIMEZONE);
    }

    static String fooResourceUrl = "http://localhost:3000/tasks";


    ICacheStoreService storeService;

    public TaskServiceImpl(ICacheStoreService storeService) {
        this.storeService = storeService;
    }

    public TaskServiceImpl() {

    }

    public List<TaskModel> request() throws JsonProcessingException, InterruptedException {

        ResponseEntity<List> response = makeRequestParams();
        HttpHeaders headers = response.getHeaders();
        String etag = headers.getETag();
        List<TaskModel> lista = (List<TaskModel>) response.getBody();
        CacheResponseStatus a = null;
        if (lista != null && !lista.isEmpty()) {
            a = storeService.add(lista, fooResourceUrl, headers);
        }

        if (response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
            //todo recomendado enviar un 304
            lista = (List) storeService.find(fooResourceUrl, etag, List.class);

        }

        return lista;
    }

   /* public Mono<List<TaskModel>> requestReactive() {
        ResponseEntity<List> response = makeRequest();
        HttpHeaders headers = response.getHeaders();
        String etag = headers.getETag();

        // Leer
        return storeService.findReactive(fooResourceUrl, etag, List.class);
    }*/

    public List<TaskModel> requestReactive() throws JsonProcessingException, InterruptedException {


        ResponseEntity<List> response = makeRequestParams();
        HttpHeaders headers = response.getHeaders();
        String etag = headers.getETag();

        List<TaskModel> lista = response.getBody();

        if (lista != null && !lista.isEmpty()) {
            storeService.addReactive(lista, fooResourceUrl, headers).block();
        }

        if (response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
            lista = (List<TaskModel>) storeService.findReactive(fooResourceUrl, etag, List.class).block();
        }

        return lista;
    }

    public Mono<List<TaskModel>> requestReactivePut() {
        ResponseEntity<List> response = makeRequest();
        HttpHeaders headers = response.getHeaders();
        String etag = headers.getETag();
        List<TaskModel> lista;
        lista = (List<TaskModel>) response.getBody();
        if (lista != null && !lista.isEmpty()) {
            try {
                storeService.addReactive(lista, fooResourceUrl, headers).subscribe(y -> System.out.println(y.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Mono.just(lista);
    }


    public List<TaskModel> requestExpires(int expires) throws JsonProcessingException, InterruptedException {


        ResponseEntity<List> response = makeRequest();
        HttpHeaders headers = response.getHeaders();
        String etag = headers.getETag();

        List result = (List) storeService.find(fooResourceUrl, etag, List.class);

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
        ResponseEntity<List> response
                = restTemplate.getForEntity(fooResourceUrl, List.class);
        return response;
    }

    private ResponseEntity<List> makeRequestParams() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<List> response = null;
        String result;


        try {
            result = (String) storeService.find(fooResourceUrl, String.class);
            if (result != null && !result.isEmpty()) {
                String eTag = storeService.first(fooResourceUrl);
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
