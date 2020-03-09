package com.example.cacheLibrary.util.strategy;

import com.example.cacheLibrary.util.strategy.reactive.ReactiveCacheControlMaxAge;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class StrategyFactory {

  private Map<CacheControlEnum, IStrategy> strategies = new EnumMap<>(CacheControlEnum.class);

  public StrategyFactory() {
    initStrategies();
  }

  public IStrategy getStrategy(CacheControlEnum cacheControl) {
    if (cacheControl == null || !strategies.containsKey(cacheControl)) {
      return null;
    }
    return strategies.get(cacheControl);
  }

  private void initStrategies() {
    strategies.put(CacheControlEnum.MAXAGE, new CacheControlMaxAge());
    strategies.put(CacheControlEnum.NOSTORE, new CacheControlNoStore());
    strategies.put(CacheControlEnum.MAXAGE, new ReactiveCacheControlMaxAge());
  }
}
