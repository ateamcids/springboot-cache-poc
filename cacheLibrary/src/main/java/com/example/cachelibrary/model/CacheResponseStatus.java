package com.example.cachelibrary.model;

import org.springframework.http.HttpStatus;

/** Resultado obtenido luego de la utilización de los repositorios. */
public class CacheResponseStatus {

  private String descripcion;
  private HttpStatus status;
  private boolean isCaching;

  /**
   * Constructor.
   *
   * @param descripcion descripción del resultado.
   * @param status status Http recomendado para ser respondido por el microservicio consumidor.
   * @param isCaching boolean indicando si fue cacheado o no en Redis.
   */
  public CacheResponseStatus(String descripcion, HttpStatus status, boolean isCaching) {
    this.descripcion = descripcion;
    this.status = status;
    this.isCaching = isCaching;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public boolean isCaching() {
    return isCaching;
  }

  public void setCaching(boolean caching) {
    isCaching = caching;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof CacheResponseStatus)) {
      return false;
    }
    CacheResponseStatus c = (CacheResponseStatus) o;
    return descripcion.equals(c.descripcion) && status.equals(c.status) && isCaching == c.isCaching;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
