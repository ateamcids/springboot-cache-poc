package com.telecom.ateam.minipoc.cachelibrary.util.strategy;

import com.telecom.ateam.minipoc.cachelibrary.model.CacheModel;
import com.telecom.ateam.minipoc.cachelibrary.repositories.impl.CacheRepository;
import com.telecom.ateam.minipoc.cachelibrary.repositories.interfaces.ICacheRepository;
import org.springframework.http.HttpHeaders;

import java.util.concurrent.TimeUnit;

public class CacheControlMaxAge implements IStrategy {

    @Override
    public CacheControlModel cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) {
        int maxAge = 4;
        boolean cached = cacheRepository.add(cacheModel.collection, cacheModel.hkey, cacheModel.object,maxAge, TimeUnit.SECONDS);
        return new CacheControlModel(cached,maxAge);
    }
}
