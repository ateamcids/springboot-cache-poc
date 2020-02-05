package com.telecom.ateam.minipoc.cachelibrary.model;

import org.springframework.http.HttpStatus;

public class CacheResponseStatus {
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



    private String descripcion;
    private HttpStatus status;
    private boolean isCaching;


    public CacheResponseStatus(String descripcion, HttpStatus status, boolean isCaching) {
        this.descripcion = descripcion;
        this.status = status;
        this.isCaching = isCaching;
    }




}
