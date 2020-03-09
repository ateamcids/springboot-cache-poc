package com.example.cacheLibrary.model;

import org.springframework.http.HttpStatus;

public class CacheResponseStatus {

    private String descripcion;
    private HttpStatus status;
    private boolean isCaching;


    public CacheResponseStatus(String descripcion, HttpStatus status, boolean isCaching) {
        this.descripcion = descripcion;
        this.status = status;
        this.isCaching = isCaching;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public boolean isCaching() { return isCaching; }

    public void setCaching(boolean caching) { isCaching = caching; }

}
