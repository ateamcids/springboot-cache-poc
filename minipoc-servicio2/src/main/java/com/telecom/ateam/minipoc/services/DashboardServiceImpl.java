package com.telecom.ateam.minipoc.services;

import com.telecom.ateam.minipoc.core.interfaces.IDashboardService;
import com.telecom.ateam.minipoc.models.DashboardModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class DashboardServiceImpl implements IDashboardService {
    @Override
    @Cacheable("dashboard")
    public List<DashboardModel> request() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://200.61.215.10:8090/dashboard";
        ResponseEntity<List> response
                = restTemplate.getForEntity(fooResourceUrl, List.class);
        return (List<DashboardModel>) response.getBody();
    }
}
