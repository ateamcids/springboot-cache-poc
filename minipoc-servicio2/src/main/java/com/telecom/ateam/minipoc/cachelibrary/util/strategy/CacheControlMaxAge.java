package com.telecom.ateam.minipoc.cachelibrary.util.strategy;

import com.telecom.ateam.minipoc.cachelibrary.model.CacheModel;
import com.telecom.ateam.minipoc.cachelibrary.repositories.interfaces.ICacheRepository;
import org.springframework.http.HttpStatus;

import java.util.concurrent.TimeUnit;

public class CacheControlMaxAge implements IStrategy {

    @Override
    public CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) {
        //int maxAge = 60;

        String[] maxage = cacheModel.header.split("=");

        boolean cached = cacheRepository.add(cacheModel.collection, cacheModel.hkey, cacheModel.object, Integer.parseInt(maxage[1]) , TimeUnit.SECONDS);

        //TODO agregar header con cache control max age HttpHeaders headers


        return new CacheControlStrategyResponse(cached,Integer.parseInt(maxage[1]), HttpStatus.OK);
    }
}
