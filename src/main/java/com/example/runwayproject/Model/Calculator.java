package com.example.runwayproject.Model;

public class Calculator {
    //class variables
    //constant
    static int blastProtection = 300;
    static int RESA = 240;
    static int stripEnd = 60;
    static int slope = 50;

    static int getSignificant(){
        return Math.max(blastProtection, (RESA+stripEnd));
    }

    //methods
    enum Status {away, towards, over}

    public static int calcTORA (Status s, RunwayDesignator r, Obstacle obs){
        if (s == Status.away){
            return r.getTora() - Calculator.getSignificant() - (Math.min(obs.getDistanceThresL(), obs.getDistanceThresR())) - r.getDisplacedThres();

        }else if (s == Status.towards){
            return (Math.max(obs.getDistanceThresL(), obs.getDistanceThresR())) + r.getDisplacedThres() - Math.max((obs.getHeight()*Calculator.slope), RESA) - Calculator.stripEnd;
        }else {
            return r.getTora();
        }
    }

    public static int calcLDA(Status s, RunwayDesignator r, Obstacle obs){
        if (s == Status.over){
            return r.getLda() - (Math.min(obs.getDistanceThresL(), obs.getDistanceThresR())) - Calculator.stripEnd - (Math.max((Calculator.slope*obs.getHeight()), (Math.max(Calculator.RESA, Calculator.blastProtection))));
        }else {
            return (Math.max(obs.getDistanceThresL(), obs.getDistanceThresR())) - Calculator.RESA - Calculator.stripEnd;
        }
    }

    public static int calcTODA(Status s, RunwayDesignator r, Obstacle obs){
        if (s == Status.away) {
            return calcTORA(s,r,obs) + r.getClearway();
        }else {
            return calcTORA(s,r,obs);
        }
    }

    public static int calcASDA(Status s, RunwayDesignator r, Obstacle obs){
        if (s == Status.away) {
            return calcTORA(s,r,obs) + r.getStopway();
        }else {
            return calcTORA(s,r,obs);
        }
    }

