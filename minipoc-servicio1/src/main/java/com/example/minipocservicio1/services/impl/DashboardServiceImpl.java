package com.example.minipocservicio1.services.impl;

import com.example.minipocservicio1.cachelibrary.repositories.interfaces.ICacheRepositoryOther;
import com.example.minipocservicio1.cachelibrary.services.interfaces.ICacheStoreService;
import com.example.minipocservicio1.models.DashboardModel;

import com.example.minipocservicio1.services.interfaces.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@EnableConfigurationProperties()
public class DashboardServiceImpl implements IDashboardService {
    @Value("${customcache.collection}")
    private String collection;

    @Value("${customcache.hkey}")
    private String hkey;

    ICacheStoreService storeService;
    List<DashboardModel> listDashboard;
    @Autowired
    ICacheRepositoryOther repositoryOther;

    public DashboardServiceImpl(ICacheStoreService storeService) {
        this.storeService = storeService;
    }


    @Override
    public List<DashboardModel> request() {

        System.out.println(collection);
        System.out.println(hkey);
        listDashboard = (List<DashboardModel>) storeService.find("dashboard2", "lista", List.class);
        if (listDashboard != null && !listDashboard.isEmpty()) {
            return listDashboard;
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl
                    = "http://localhost:8080/api/v1/micro-servicio2/listar-cache-repository";
            ResponseEntity<List> response
                    = restTemplate.getForEntity(fooResourceUrl, List.class);
            listDashboard = (List<DashboardModel>) response.getBody();
            return listDashboard;
        }
    }
}
