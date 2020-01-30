package com.telecom.ateam.minipoc.cachelibrary.services.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface ICacheStoreService<T> {
    T find(String collection, String hkey, Class<T> tClass);
   Mono<T> findReactive(String collection, String hkey, Class<T> tClass);

    boolean addCollection(String collection, String hkey, T object);
    boolean addCollection(String collection, String hkey, T object, int timeout, TimeUnit unit);
    boolean addCollection(String collection, String hkey, T object, Date date);
    boolean add( T object, String requestUrl , HttpHeaders headers);
    Mono<Boolean> addReactive(T object, String requestUrl , HttpHeaders headers)  throws JsonProcessingException, InterruptedException;
}
