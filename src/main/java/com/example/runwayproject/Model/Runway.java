package com.example.runwayproject.Model;

public class Runway {
    //class variables
    private String runwayName;
    private RunwayDesignator left,right;

    //getter setter
    public String getRunwayName() {
        return runwayName;
    }

    public void setRunwayName(String runwayName) {
        this.runwayName = runwayName;
    }

    public RunwayDesignator getLeftDesignator() {
        return left;
    }

    public void setLeft(RunwayDesignator left) {
        this.left = left;
    }

    public RunwayDesignator getRightDesignator() {
        return right;
    }

    public void setRight(RunwayDesignator right) {
        this.right = right;
    }

    public Runway(String runwayName, RunwayDesignator left, RunwayDesignator right) {
        this.runwayName = runwayName;
        this.left = left;
        this.right = right;
    }
}
