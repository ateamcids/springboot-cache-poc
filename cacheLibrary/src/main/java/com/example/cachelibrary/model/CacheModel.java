package com.example.cachelibrary.model;

import org.springframework.http.HttpHeaders;

/**
 * Modelo utlizado para ser enviado al repositorio conteniendo diferentes datos para poder cumplir
 * con las funcionalidades requeridas en los repositorios.
 *
 * @param <T> Type of object, siendo este el objeto a ser guardado en Redis.
 */
public class CacheModel<T> {

  private T object;
  private HttpHeaders headers;
  private String header;
  private String collection;
  private String hkey;

  /**
   * Constructor.
   *
   * @param object objeto a ser guardado en cache.
   * @param headers headers que se recibieron luego de hacer una consulta HTTP, siendo estos quienes
   *     intervienen como va a comportarse el guardado en Redis.
   * @param collection colección de Redis donde se va a guardar la información.
   * @param hkey hashkey que va a utilizar en la misma.
   */
  public CacheModel(T object, HttpHeaders headers, String collection, String hkey) {
    this.object = object;
    this.headers = headers;
    this.collection = collection;
    this.hkey = hkey;
  }

  /**
   * Constructor.
   *
   * @param object objeto a ser guardado en cache.
   * @param header Header en particular a analizar.
   * @param collection colección de Redis donde se va a guardar la información.
   * @param hkey hashkey que va a utilizar en la misma.
   */
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
