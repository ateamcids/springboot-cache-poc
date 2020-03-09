package com.example.cacheLibrary.repositories.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface ICacheRepository<T> {

  boolean add(String collection, String hkey, T object);

  boolean add(String collection, String hkey, T object, int timeout, TimeUnit unit);

  boolean add(String collection, String hkey, T object, Date date);

  Mono<Boolean> addReactive(String collection, String hkey, T object)
      throws JsonProcessingException, InterruptedException;

  Mono<Boolean> addReactive(String collection, String hkey, T object, int timeout, TimeUnit unit)
      throws JsonProcessingException, InterruptedException;

  Mono<T> findReactive(String collection, String hkey, Class<T> tclass);

  boolean delete(String collection);

  boolean delete(String collection, String hkey);

  boolean deleteReactive(String collection);

  T find(String collection, String hkey, Class<T> tclass);

  T find(String collection, Class<T> tclass);

  Boolean isAvailable();

  boolean any(String collection);

  boolean hasKey(String collection, String hkey);

  Map completeCollection(String collection);

  public String first(String collection);
}
