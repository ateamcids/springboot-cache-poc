package com.telecom.ateam.minipoc.cachelibrary.util.strategy;

import com.telecom.ateam.minipoc.cachelibrary.model.CacheModel;
import com.telecom.ateam.minipoc.cachelibrary.repositories.impl.CacheRepository;
import com.telecom.ateam.minipoc.cachelibrary.repositories.interfaces.ICacheRepository;
import org.springframework.http.HttpHeaders;

public interface IStrategy {
    CacheControlModel cacheControlStrategy(CacheModel cacheModel, ICacheRepository cacheRepository);
}
