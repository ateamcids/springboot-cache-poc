package com.telecom.ateam.minipoc.cachelibrary.util.strategy;

import com.telecom.ateam.minipoc.cachelibrary.model.CacheModel;
import com.telecom.ateam.minipoc.cachelibrary.repositories.interfaces.ICacheRepository;
import org.springframework.http.HttpStatus;

public class CacheControlNoStore implements IStrategy {

    @Override
    public CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository) {
        return new CacheControlStrategyResponse(false,null, HttpStatus.OK);
    }
}
