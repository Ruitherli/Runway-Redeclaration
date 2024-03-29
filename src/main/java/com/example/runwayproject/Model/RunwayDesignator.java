package com.example.runwayproject.Model;

public class RunwayDesignator {
    //class variables
    private int tora, toda, asda, lda, displacedThres;
    /////displace threshold = tora - lda
    private String runwayDesignatorName;

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

    public int getDisplacedThres() {
        return displacedThres;
    }

    public void setDisplacedThres(int displacedThres) {
        this.displacedThres = displacedThres;
    }

    public String getRunwayDesignatorName() {
        return runwayDesignatorName;
    }
    public int getClearway() {
        return getToda()-getTora();
    }
    public int getStopway() {
        return getAsda() - getTora();
    }
    public void setRunwayDesignatorName(String runwayDesignatorName) {
        this.runwayDesignatorName = runwayDesignatorName;
    }


    //constructor (no obstacle)
    public RunwayDesignator(int tora, int toda, int asda, int lda, int displacedThres, String runwayDesignatorName){
        setAsda(asda);
        setRunwayDesignatorName(runwayDesignatorName);
        setLda(lda);
        setToda(toda);
        setTora(tora);
        setDisplacedThres(displacedThres);
    }
}
