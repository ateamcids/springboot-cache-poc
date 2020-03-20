package com.example.cachelibrary.util.strategy.model;

import org.springframework.http.HttpStatus;

/**
 * Resultado que devuelve la ejecución de alguna de las estrategias de cachecontrol
 */
public class CacheControlStrategyResponse {
    private boolean caching;
    private Integer maxAge;
    private HttpStatus status;

    /**
     *
     * @param caching valor booleano si se cacheo correctamente
     * @param maxAge valor del tiempo de expiración de los datos
     * @param status valor del estado de la petición http
     */
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
