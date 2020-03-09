package com.example.cachelibrary.util.strategy;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import org.springframework.http.HttpStatus;

public class CacheControlNoStore implements IStrategy {

  public CacheControlStrategyResponse cacheControlStrategy(
      CacheModel cacheModel, ICacheRepository cacheRepository) {
    return new CacheControlStrategyResponse(false, null, HttpStatus.OK);
  }
}
