package com.example.minipocservicio1.cachelibrary.util.strategy;

public enum CacheControlEnum {
    MUSTREVALIDATE("must-revalidate"),
    NOCACHE("no-cache"),
    NOSTORE("no-store"),
    NOTRANSFORM("no-transform"),
    PUBLIC("public"),
    PRIVATE("private"),
    PROXYREVALIDATE("proxy-revalidate"),
    MAXAGE("max-age="),
    SMAXAGE("s-maxage="),
    UNKNOWN("unknown");


    private final String cacheControlCode;

    private CacheControlEnum(String cacheControlCode) {
        this.cacheControlCode = cacheControlCode;
    }

    public static CacheControlEnum getByCode(String cacheControlCode) {
        for(CacheControlEnum e : values()) {
            if(e.cacheControlCode.equals(cacheControlCode)) return e;
        }
        return UNKNOWN;
    }
}
