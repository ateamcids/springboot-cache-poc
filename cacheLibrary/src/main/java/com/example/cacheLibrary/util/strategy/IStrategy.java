package com.example.cacheLibrary.util.strategy;

import com.example.cacheLibrary.model.CacheModel;
import com.example.cacheLibrary.repositories.interfaces.ICacheRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IStrategy {
    CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) throws JsonProcessingException, InterruptedException;
}
