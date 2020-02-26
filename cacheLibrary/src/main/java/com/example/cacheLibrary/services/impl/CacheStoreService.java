package com.example.cacheLibrary.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.cacheLibrary.model.CacheModel;
import com.example.cacheLibrary.model.CacheResponseStatus;
import com.example.cacheLibrary.repositories.interfaces.ICacheRepository;
import com.example.cacheLibrary.services.interfaces.ICacheStoreService;
import com.example.cacheLibrary.util.strategy.CacheControlEnum;
import com.example.cacheLibrary.util.strategy.CacheControlStrategyResponse;
import com.example.cacheLibrary.util.strategy.IStrategy;
import com.example.cacheLibrary.util.strategy.StrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class CacheStoreService<T> implements ICacheStoreService<T> {

     ICacheRepository<T> cacheRepository;

     StrategyFactory strategyFactory;
    public CacheStoreService() {
    }

    public CacheStoreService(ICacheRepository<T> cacheRepository, StrategyFactory strategyFactory) {
        this.cacheRepository = cacheRepository;
        this.strategyFactory = strategyFactory;
    }

    public boolean addCollection(String collection, String hkey, T object) {
        return cacheRepository.add(collection, hkey, object);
    }

    public boolean addCollection(String collection, String hkey, T object, int timeout, TimeUnit unit) {
        return cacheRepository.add(collection, hkey, object, timeout, unit);
    }

    public boolean addCollection(String collection, String hkey, T object, Date date) {
        return cacheRepository.add(collection, hkey, object, date);
    }

    public CacheResponseStatus add(T object, String requestUrl, HttpHeaders headers) {
        String hkey = headers.getETag();
        if (hkey == null) hkey = requestUrl;

        /**
         * ETag desarrollado abajo
         */
        if (cacheRepository.any(requestUrl)) {
            if (cacheRepository.hasKey(requestUrl, hkey)) {
                return new CacheResponseStatus("No se ha modificado", HttpStatus.NOT_MODIFIED, true);
            } else {
                //Todo flushear coleccion de esa requestUrl
                cacheRepository.delete(requestUrl);
            }
        }


        String[] cacheControls = Arrays.stream(headers.getCacheControl().split(",")).map(String::trim).toArray(String[]::new);
        IStrategy strategy = null;

        //TODO foreach cada cachecontrol
        for (String cacheName : cacheControls) {
            strategy = strategyFactory.getStrategy(CacheControlEnum.getByCode(cacheName));

            if (strategy != null) {
                CacheControlStrategyResponse res = strategy.cacheControlStrategy(new CacheModel<T>(object, headers, requestUrl, hkey), cacheRepository);
                return new CacheResponseStatus("Se aplicó estrategia", res.getStatus(), res.isCaching());
            }

        }
        boolean add = cacheRepository.add(requestUrl, hkey, object);
        return new CacheResponseStatus("Se aplicó estrategia", HttpStatus.OK, add);
    }

    public Mono<Boolean> addReactive(T object, String requestUrl, HttpHeaders headers) throws JsonProcessingException, InterruptedException {

        String[] cacheControls = Arrays.stream(headers.getCacheControl().split(",")).map(String::trim).toArray(String[]::new);
        String hkey = headers.getETag();
        if (hkey == null) hkey = requestUrl;
        return cacheRepository.addReactive(requestUrl, hkey, object);
    }

    public T find(String collection, String hkey, Class<T> tClass) {
        return cacheRepository.find(collection, hkey, tClass);
    }

    public T find(String collection,  Class<T> tClass) {
        return cacheRepository.find(collection, tClass);
    }


    public String first(String collection) {
        return cacheRepository.first(collection);
    }

    public Mono<T> findReactive(String collection, String hkey, Class<T> tClass) {
        return cacheRepository.findReactive(collection, hkey, tClass);
    }


}
