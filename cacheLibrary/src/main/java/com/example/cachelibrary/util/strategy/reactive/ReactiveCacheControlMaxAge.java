package com.example.cachelibrary.util.strategy.reactive;

import com.example.cachelibrary.model.CacheModel;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.util.strategy.model.CacheControlStrategyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public class ReactiveCacheControlMaxAge implements IReactiveStrategy {

  public CacheControlStrategyResponse cacheControlStrategy(
      CacheModel cacheModel, ICacheRepository cacheRepository)
      throws JsonProcessingException, InterruptedException {

    String[] maxage = cacheModel.getHeader().split("=");

    Mono cached =
        cacheRepository.addReactive(
            cacheModel.getCollection(),
            cacheModel.getHkey(),
            cacheModel.getObject(),
            Integer.parseInt(maxage[1]),
            TimeUnit.SECONDS);

    // TODO agregar header con cache control max age HttpHeaders headers

    return new CacheControlStrategyResponse(
        (boolean) cached.block(), Integer.parseInt(maxage[1]), HttpStatus.OK);
  }
}
