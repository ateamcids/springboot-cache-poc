package com.telecom.ateam.minipoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.cachelibrary.repositories.interfaces.ICacheRepository;
import com.telecom.ateam.minipoc.cachelibrary.services.interfaces.ICacheStoreService;
import com.telecom.ateam.minipoc.core.interfaces.ITestService;
import com.telecom.ateam.minipoc.models.DashboardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TestServiceImpl implements ITestService {

    ICacheStoreService storeService;
    public TestServiceImpl(ICacheStoreService storeService) {
        this.storeService = storeService;
    }

    public List<DashboardModel> request() {
/*        List result = (List) storeService.find("dashboard","lista",List.class);

        if(result != null && !result.isEmpty()){
            List<DashboardModel> lista = (List<DashboardModel>) result;
            return lista;
        }*/


        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://200.61.215.10:8090/dashboard";
        ResponseEntity<List> response
                = restTemplate.getForEntity(fooResourceUrl, List.class);
        HttpHeaders headers = response.getHeaders();


        List<DashboardModel> lista = (List<DashboardModel>) response.getBody();
        if(lista != null && !lista.isEmpty()){
            //storeService.addCollection("dashboard1", "lista", lista);
           // storeService.addCollection("dashboard", "lista", lista,15, TimeUnit.SECONDS);
            storeService.add(lista,fooResourceUrl,headers);
        }

        return lista;
    }
}
