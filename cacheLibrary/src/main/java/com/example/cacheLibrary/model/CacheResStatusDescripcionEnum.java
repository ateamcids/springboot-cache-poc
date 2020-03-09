package com.example.cacheLibrary.model;

public enum CacheResStatusDescripcionEnum {
  APLICOESTRATEGIA("Se aplicó estrategia"),
  NOMODIFICACION("No se ha modificado");

  private final String descripcion;

  private CacheResStatusDescripcionEnum(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion() {
    return descripcion;
  }
}
