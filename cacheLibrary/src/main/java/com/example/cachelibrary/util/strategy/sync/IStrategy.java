package com.example.cachelibrary.util.strategy.sync;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.util.strategy.model.CacheControlStrategyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

/** Interfaz común para todos las estragias de cache control . */
public interface IStrategy {
  /**
   * Método que va a implementar la estragia concreta de cache control .
   *
   * @param cacheModel Modelo da dato
   * @param cacheRepository Objeto para invocar a funciones del repositorio de redis
   * @return Modelo de datos de tipo CacheControlStrategyResponse
   */
  CacheControlStrategyResponse cacheControlStrategy(
      CacheModel cacheModel, ICacheRepository cacheRepository)
      throws JsonProcessingException, InterruptedException;
}
