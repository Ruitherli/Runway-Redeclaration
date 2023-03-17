package com.example.runwayproject.Model;

public class ObstacleLocation {
    //variables
    private int distanceThresR, distanceThresL, distanceFromCenterline;
    private Direction direction;
    enum Direction {North, South, Centre}

    //getter setter

    public int getDistanceThresR() {
        return distanceThresR;
    }

    public void setDistanceThresR(int distanceThresR) {
        this.distanceThresR = distanceThresR;
    }

    public int getDistanceThresL() {
        return distanceThresL;
    }

    public void setDistanceThresL(int distanceThresL) {
        this.distanceThresL = distanceThresL;
    }

    public int getDistanceFromCenterline() {
        return distanceFromCenterline;
    }

    public void setDistanceFromCenterline(int distanceFromCenterline) {
        this.distanceFromCenterline = distanceFromCenterline;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //constructor
    public ObstacleLocation(int distanceThresR, int distanceThresL, int distanceFromCenterline, Direction direction) {
        this.distanceThresR = distanceThresR;
        this.distanceThresL = distanceThresL;
        this.distanceFromCenterline = distanceFromCenterline;
        this.direction = direction;
    }
}
