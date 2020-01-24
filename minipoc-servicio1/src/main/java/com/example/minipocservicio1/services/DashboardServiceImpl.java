package com.example.minipocservicio1.services;

import com.example.minipocservicio1.models.DashboardModel;

import com.example.minipocservicio1.repository.IDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class DashboardServiceImpl  implements IDashboardService {
    @Override
    public List<DashboardModel> request() {
        System.out.println("Servicio");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/api/v1/micro-servicio2/listar-cache-repository";
        ResponseEntity<List> response
                = restTemplate.getForEntity(fooResourceUrl, List.class);
        return (List<DashboardModel>) response.getBody();
    }
}
