package com.example.minipocservicio1.cachelibrary.services.interfaces;

import org.springframework.http.HttpHeaders;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface ICacheStoreService<T> {
    T find(String collection, String hkey, Class<T> tClass);
    boolean addCollection(String collection, String hkey, T object);
    boolean addCollection(String collection, String hkey, T object, int timeout, TimeUnit unit);
    boolean addCollection(String collection, String hkey, T object, Date date);
    boolean add(T object, String requestUrl, HttpHeaders headers);
}
