package com.example.cacheLibrary.util.strategy;

//import org.omg.CORBA.UNKNOWN;

import java.util.regex.Pattern;

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

    private CacheControlEnum(Pattern cacheControlCode) {
        this.maxagePattern = cacheControlCode;
        this.cacheControlCode = null;
    }

    private CacheControlEnum(String cacheControlCode) {
        this.cacheControlCode = cacheControlCode;
        this.maxagePattern = null;
    }

    public static CacheControlEnum getByCode(String cacheControlCode) {
        for (CacheControlEnum e : values()) {
            if (e.cacheControlCode != null) {
                if (e.cacheControlCode.equals(cacheControlCode))
                    return e;
            }
            if (e.maxagePattern != null) {
                if (e.maxagePattern.matcher(cacheControlCode).find()) {
                    return e;
                }
            }
        }
        return UNKNOWN;
    }
}
