package com.example.cachelibrary.util.strategy.sync;

import com.example.cachelibrary.util.strategy.reactive.ReactiveCacheControlEnum;
import java.util.regex.Pattern;

/**
 * Enum que indica todas los posibles valores que existen en el cache control para ser utilizado en
 * la estrategia concreta .
 */
public enum CacheControlEnum {
  MUSTREVALIDATE("must-revalidate"),
  NOCACHE("no-cache"),
  NOSTORE("no-store"),
  NOTRANSFORM("no-transform"),
  PUBLIC("public"),
  PRIVATE("private"),
  PROXYREVALIDATE("proxy-revalidate"),
  MAXAGE(Pattern.compile("max-age=([0-9]+)")),
  SMAXAGE("s-maxage="),
  UNKNOWN("unknown");

  private final String cacheControlCode;
  private final Pattern maxagePattern;

  /**
   * Inicializa la variable maxagePattern para ser usada en cache control Max-age .
   *
   * @param cacheControlCode Nombre de cache control .
   */
  CacheControlEnum(Pattern cacheControlCode) {
    this.maxagePattern = cacheControlCode;
    this.cacheControlCode = null;
  }

  /**
   * Inicializa la variable cacheControlCode para ser usada en cache control diferente a Max-age .
   *
   * @param cacheControlCode Nombre de cache control .
   */
  CacheControlEnum(String cacheControlCode) {
    this.cacheControlCode = cacheControlCode;
    this.maxagePattern = null;
  }

  /**
   * Verifica que tipo de cache control se recibe y que se encuentre dentro de los definidos en el
   * enum .
   *
   * @param cacheControlCode Nombre de cache control
   * @return objeto de tipo {@link ReactiveCacheControlEnum} que coincida que algún enum .
   */
  public static CacheControlEnum getByCode(String cacheControlCode) {
    for (CacheControlEnum e : values()) {
      if (e.cacheControlCode != null && e.cacheControlCode.equals(cacheControlCode)) {
        return e;
      }
      if (e.maxagePattern != null && e.maxagePattern.matcher(cacheControlCode).find()) {
        return e;
      }
    }
    return UNKNOWN;
  }
}
