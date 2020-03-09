package com.example.cacheLibrary.util.strategy;

import com.example.cacheLibrary.model.CacheModel;
import com.example.cacheLibrary.repositories.interfaces.ICacheRepository;
import org.springframework.http.HttpStatus;

import java.util.concurrent.TimeUnit;

public class CacheControlMaxAge implements IStrategy {

    public CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) {

        String[] maxage = cacheModel.header.split("=");

        boolean cached = cacheRepository.add(cacheModel.collection, cacheModel.hkey, cacheModel.object, Integer.parseInt(maxage[1]) , TimeUnit.SECONDS);

        //TODO agregar header con cache control max age HttpHeaders headers

        return new CacheControlStrategyResponse(cached,Integer.parseInt(maxage[1]), HttpStatus.OK);
    }
}
