package com.example.runwayproject.Model;

public class Obstacle {
    //variables
    String obstacleName;
    int height, length, width;/*, distanceThresR, distanceThresL, distanceFromCenterline;
    Direction direction;
    enum Direction {North, South, Centre}*/

    //getter setter

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /*public int getDistanceThresR() {
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
    }*/

    public String getObstacleName() {
        return obstacleName;
    }

    public void setObstacleName(String obstacleName) {
        this.obstacleName = obstacleName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Obstacle(String obstacleName, int height, int length, int width) {
        this.obstacleName = obstacleName;
        this.height = height;
        this.length = length;
        this.width = width;
        /*this.distanceThresR = distanceThresR;
        this.distanceThresL = distanceThresL;
        this.distanceFromCenterline = distanceFromCenterline;
        this.direction = direction;*/
    }
}
