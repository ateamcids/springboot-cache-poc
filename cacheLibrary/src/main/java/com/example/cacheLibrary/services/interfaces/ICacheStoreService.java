package com.example.cacheLibrary.services.interfaces;

import com.example.cacheLibrary.model.CacheResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

public interface ICacheStoreService<T> {
  T find(String collection, String hkey, Class<T> tclass);

  T find(String collection, Class<T> tclass);

  public String first(String collection);

  boolean addCollection(String collection, String hkey, T object);

  boolean addCollection(String collection, String hkey, T object, int timeout, TimeUnit unit);

  boolean addCollection(String collection, String hkey, T object, Date date);

  CacheResponseStatus add(T object, String requestUrl, HttpHeaders headers)
      throws JsonProcessingException, InterruptedException;

  Mono<T> findReactive(String collection, String hkey, Class<T> tclass);

  Mono<CacheResponseStatus> addReactive(T object, String requestUrl, HttpHeaders headers)
      throws JsonProcessingException, InterruptedException;

  Mono<Boolean> addReactiveCollection(T object, String requestUrl, HttpHeaders headers)
      throws JsonProcessingException, InterruptedException;

  Mono<Boolean> addReactiveCollection(T object, String requestUrl, int timeOut, TimeUnit unit)
      throws JsonProcessingException, InterruptedException;
}
