package com.telecom.ateam.minipoc.cachelibrary.util.strategy;

import com.telecom.ateam.minipoc.cachelibrary.model.CacheModel;
import com.telecom.ateam.minipoc.cachelibrary.repositories.interfaces.ICacheRepository;

public interface IStrategy {
    CacheControlStrategyResponse cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository);
}
