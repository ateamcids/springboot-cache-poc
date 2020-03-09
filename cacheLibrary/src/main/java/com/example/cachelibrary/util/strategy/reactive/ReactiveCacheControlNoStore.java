package com.example.cachelibrary.util.strategy.reactive;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.util.strategy.CacheControlStrategyResponse;
import com.example.cachelibrary.util.strategy.IStrategy;
import org.springframework.http.HttpStatus;

public class ReactiveCacheControlNoStore implements IStrategy {

  public CacheControlStrategyResponse cacheControlStrategy(
      CacheModel cacheModel, ICacheRepository cacheRepository) {
    return new CacheControlStrategyResponse(false, null, HttpStatus.OK);
  }
}
