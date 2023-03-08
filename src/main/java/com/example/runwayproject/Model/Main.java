package com.example.runwayproject.Model;

public class Main {


    public static void main(String[] args) {
//Runway (String airportName, int blastProtection, int resa, int stripEnd, int tocs, int als, int tora, int toda, int asda, int lda, String runwayName, int obstacleHeight, int distanceFromThres)
        Runway s1L = new Runway("Heathrow", 300, 240, 60, 50, 50, 3902, 3902, 3902, 3595, "09L", 0, 0);
        Runway s1R = new Runway("Heathrow", 300, 240, 60, 50, 50, 3884, 3962, 3884, 3884, "27R",0 ,0);
        Runway s2L = new Runway("Heathrow", 300, 240, 60, 50, 50, 3660, 3660, 3660, 3660, "27L", 0, 0);
        Runway s2R = new Runway("Heathrow", 300, 240, 60, 50, 50, 3660, 3660, 3660, 3353, "09R",0 ,0);

        System.out.println("----scenario 1----");
        s1R.setObstaclePresent(true);
        s1R.setObstacleHeight(12);
        s1R.setDistanceFromThres(3646);
        s1L.setObstaclePresent(true);
        s1L.setObstacleHeight(12);
        s1L.setDistanceFromThres(-50);
        System.out.println(s1L.calcTora(Runway.Direction.away));
        System.out.println(s1L.calcASDA(Runway.Direction.away));
        System.out.println(s1L.calcTODA(Runway.Direction.away));
        System.out.println(s1L.calcLda(Runway.Direction.over));
        System.out.println(s1R.calcTora(Runway.Direction.towards));
        System.out.println(s1R.calcASDA(Runway.Direction.towards));
        System.out.println(s1R.calcTODA(Runway.Direction.towards));
        System.out.println(s1R.calcLda(Runway.Direction.towards));

        System.out.println("----scenario 2----");
        s2R.setObstaclePresent(true);
        s2R.setObstacleHeight(25);
        s2R.setDistanceFromThres(2853);
        s2L.setObstaclePresent(true);
        s2L.setObstacleHeight(25);
        s2L.setDistanceFromThres(500);
        System.out.println(s2R.calcTora(Runway.Direction.towards));
        System.out.println(s2R.calcTODA(Runway.Direction.towards));
        System.out.println(s2R.calcASDA(Runway.Direction.towards));
        System.out.println(s2R.calcLda(Runway.Direction.towards));
        System.out.println(s2L.calcTora(Runway.Direction.away));
        System.out.println(s2L.calcASDA(Runway.Direction.away));
        System.out.println(s2L.calcTODA(Runway.Direction.away));
        System.out.println(s2L.calcLda(Runway.Direction.over));

        System.out.println("----scenario 3----");
        s2R.setObstaclePresent(true);
        s2R.setObstacleHeight(15);
        s2R.setDistanceFromThres(150);
        s2L.setObstaclePresent(true);
        s2L.setObstacleHeight(15);
        s2L.setDistanceFromThres(3203);
        System.out.println(s2R.calcTora(Runway.Direction.away));
        System.out.println(s2R.calcASDA(Runway.Direction.away));
        System.out.println(s2R.calcTODA(Runway.Direction.away));
        System.out.println(s2R.calcLda(Runway.Direction.over));
        System.out.println(s2L.calcTora(Runway.Direction.towards));
        System.out.println(s2L.calcTODA(Runway.Direction.towards));
        System.out.println(s2L.calcASDA(Runway.Direction.towards));
        System.out.println(s2L.calcLda(Runway.Direction.towards));

        System.out.println("----scenario 4----");
        s1R.setObstaclePresent(true);
        s1R.setObstacleHeight(20);
        s1R.setDistanceFromThres(50);
        s1L.setObstaclePresent(true);
        s1L.setObstacleHeight(20);
        s1L.setDistanceFromThres(3546);
        System.out.println(s1L.calcTora(Runway.Direction.towards));
        System.out.println(s1L.calcTODA(Runway.Direction.towards));
        System.out.println(s1L.calcASDA(Runway.Direction.towards));
        System.out.println(s1L.calcLda(Runway.Direction.towards));
        System.out.println(s1R.calcTora(Runway.Direction.away));
        System.out.println(s1R.calcASDA(Runway.Direction.away));
        System.out.println(s1R.calcTODA(Runway.Direction.away));
        System.out.println(s1R.calcLda(Runway.Direction.over));


    }

}


