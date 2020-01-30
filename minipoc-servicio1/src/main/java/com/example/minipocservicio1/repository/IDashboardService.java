package com.example.minipocservicio1.repository;

import com.example.minipocservicio1.models.DashboardModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDashboardService {
    List<DashboardModel> request();
}
