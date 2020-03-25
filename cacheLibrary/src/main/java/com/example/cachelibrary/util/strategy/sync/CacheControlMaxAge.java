package com.example.cachelibrary.util.strategy.sync;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import java.util.concurrent.TimeUnit;
import com.example.cachelibrary.util.strategy.model.CacheControlStrategyResponse;
import org.springframework.http.HttpStatus;

public class CacheControlMaxAge implements IStrategy {

  /**
   * Implementación de la estragia concreta Max-age .
   *
   * @param cacheModel Modelo da dato.
   * @param cacheRepository Objeto para invocar a funciones del repositorio de redis.
   * @return Modelo de datos definido en {@link CacheControlStrategyResponse} .
   */
  public CacheControlStrategyResponse cacheControlStrategy(
      CacheModel cacheModel, ICacheRepository cacheRepository) {

    String[] maxage = cacheModel.getHeader().split("=");

    boolean cached =
        cacheRepository.add(
            cacheModel.getCollection(),
            cacheModel.getHkey(),
            cacheModel.getObject(),
            Integer.parseInt(maxage[1]),
            TimeUnit.SECONDS);

    return new CacheControlStrategyResponse(cached, Integer.parseInt(maxage[1]), HttpStatus.OK);
  }
}
