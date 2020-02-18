package com.example.minipocservicio1.cachelibrary.util.strategy;


import com.example.minipocservicio1.cachelibrary.model.CacheModel;
import com.example.minipocservicio1.cachelibrary.repositories.interfaces.ICacheRepository;
import org.springframework.http.HttpStatus;

public class CacheControlNoStore implements IStrategy {

    @Override
    public CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) {
        return new CacheControlStrategyResponse(false,null, HttpStatus.OK);
    }
}
