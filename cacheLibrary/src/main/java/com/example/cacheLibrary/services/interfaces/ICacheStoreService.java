package com.example.cacheLibrary.services.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.cacheLibrary.model.CacheResponseStatus;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public interface ICacheStoreService<T> {
    T find(String collection, String hkey, Class<T> tClass);

    T find(String collection, Class<T> tClass);

    public String first(String collection);

    Mono<T> findReactive(String collection, String hkey, Class<T> tClass);

    boolean addCollection(String collection, String hkey, T object);

    boolean addCollection(String collection, String hkey, T object, int timeout, TimeUnit unit);

    boolean addCollection(String collection, String hkey, T object, Date date);

    CacheResponseStatus add(T object, String requestUrl, HttpHeaders headers) throws JsonProcessingException, InterruptedException;

    Mono<CacheResponseStatus> addReactive(T object, String requestUrl, HttpHeaders headers) throws JsonProcessingException, InterruptedException;

    Mono<Boolean> addReactiveCollection(T object, String requestUrl, HttpHeaders headers) throws JsonProcessingException, InterruptedException;

    Mono<Boolean> addReactiveCollection(T object, String requestUrl, int timeOut, TimeUnit unit) throws JsonProcessingException, InterruptedException;

}
