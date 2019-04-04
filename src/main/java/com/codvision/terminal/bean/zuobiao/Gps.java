package com.codvision.terminal.bean.zuobiao;

public class Gps {
    private Double wgLat;
    private Double wgLon;

    public Gps(Double wgLat, Double wgLon) {
        setWgLat(wgLat);
        setWgLon(wgLon);
    }

    public Double getWgLat() {
        return wgLat;
    }

    public void setWgLat(Double wgLat) {
        this.wgLat = wgLat;
    }

    public Double getWgLon() {
        return wgLon;
    }

    public void setWgLon(Double wgLon) {
        this.wgLon = wgLon;
    }

    @Override
    public String toString() {
        return wgLat + "," + wgLon;
    }

    public Gps(){}
}
