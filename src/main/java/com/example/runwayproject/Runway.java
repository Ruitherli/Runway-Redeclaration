package com.example.runwayproject;

public class Runway extends Airport{
    //class variables
    int tora, toda, asda, lda, obstacleHeight, distanceFromThres, clearway, stopway;
    /////displace threshold = tora - lda
    String runwayName;
    boolean obstaclePresent;

    //getter setter
    public int getTora() {
        return tora;
    }
    public void setTora(int tora) {
        this.tora = tora;
    }
    public int getToda() {
        return toda;
    }
    public void setToda(int toda) {
        this.toda = toda;
    }
    public int getAsda() {
        return asda;
    }
    public void setAsda(int asda) {
        this.asda = asda;
    }
    public int getLda() {
        return lda;
    }
    public void setLda(int lda) {
        this.lda = lda;
    }
    public int getObstacleHeight() {
        return obstacleHeight;
    }
    public void setObstacleHeight(int obstacleHeight) {
        this.obstacleHeight = obstacleHeight;
    }
    public String getRunwayName() {
        return runwayName;
    }
    public int getDistanceFromThres() {
        return distanceFromThres;
    }
    public void setDistanceFromThres(int distanceFromThres) {
        this.distanceFromThres = distanceFromThres;
    }
    public int getClearway() {
        return getToda()-getTora();
    }
    public int getStopway() {
        return getAsda() - getTora();
    }
    public void setRunwayName(String runwayName) {
        this.runwayName = runwayName;
    }
    public boolean isObstaclePresent() {
        return obstaclePresent;
    }
    public void setObstaclePresent(boolean obstaclePresent) {
        this.obstaclePresent = obstaclePresent;
    }

    //constructor (no obstacle)
    Runway (String airportName, int blastProtection, int resa, int stripEnd, int tocs, int als, int tora, int toda, int asda, int lda, String runwayName){
        super(airportName, blastProtection, resa, stripEnd, tocs, als);
        setObstaclePresent(false);
        setAsda(asda);
        setRunwayName(runwayName);
        setLda(lda);
        setToda(toda);
        setTora(tora);
    }
    //constructor (obstacle present)
    Runway (String airportName, int blastProtection, int resa, int stripEnd, int tocs, int als, int tora, int toda, int asda, int lda, String runwayName,
            int obstacleHeight, int distanceFromThres){
        super(airportName, blastProtection, resa, stripEnd, tocs, als);
        setObstaclePresent(true);
        setAsda(asda);
        setRunwayName(runwayName);
        setLda(lda);
        setToda(toda);
        setTora(tora);
        setObstacleHeight(obstacleHeight);
        setDistanceFromThres(distanceFromThres);
    }

    //methods
    enum Direction {away, towards, over}

    public int calcTora(Direction s){
        if (isObstaclePresent() && (s == Direction.away)){
            return getTora() - getSignificant() - getDistanceFromThres() - (getTora()-getLda());

        }else if (isObstaclePresent() && (s == Direction.towards)){
            return getDistanceFromThres() + (getTora()-getLda()) - (getObstacleHeight()*getTocs()) - getStripEnd();
        }else {
            return getTora();
        }
    }

    public int calcLda(Direction s){
        if (!isObstaclePresent()) {
            return getLda();
        }else if (isObstaclePresent() && (s == Direction.over)){
            return getLda() - getDistanceFromThres() - getStripEnd() - (Math.max((getAls()*getObstacleHeight()), (Math.max(getResa(), getBlastProtection()))));
        }else {
            return getDistanceFromThres() - getResa() - getStripEnd();
        }
    }

    public int calcTODA(Direction s){
        if (s == Direction.away) {
            return calcTora(s) + getClearway();
        }else {
            return calcTora(s);
        }
    }

    public int calcASDA(Direction s){
        if (s == Direction.away) {
            return calcTora(s) + getStopway();
        }else {
            return calcTora(s);
        }
    }


}
