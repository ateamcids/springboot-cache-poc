package com.example.cachelibrary.util.strategy.reactive;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.util.strategy.model.CacheControlStrategyResponse;
import org.springframework.http.HttpStatus;

public class ReactiveCacheControlNoStore implements IReactiveStrategy {

  public CacheControlStrategyResponse cacheControlStrategy(
      CacheModel cacheModel, ICacheRepository cacheRepository) {
    return new CacheControlStrategyResponse(false, null, HttpStatus.OK);
  }
}
