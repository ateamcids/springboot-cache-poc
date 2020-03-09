package com.example.cacheLibrary.model;

import com.example.cacheLibrary.util.strategy.CacheControlEnum;

import java.util.regex.Pattern;

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
