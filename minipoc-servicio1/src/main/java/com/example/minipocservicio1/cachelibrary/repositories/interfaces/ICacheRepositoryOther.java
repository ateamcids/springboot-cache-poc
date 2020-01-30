package com.example.minipocservicio1.cachelibrary.repositories.interfaces;

import com.example.minipocservicio1.models.DashboardModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICacheRepositoryOther extends CrudRepository<DashboardModel, Long> {
    void save(List<? extends DashboardModel> dashboard);
}
