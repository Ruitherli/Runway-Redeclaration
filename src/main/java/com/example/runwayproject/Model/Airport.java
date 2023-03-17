package com.example.runwayproject.Model;

public class Airport {
    //class variables
    //int blastProtection, resa, stripEnd, tocs, als;
    private int id;
    private String airportName;

    //getter setter
    /*public int getBlastProtection() {
        return blastProtection;
    }
    public void setBlastProtection(int blastProtection) {
        this.blastProtection = blastProtection;
    }
    public int getResa() {
        return resa;
    }
    public void setResa(int resa) {
        this.resa = resa;
    }
    public int getStripEnd() {
        return stripEnd;
    }
    public void setStripEnd(int stripEnd) {
        this.stripEnd = stripEnd;
    }
    public int getTocs() {
        return tocs;
    }
    public void setTocs(int tocs) {
        this.tocs = tocs;
    }
    public int getAls() {
        return als;
    }
    public void setAls(int als) {
        this.als = als;
    }*/

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAirportName() {
        return airportName;
    }
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    //constructor
    /*Airport(String airportName, int blastProtection, int resa, int stripEnd, int tocs, int als){
        setAirportName(airportName);
        setBlastProtection(blastProtection);
        setResa(resa);
        setStripEnd(stripEnd);
        setTocs(tocs);
        setAls(als);
    }*/

    Airport(String airportName, int id){
        setAirportName(airportName);
        setId(id);
    }

    //method
    /*int getSignificant(){
        return Math.max(getBlastProtection(), (getResa()+getStripEnd()));
    }*/

}
