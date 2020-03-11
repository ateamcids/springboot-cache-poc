package com.example.cachelibrary.util.strategy.reactive;

import com.example.cachelibrary.util.strategy.sync.CacheControlEnum;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class ReactiveStrategyFactory {

  private Map<CacheControlEnum, IReactiveStrategy> strategies = new EnumMap<>(CacheControlEnum.class);

  public ReactiveStrategyFactory() {
    initStrategies();
  }

  public IReactiveStrategy getStrategy(CacheControlEnum cacheControl) {
    if (cacheControl == null || !strategies.containsKey(cacheControl)) {
      return null;
    }
    return strategies.get(cacheControl);
  }

  private void initStrategies() {
    strategies.put(CacheControlEnum.NOSTORE, new ReactiveCacheControlNoStore());
    strategies.put(CacheControlEnum.MAXAGE, new ReactiveCacheControlMaxAge());
  }
}
