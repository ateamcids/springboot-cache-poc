package com.example.cachelibrary.util.strategy.reactive;


import com.example.cachelibrary.util.strategy.reactive.ReactiveCacheControlEnum;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ReactiveStrategyFactory {

    private Map<ReactiveCacheControlEnum, IReactiveStrategy> strategies =
            new EnumMap<>(ReactiveCacheControlEnum.class);

    public ReactiveStrategyFactory() {
        initStrategies();
    }

    /**
     * Verificar si existe el cache control y devolver la estragia concreta .
     *
     * @param cacheControl Nombre del cache control
     * @return interface de la estrategia
     */
    public IReactiveStrategy getStrategy(ReactiveCacheControlEnum cacheControl) {
        if (cacheControl == null || !strategies.containsKey(cacheControl)) {
            return null;
        }
        return strategies.get(cacheControl);
    }

    /**
     * Inicializar las estragias concretas .
     */
    private void initStrategies() {
        strategies.put(ReactiveCacheControlEnum.NOSTORE, new ReactiveCacheControlNoStore());
        strategies.put(ReactiveCacheControlEnum.MAXAGE, new ReactiveCacheControlMaxAge());
    }
}
