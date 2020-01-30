package com.telecom.ateam.minipoc.cachelibrary.util.strategy;

import com.telecom.ateam.minipoc.cachelibrary.model.CacheModel;
import com.telecom.ateam.minipoc.cachelibrary.repositories.impl.CacheRepository;
import com.telecom.ateam.minipoc.cachelibrary.repositories.interfaces.ICacheRepository;
import org.springframework.http.HttpHeaders;

public class CacheControlNoStore implements IStrategy {

    @Override
    public CacheControlModel cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) {
        return new CacheControlModel(false,null);
    }
}
