package com.telecom.ateam.minipoc.cachelibrary.util.strategy;

import com.telecom.ateam.minipoc.cachelibrary.model.CacheModel;
import com.telecom.ateam.minipoc.cachelibrary.repositories.interfaces.ICacheRepository;
import org.springframework.http.HttpStatus;

import java.util.concurrent.TimeUnit;

public class CacheControlMaxAge implements IStrategy {

    @Override
    public CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) {
        int maxAge = 4;
        boolean cached = cacheRepository.add(cacheModel.collection, cacheModel.hkey, cacheModel.object,maxAge, TimeUnit.SECONDS);

        //TODO agregar header con cache control max age HttpHeaders headers


        return new CacheControlStrategyResponse(cached,maxAge, HttpStatus.OK);
    }
}
