package com.example.cacheLibrary.util.strategy.reactive;

import com.example.cacheLibrary.model.CacheModel;
import com.example.cacheLibrary.repositories.interfaces.ICacheRepository;
import com.example.cacheLibrary.util.strategy.CacheControlStrategyResponse;
import com.example.cacheLibrary.util.strategy.IStrategy;
import org.springframework.http.HttpStatus;

public class ReactiveCacheControlNoStore implements IStrategy {

    public CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) {
        return new CacheControlStrategyResponse(false,null, HttpStatus.OK);
    }
}
