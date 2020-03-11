package com.example.cachelibrary.util.strategy.reactive;

import java.util.regex.Pattern;

public enum ReactiveCacheControlEnum {
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

  private ReactiveCacheControlEnum(Pattern cacheControlCode) {
    this.maxagePattern = cacheControlCode;
    this.cacheControlCode = null;
  }

  private ReactiveCacheControlEnum(String cacheControlCode) {
    this.cacheControlCode = cacheControlCode;
    this.maxagePattern = null;
  }

  public static ReactiveCacheControlEnum getByCode(String cacheControlCode) {
    for (ReactiveCacheControlEnum e : values()) {
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
