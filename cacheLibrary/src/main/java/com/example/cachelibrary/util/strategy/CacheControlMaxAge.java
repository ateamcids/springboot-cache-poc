package com.example.cachelibrary.util.strategy;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpStatus;

public class CacheControlMaxAge implements IStrategy {

  public CacheControlStrategyResponse cacheControlStrategy(
      CacheModel cacheModel, ICacheRepository cacheRepository) {

    String[] maxage = cacheModel.getHeader().split("=");

    boolean cached =
        cacheRepository.add(
            cacheModel.getCollection(),
            cacheModel.getHkey(),
            cacheModel.getObject(),
            Integer.parseInt(maxage[1]),
            TimeUnit.SECONDS);

    // TODO agregar header con cache control max age HttpHeaders headers

    return new CacheControlStrategyResponse(cached, Integer.parseInt(maxage[1]), HttpStatus.OK);
  }
}
