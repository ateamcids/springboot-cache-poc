package com.teco.cachelibrary.model;

/**
 * Enum que indica si se aplicó o no se aplicó una modificación para ser utilizado como descripción
 * del objeto.
 *
 * @see CacheResponseStatus
 */
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
