package com.example.cacheLibrary.util.strategy.reactive;

import com.example.cacheLibrary.model.CacheModel;
import com.example.cacheLibrary.repositories.interfaces.ICacheRepository;
import com.example.cacheLibrary.util.strategy.CacheControlStrategyResponse;
import com.example.cacheLibrary.util.strategy.IStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

public class ReactiveCacheControlMaxAge implements IStrategy {

    public CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) throws JsonProcessingException, InterruptedException {
        //int maxAge = 60;

        String[] maxage = cacheModel.header.split("=");

     //   boolean cached = cacheRepository.add(cacheModel.collection, cacheModel.hkey, cacheModel.object, Integer.parseInt(maxage[1]) , TimeUnit.SECONDS);
        Mono cached = cacheRepository.addReactive(cacheModel.collection, cacheModel.hkey, cacheModel.object, Integer.parseInt(maxage[1]) , TimeUnit.SECONDS);

        //TODO agregar header con cache control max age HttpHeaders headers

System.out.println(cached);
        System.out.println(cached.block());

        return new CacheControlStrategyResponse(cached.equals(true),Integer.parseInt(maxage[1]), HttpStatus.OK);
    }
}