    public static void printDistances(RunwayDesignator left, RunwayDesignator right, Obstacle obs){

        System.out.println(left.getRunwayDesignatorName() + " ----take off away, landing over----");
        if (blastProtection >= (RESA+stripEnd)) {
            System.out.println("TORA = Original TORA - Blast protection - Distance from threshold - Displaced threshold");
            if (obs.getDistanceThresL() <= obs.getDistanceThresR()){
                System.out.println(" = " + left.getTora() + " - " + blastProtection + " - " + obs.getDistanceThresL() + " - " + left.getDisplacedThres());

            }else {
                System.out.println(" = " + left.getTora() + " - " + blastProtection + " - " + obs.getDistanceThresR() + " - " + left.getDisplacedThres());
            }
        }else{
            System.out.println("TORA = Original TORA - RESA - Strip end - Distance from threshold - Displaced threshold");
            if (obs.getDistanceThresL() <= obs.getDistanceThresR()){
                System.out.println(" = " + left.getTora() + " - " + RESA + " - " + stripEnd + " - " + obs.getDistanceThresL() + " - " + left.getDisplacedThres());

            }else {
                System.out.println(" = " + left.getTora() + " - " + RESA + " - " + stripEnd + " - " + obs.getDistanceThresR() + " - " + left.getDisplacedThres());

            }
        }
        System.out.println(" = " + calcTORA(Calculator.Status.away,left,obs));
        System.out.println("ASDA = Recalculated TORA + StopWay");
        System.out.println(" = " + calcTORA(Calculator.Status.away,left,obs) + " + (" + left.getAsda() + " - " + left.getTora() + ")");
        System.out.println(" = " + calcASDA(Calculator.Status.away,left,obs));
        System.out.println("TODA = Recalculated + ClearWay");
        System.out.println(" = " + calcTORA(Calculator.Status.away,left,obs) + " + (" + left.getToda() + " - " + left.getTora() + ")");
        System.out.println(" = " + calcTODA(Calculator.Status.away,left,obs));
        if ((slope*obs.getHeight()) >= RESA){
            System.out.println("LDA = Original LDA - Slope calculation - Distance from threshold - Strip end");
            if (obs.getDistanceThresL() <= obs.getDistanceThresR()){
                System.out.println(" = " + left.getLda() + " - " + slope + "*" + obs.getHeight() + " - " + obs.getDistanceThresL() + " - " + stripEnd);
            }else {
                System.out.println(" = " + left.getLda() + " - " + slope + "*" + obs.getHeight() + " - " + obs.getDistanceThresR() + " - " + stripEnd);
            }
        }else{
            System.out.println("LDA = Original LDA - RESA - Distance from threshold - Strip end");
            if (obs.getDistanceThresL() <= obs.getDistanceThresR()){
                System.out.println(" = " + left.getLda() + " - " + RESA + " - " + obs.getDistanceThresL() + " - " + stripEnd);
            }else {
                System.out.println(" = " + left.getLda() + " - " + RESA + " - " + obs.getDistanceThresR() + " - " + stripEnd);
            }
        }
        System.out.println(" = " + calcLDA(Calculator.Status.over,left,obs));

        System.out.println(right.getRunwayDesignatorName() + " ----take off towards, landing towards----");
        if ((slope*obs.getHeight()) >= RESA){
            System.out.println("TORA = Distance from threshold - Slope calculation + Displaced Threshold - Strip end");
            if (obs.getDistanceThresL() <= obs.getDistanceThresR()){
                System.out.println(" = " + obs.getDistanceThresR() + " - " + slope + "*" + obs.getHeight() + " + " + right.getDisplacedThres() + " - " + stripEnd);
            }else {
                System.out.println(" = " + obs.getDistanceThresL() + " - " + slope + "*" + obs.getHeight() + " + " + right.getDisplacedThres() + " - " + stripEnd);
            }
        }else{
            System.out.println("TORA = Distance from threshold - RESA + Displaced Threshold - Strip end");
            if (obs.getDistanceThresL() <= obs.getDistanceThresR()){
                System.out.println(" = " + obs.getDistanceThresR() + " - " + RESA + " + " + right.getDisplacedThres() + " - " + stripEnd);
            }else {
                System.out.println(" = " + obs.getDistanceThresL() + " - " + RESA + " + " + right.getDisplacedThres() + " - " + stripEnd);
            }
        }
        System.out.println(" = " + calcTORA(Status.towards,right,obs));
        System.out.println("ASDA = Recalculated TORA");
        System.out.println(" = " + calcASDA(Status.towards,right,obs));
        System.out.println("TODA = Recalculated TORA");
        System.out.println(" = " + calcTODA(Status.towards,right,obs));
        System.out.println("LDA = Distance from threshold - RESA - Strip end");
        if (obs.getDistanceThresL() <= obs.getDistanceThresR()){
            System.out.println(" = " + obs.getDistanceThresR() + " - " + RESA + " - " + stripEnd);

        }else {
            System.out.println(" = " + obs.getDistanceThresL() + " - " + RESA + " - " + stripEnd);

        }
        System.out.println(" = " + calcLDA(Status.towards,right,obs));
        System.out.println();
    }

    //determine the obstacle position (comparing distance threshold from left and right)
    //shorter distance threshold : take-off away, landing over
    //longer distance threshold : take-off towards, landing towards
    /*public static void calcAll(RunwayDesignator left, RunwayDesignator right, Obstacle obs){
        if (obs.getDistanceThresL() <= obs.getDistanceThresR()){
            printDistances(left,right,obs);

        }else {
            printDistances(right, left, obs);

        }
    }*/

    public static void calcAll(Runway runway, Obstacle obs){
        if (obs.getDistanceThresL() <= obs.getDistanceThresR()){
            printDistances(runway.getLeftDesignator(), runway.getRightDesignator(), obs);

        }else {
            printDistances(runway.getRightDesignator(), runway.getLeftDesignator(), obs);

        }
    }







}
