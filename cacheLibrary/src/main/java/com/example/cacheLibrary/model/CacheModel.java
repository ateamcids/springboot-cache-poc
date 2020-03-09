package com.example.cacheLibrary.model;

import org.springframework.http.HttpHeaders;

public class CacheModel<T> {

  private T object;
  private HttpHeaders headers;
  private String header;
  private String collection;
  private String hkey;

  public CacheModel(T object, HttpHeaders headers, String collection, String hkey) {
    this.object = object;
    this.headers = headers;
    this.collection = collection;
    this.hkey = hkey;
  }

  public CacheModel(T object, String header, String collection, String hkey) {
    this.object = object;
    this.header = header;
    this.collection = collection;
    this.hkey = hkey;
  }

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

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }
}
