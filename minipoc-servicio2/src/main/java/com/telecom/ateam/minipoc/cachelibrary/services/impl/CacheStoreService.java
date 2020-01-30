package com.telecom.ateam.minipoc.cachelibrary.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.cachelibrary.model.CacheModel;
import com.telecom.ateam.minipoc.cachelibrary.repositories.interfaces.ICacheRepository;
import com.telecom.ateam.minipoc.cachelibrary.services.interfaces.ICacheStoreService;
import com.telecom.ateam.minipoc.cachelibrary.util.strategy.CacheControlEnum;
import com.telecom.ateam.minipoc.cachelibrary.util.strategy.IStrategy;
import com.telecom.ateam.minipoc.cachelibrary.util.strategy.StrategyFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class CacheStoreService<T> implements ICacheStoreService<T> {
    private final ICacheRepository<T> cacheRepository;
    private final StrategyFactory strategyFactory;

    public CacheStoreService(ICacheRepository<T> cacheRepository, StrategyFactory strategyFactory) {
        this.cacheRepository = cacheRepository;
        this.strategyFactory = strategyFactory;
    }

    public boolean addCollection(String collection, String hkey, T object){
        return cacheRepository.add(collection,hkey,object);
    }

    public boolean addCollection(String collection, String hkey, T object, int timeout, TimeUnit unit){
        return cacheRepository.add(collection,hkey,object,timeout,unit);
    }
    public boolean addCollection(String collection, String hkey, T object, Date date){
        return cacheRepository.add(collection,hkey,object,date);
    }

    public boolean add( T object, String requestUrl , HttpHeaders headers){
        String hkey = headers.getETag();

        if(cacheRepository.any(requestUrl)){
            if (cacheRepository.hasKey(requestUrl,hkey)){
                return false;
            }
        }
        if (hkey == null) hkey = requestUrl;

        String cacheControl = headers.getCacheControl();
        IStrategy strategy = null;
        if (cacheControl.contains("max-age")){
            strategy = strategyFactory.getStrategy(CacheControlEnum.MAXAGE);
        }
        if (cacheControl.contains("no-store")){
            strategy = strategyFactory.getStrategy(CacheControlEnum.NOSTORE);
        }
        /*if (cacheControl.contains("private")){
            strategy = strategyFactory.getStrategy(CacheControlEnum.NOSTORE);
        }*/

        if (strategy!=null){
            return strategy.cacheControlStrategy(new CacheModel<T>(object,headers,requestUrl,hkey),cacheRepository).isCaching();
        }
        return cacheRepository.add(requestUrl, hkey, object);










    }

    public Mono<Boolean> addReactive(T object, String requestUrl , HttpHeaders headers) throws JsonProcessingException, InterruptedException {
        String cacheControl = headers.getCacheControl();
 /*       switch (cacheControl){
            case CacheControlEnum
                    .PRIVATE
        }*/
        String hkey = headers.getETag();

        if (hkey == null) hkey = requestUrl;

        /*if(cacheRepository.any(requestUrl)){
            if (cacheRepository.hasKey(requestUrl,hkey)){
                return false;
            }
        }*/
        return cacheRepository.addReactive(requestUrl, hkey, object);
    }

    public T find(String collection, String hkey, Class<T> tClass){
       return cacheRepository.find(collection, hkey, tClass);
    }

    public Mono<T> findReactive(String collection, String hkey, Class<T> tClass){
        return cacheRepository.findReactive(collection, hkey, tClass);
    }
}
