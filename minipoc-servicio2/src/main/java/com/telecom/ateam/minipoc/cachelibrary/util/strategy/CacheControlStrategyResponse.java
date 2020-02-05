package com.telecom.ateam.minipoc.cachelibrary.util.strategy;

import org.springframework.http.HttpStatus;

public class CacheControlStrategyResponse {
    private boolean caching;
    private Integer maxAge;
    private HttpStatus status;



    public CacheControlStrategyResponse(boolean caching, Integer maxAge, HttpStatus status) {
        this.caching = caching;
        this.maxAge = maxAge;
        this.status = status;
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


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
