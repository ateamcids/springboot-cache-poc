package com.telecom.ateam.minipoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.cachelibrary.model.CacheResponseStatus;
import com.telecom.ateam.minipoc.cachelibrary.services.interfaces.ICacheStoreService;
import com.telecom.ateam.minipoc.models.TaskModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TaskServiceImpl implements ITaskService {

    static String fooResourceUrl = "http://172.16.18.154:3000/tasks";

    ICacheStoreService storeService;

    public TaskServiceImpl(ICacheStoreService storeService) {
        this.storeService = storeService;
    }

    public List<TaskModel> request() throws JsonProcessingException, InterruptedException {

        ResponseEntity<List> response = makeRequest();
        HttpHeaders headers = response.getHeaders();
   /*     String etag = headers.getETag();

        List result = (List) storeService.find(fooResourceUrl, etag, List.class);

        if (result != null && !result.isEmpty()) {
            List<TaskModel> lista = (List<TaskModel>) result;
            return lista;
        }*/

        List<TaskModel> lista = (List<TaskModel>) response.getBody();
        CacheResponseStatus a = null;
        if (lista != null && !lista.isEmpty()) {
          a = storeService.add(lista, fooResourceUrl, headers);
        }

        if (a.getStatus() == HttpStatus.NOT_MODIFIED){
            //todo recomendado enviar un 304
            return null;
        }

        return lista;
    }

    public Mono<List<TaskModel>> requestReactive() {
        ResponseEntity<List> response = makeRequest();
        HttpHeaders headers = response.getHeaders();
        String etag = headers.getETag();

        // Leer
        return storeService.findReactive(fooResourceUrl, etag, List.class);
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
}