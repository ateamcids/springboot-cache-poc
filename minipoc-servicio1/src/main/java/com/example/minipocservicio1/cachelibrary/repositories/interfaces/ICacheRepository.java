package com.example.minipocservicio1.cachelibrary.repositories.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface ICacheRepository<T>{
    boolean add(String collection, String hkey, T object);
    boolean add(String collection, String hkey, T object, int timeout, TimeUnit unit);
    boolean add(String collection, String hkey, T object, Date date);
    Mono<Boolean> addReactive(String collection, String hkey, T object) throws JsonProcessingException, InterruptedException;

    boolean delete(String collection, String hkey);
    T find(String collection, String hkey, Class<T> tClass);
    Mono<T> findReactive(String collection, String hkey, Class<T> tClass);

    Boolean isAvailable();
    boolean any(String collection);
    boolean hasKey(String collection, String hkey);

}
