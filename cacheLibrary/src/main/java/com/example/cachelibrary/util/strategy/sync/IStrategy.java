package com.example.cachelibrary.util.strategy.sync;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.util.strategy.model.CacheControlStrategyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IStrategy {
  CacheControlStrategyResponse cacheControlStrategy(
      CacheModel cacheModel, ICacheRepository cacheRepository)
      throws JsonProcessingException, InterruptedException;
}
