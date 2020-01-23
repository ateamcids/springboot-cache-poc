package com.telecom.ateam.minipoc.models;

import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;

@Cacheable
public class DashboardModel implements Serializable {
    private String OB_NOMBRE;

    private int CANT_APP;

    private int OB_ID;

    private int OB_ID_VAL;

    public String getOB_NOMBRE() {
        return OB_NOMBRE;
    }

    public void setOB_NOMBRE(String OB_NOMBRE) {
        this.OB_NOMBRE = OB_NOMBRE;
    }

    public int getCANT_APP() {
        return CANT_APP;
    }

    public void setCANT_APP(int CANT_APP) {
        this.CANT_APP = CANT_APP;
    }

    public int getOB_ID() {
        return OB_ID;
    }

    public void setOB_ID(int OB_ID) {
        this.OB_ID = OB_ID;
    }

    public int getOB_ID_VAL() {
        return OB_ID_VAL;
    }

    public void setOB_ID_VAL(int OB_ID_VAL) {
        this.OB_ID_VAL = OB_ID_VAL;
    }

}
