package com.example.cachelibrary.util.strategy.sync;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.util.strategy.model.CacheControlStrategyResponse;
import org.springframework.http.HttpStatus;

public class CacheControlNoStore implements IStrategy {

  /**
   * Implementación de la estragia concreta no-store .
   *
   * @param cacheModel Modelo da dato.
   * @param cacheRepository Objeto para invocar a funciones del repositorio de redis.
   * @return Modelo de datos definido en {@link CacheControlStrategyResponse} .
   */
  public CacheControlStrategyResponse cacheControlStrategy(
      CacheModel cacheModel, ICacheRepository cacheRepository) {
    return new CacheControlStrategyResponse(false, null, HttpStatus.OK);
  }
}
