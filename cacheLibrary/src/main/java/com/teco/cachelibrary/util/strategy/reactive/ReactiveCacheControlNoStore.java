package com.teco.cachelibrary.util.strategy.reactive;

import com.teco.cachelibrary.model.CacheModel;
import com.teco.cachelibrary.repositories.interfaces.ICacheRepository;
import com.teco.cachelibrary.util.strategy.model.CacheControlStrategyResponse;
import org.springframework.http.HttpStatus;

public class ReactiveCacheControlNoStore implements IReactiveStrategy {

  /**
   * Implementación de la estragia concreta no-store de forma Reactiva .
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
