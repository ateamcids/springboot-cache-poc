package com.example.minipocservicio1.services;

import com.example.minipocservicio1.cachelibrary.repositories.interfaces.ICacheRepositoryOther;
import com.example.minipocservicio1.cachelibrary.services.interfaces.ICacheStoreService;
import com.example.minipocservicio1.models.DashboardModel;

import com.example.minipocservicio1.repository.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class DashboardServiceImpl implements IDashboardService {
    ICacheStoreService storeService;
    List<DashboardModel> listDashboard;
    @Autowired
    ICacheRepositoryOther repositoryOther;

    public DashboardServiceImpl(ICacheStoreService storeService) {
        this.storeService = storeService;
    }


    @Override
    public List<DashboardModel> request() {

        System.out.println("Servicio");
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
