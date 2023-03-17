package com.example.runwayproject.Model;

public class Main {


    public static void main(String[] args) {
//int tora, int toda, int asda, int lda, String runwayName
        RunwayDesignator l1 = new RunwayDesignator(3902, 3902, 3902, 3595, 306,"09L");
        RunwayDesignator r1 = new RunwayDesignator(3884, 3962, 3884, 3884, 0,"27R");
        RunwayDesignator l2 = new RunwayDesignator(3660, 3660, 3660, 3660, 0,"27L");
        RunwayDesignator r2 = new RunwayDesignator(3660, 3660, 3660, 3353, 307,"09R");

        Obstacle o1 = new Obstacle("obs 1",-12,10,10);
        ObstacleLocation location1 = new ObstacleLocation(3646,-50,0,ObstacleLocation.Direction.Centre);

        Obstacle o2 = new Obstacle("obs 2",25,10,10);
        ObstacleLocation location2 = new ObstacleLocation(2853,5000,20,ObstacleLocation.Direction.South);

        Obstacle o3 = new Obstacle("obs 3",15,10,10);
        ObstacleLocation location3 = new ObstacleLocation(150,3203,600,ObstacleLocation.Direction.North);

        Obstacle o4 = new Obstacle("obs 4",20,10,10);
        ObstacleLocation location4 = new ObstacleLocation(50,3546,-20,ObstacleLocation.Direction.North);

        Runway runway1 = new Runway("09L/27R", l1, r1);
        Runway runway2 = new Runway("27L/09R", l2, r2);

        System.out.println("----scenario 1----");
        //Calculator.calcAll(l1, r1, o1);
        Calculator.calcAll(runway1, o1, location1);

        System.out.println("----scenario 2----");
        //Calculator.calcAll(l2, r2, o2);
        Calculator.calcAll(runway2, o2, location2);

        System.out.println("----scenario 3----");
        //Calculator.calcAll(l2, r2, o3);
        Calculator.calcAll(runway2, o3, location3);

        System.out.println("----scenario 4----");
        //Calculator.calcAll(l1, r1, o4);
        Calculator.calcAll(runway1, o4, location4);

    }

}


