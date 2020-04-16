package com.teco.cachelibrary.util.strategy.reactive;

import com.teco.cachelibrary.util.strategy.sync.CacheControlEnum;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class ReactiveStrategyFactory {

  private Map<CacheControlEnum, IReactiveStrategy> strategies =
      new EnumMap<>(CacheControlEnum.class);

  public ReactiveStrategyFactory() {
    initStrategies();
  }

  /**
   * Verificar si existe el cache control y devolver la estragia concreta .
   *
   * @param cacheControl Nombre del cache control
   * @return interface de la estrategia
   */
  public IReactiveStrategy getStrategy(CacheControlEnum cacheControl) {
    if (cacheControl == null || !strategies.containsKey(cacheControl)) {
      return null;
    }
    return strategies.get(cacheControl);
  }

  /** Inicializar las estragias concretas . */
  private void initStrategies() {
    strategies.put(CacheControlEnum.NOSTORE, new ReactiveCacheControlNoStore());
    strategies.put(CacheControlEnum.MAXAGE, new ReactiveCacheControlMaxAge());
  }
}
