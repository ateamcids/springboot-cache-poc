package com.example.cacheLibrary.repositories.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public interface ICacheRepository<T> {

    boolean add(String collection, String hkey, T object);

    boolean add(String collection, String hkey, T object, int timeout, TimeUnit unit);

    boolean add(String collection, String hkey, T object, Date date);

    Mono<Boolean> addReactive(String collection, String hkey, T object) throws JsonProcessingException, InterruptedException;

    boolean delete(String collection);

    boolean deleteReactive(String collection);

    boolean delete(String collection, String hkey);

    T find(String collection, String hkey, Class<T> tClass);

    T find(String collection, Class<T> tClass);


    Mono<T> findReactive(String collection, String hkey, Class<T> tClass);

    Boolean isAvailable();

    boolean any(String collection);

    boolean hasKey(String collection, String hkey);

    Map completeCollection(String collection);

    public String first(String collection);

}
