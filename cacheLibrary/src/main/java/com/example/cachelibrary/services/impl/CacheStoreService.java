package com.example.cachelibrary.services.impl;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.model.CacheResStatusDescripcionEnum;
import com.example.cachelibrary.model.CacheResponseStatus;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.services.interfaces.ICacheStoreService;
import com.example.cachelibrary.util.strategy.reactive.IReactiveStrategy;
import com.example.cachelibrary.util.strategy.reactive.ReactiveStrategyFactory;
import com.example.cachelibrary.util.strategy.model.CacheControlStrategyResponse;
import com.example.cachelibrary.util.strategy.reactive.ReactiveCacheControlEnum;
import com.example.cachelibrary.util.strategy.sync.CacheControlEnum;
import com.example.cachelibrary.util.strategy.sync.IStrategy;
import com.example.cachelibrary.util.strategy.sync.StrategyFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class CacheStoreService<T> implements ICacheStoreService<T> {

    private final ICacheRepository<T> cacheRepository;
    private final StrategyFactory strategyFactory;
    private final ReactiveStrategyFactory reactiveStrategyFactory;

    public CacheStoreService(ICacheRepository<T> cacheRepository, StrategyFactory strategyFactory, ReactiveStrategyFactory reactiveStrategyFactory) {
        this.cacheRepository = cacheRepository;
        this.strategyFactory = strategyFactory;
        this.reactiveStrategyFactory = reactiveStrategyFactory;
    }

    public boolean addCollection(String collection, String hkey, T object) {
        return cacheRepository.add(collection, hkey, object);
    }

    public boolean addCollection(
            String collection, String hkey, T object, int timeout, TimeUnit unit) {
        return cacheRepository.add(collection, hkey, object, timeout, unit);
    }

    public boolean addCollection(String collection, String hkey, T object, Date date) {
        return cacheRepository.add(collection, hkey, object, date);
    }

    public CacheResponseStatus add(T object, String requestUrl, HttpHeaders headers)
            throws JsonProcessingException, InterruptedException {
        String hkey = headers.getETag();
        if (hkey == null) {
            hkey = requestUrl;
        }

        String descripcion = CacheResStatusDescripcionEnum.APLICOESTRATEGIA.getDescripcion();

        /** ETag desarrollado abajo */
        if (cacheRepository.any(requestUrl)) {
            if (cacheRepository.hasKey(requestUrl, hkey)) {
                descripcion = CacheResStatusDescripcionEnum.NOMODIFICACION.getDescripcion();
                return new CacheResponseStatus(descripcion, HttpStatus.NOT_MODIFIED, true);
            } else {
                // flushear coleccion de esa requestUrl
                cacheRepository.delete(requestUrl);
            }
        }

        if (headers.getCacheControl() != null) {
            String[] cacheControls =
                    Arrays.stream(headers.getCacheControl().split(","))
                            .map(String::trim)
                            .toArray(String[]::new);
            IStrategy strategy = null;

            // foreach cada cachecontrol
            for (String cacheName : cacheControls) {

                strategy = strategyFactory.getStrategy(CacheControlEnum.getByCode(cacheName));

                if (strategy != null) {
                    CacheControlStrategyResponse res =
                            strategy.cacheControlStrategy(
                                    new CacheModel<T>(object, cacheName, requestUrl, hkey), cacheRepository);
                    return new CacheResponseStatus(descripcion, res.getStatus(), res.isCaching());
                }
            }
        }

        if (headers.getETag() != null) {
            boolean add = cacheRepository.add(requestUrl, hkey, object);

            return new CacheResponseStatus(descripcion, HttpStatus.OK, add);
        } else {
            return null;
        }
    }

    public Mono<CacheResponseStatus> addReactive(T object, String requestUrl, HttpHeaders headers)
            throws JsonProcessingException, InterruptedException {
        String hkey = headers.getETag();
        if (hkey == null) {
            hkey = requestUrl;
        }
        String descripcion = CacheResStatusDescripcionEnum.APLICOESTRATEGIA.getDescripcion();
        /** ETag desarrollado abajo */
        if (cacheRepository.any(requestUrl)) {
            if (cacheRepository.hasKey(requestUrl, hkey)) {
                descripcion = CacheResStatusDescripcionEnum.NOMODIFICACION.getDescripcion();
                return Mono.just(new CacheResponseStatus(descripcion, HttpStatus.NOT_MODIFIED, true));
            } else {
                // flushear coleccion de esa requestUrl
                cacheRepository.delete(requestUrl);
            }
        }

        if (headers.getCacheControl() != null) {
            String[] cacheControls =
                    Arrays.stream(headers.getCacheControl().split(","))
                            .map(String::trim)
                            .toArray(String[]::new);
            IReactiveStrategy strategy = null;

            // foreach cada cachecontrol
            for (String cacheName : cacheControls) {

                strategy = reactiveStrategyFactory.getStrategy(ReactiveCacheControlEnum.getByCode(cacheName));

                if (strategy != null) {
                    CacheControlStrategyResponse res =
                            strategy.cacheControlStrategy(
                                    new CacheModel<T>(object, cacheName, requestUrl, hkey), cacheRepository);
                    return Mono.just(new CacheResponseStatus(descripcion, res.getStatus(), res.isCaching()));
                }
            }
        }

        if (headers.getETag() != null) {
            boolean add = cacheRepository.addReactive(requestUrl, hkey, object).block();

            return Mono.just(new CacheResponseStatus("Se aplicó estrategia", HttpStatus.OK, add));
        } else {
            return null;
        }
    }

    public Mono<Boolean> addReactiveCollection(T object, String requestUrl, HttpHeaders headers) throws JsonProcessingException, InterruptedException {

        String hkey = headers.getETag();
        if (hkey == null) {
            hkey = requestUrl;
        }
        return cacheRepository.addReactive(requestUrl, hkey, object);
    }

    public Mono<Boolean> addReactiveCollection(
            T object, String requestUrl, HttpHeaders headers, int timeOut)
            throws JsonProcessingException, InterruptedException {
        String hkey = headers.getETag();
        if (hkey == null) {
            hkey = requestUrl;
        }
        return cacheRepository.addReactive(requestUrl, hkey, object, timeOut);
    }

    public T find(String collection, String hkey, Class<T> tclass) {
        return cacheRepository.find(collection, hkey, tclass);
    }

    public T find(String collection, Class<T> tclass) {
        return cacheRepository.find(collection, tclass);
    }

    public String first(String collection) {
        return cacheRepository.first(collection);
    }

    public Mono<T> findReactive(String collection, String hkey, Class<T> tclass) {
        return cacheRepository.findReactive(collection, hkey, tclass);
    }
}
