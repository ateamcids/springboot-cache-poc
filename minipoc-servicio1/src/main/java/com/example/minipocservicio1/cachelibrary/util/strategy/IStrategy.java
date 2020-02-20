package com.example.minipocservicio1.cachelibrary.util.strategy;

import com.example.minipocservicio1.cachelibrary.model.CacheModel;
import com.example.minipocservicio1.cachelibrary.repositories.interfaces.ICacheRepository;

public interface IStrategy {
    CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository);
}
