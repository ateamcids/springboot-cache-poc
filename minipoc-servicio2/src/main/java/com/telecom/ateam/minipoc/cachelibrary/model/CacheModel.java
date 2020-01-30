package com.telecom.ateam.minipoc.cachelibrary.model;

import org.springframework.http.HttpHeaders;

public class CacheModel<T> {
    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getHkey() {
        return hkey;
    }

    public void setHkey(String hkey) {
        this.hkey = hkey;
    }

    public T object;
    public HttpHeaders headers;
    public String collection;
    public String hkey;

    public CacheModel(T object, HttpHeaders headers, String collection, String hkey) {
        this.object = object;
        this.headers = headers;
        this.collection = collection;
        this.hkey = hkey;
    }
}
