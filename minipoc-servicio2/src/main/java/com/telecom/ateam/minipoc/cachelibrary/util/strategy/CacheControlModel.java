package com.telecom.ateam.minipoc.cachelibrary.util.strategy;

public class CacheControlModel {
    private boolean caching;
    private Integer maxAge;

    public CacheControlModel(boolean caching, Integer maxAge) {
        this.caching = caching;
        this.maxAge = maxAge;
    }

    public boolean isCaching() {
        return caching;
    }

    public void setCaching(boolean caching) {
        this.caching = caching;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
}
