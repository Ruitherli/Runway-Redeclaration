package com.example.runwayproject.Model;

public class Calculator {
    //class variables
    //constant
    static int blastProtection = 300;
    static int RESA = 240;
    static int stripEnd = 60;
    static int slope = 50;
    static int minRunDistance = 1000;
    static int minLandingDistance = 1000;

    static int getSignificant(){
        return Math.max(blastProtection, (RESA+stripEnd));
    }

    //methods
    enum Status {away, towards, over}

    public static int calcTORA (Status s, RunwayDesignator r, Obstacle obs, ObstacleLocation obsLocation){
        if (s == Status.away){
           // return r.getTora() - Calculator.getSignificant() - (Math.min(obsLocation.getDistanceThresL(), obsLocation.getDistanceThresR())) - r.getDisplacedThres();
            if (r.getRunwayDesignatorName().endsWith("L")){
                return r.getTora() - Calculator.getSignificant() - obsLocation.getDistanceThresL() - r.getDisplacedThres();
            }else{
                return r.getTora() - Calculator.getSignificant() - obsLocation.getDistanceThresR() - r.getDisplacedThres();
            }

        }else if (s == Status.towards){
            //return (Math.max(obsLocation.getDistanceThresL(), obsLocation.getDistanceThresR())) + r.getDisplacedThres() - Math.max((obs.getHeight()*Calculator.slope), RESA) - Calculator.stripEnd;
            if (r.getRunwayDesignatorName().endsWith("L")){
                return (obsLocation.getDistanceThresL() + r.getDisplacedThres() - Math.max((obs.getHeight()*Calculator.slope), RESA) - Calculator.stripEnd);
            }else {
                return (obsLocation.getDistanceThresR() + r.getDisplacedThres() - Math.max((obs.getHeight()*Calculator.slope), RESA) - Calculator.stripEnd);
            }
        }else {
            return r.getTora();
        }
    }

    public static int calcLDA(Status s, RunwayDesignator r, Obstacle obs, ObstacleLocation obsLocation){
        if (s == Status.over){
            //return r.getLda() - (Math.min(obsLocation.getDistanceThresL(), obsLocation.getDistanceThresR())) - Calculator.stripEnd - (Math.max((Calculator.slope*obs.getHeight()), (Math.max(Calculator.RESA, Calculator.blastProtection))));
            if (r.getRunwayDesignatorName().endsWith("L")){
                return r.getLda() - obsLocation.getDistanceThresL() - Calculator.stripEnd - (Math.max((Calculator.slope*obs.getHeight()), (Math.max(Calculator.RESA, Calculator.blastProtection))));
            }else {
                return r.getLda() - obsLocation.getDistanceThresR() - Calculator.stripEnd - (Math.max((Calculator.slope*obs.getHeight()), (Math.max(Calculator.RESA, Calculator.blastProtection))));
            }
        }else {
            //return (Math.max(obsLocation.getDistanceThresL(), obsLocation.getDistanceThresR())) - Calculator.RESA - Calculator.stripEnd;
            if (r.getRunwayDesignatorName().endsWith("L")){
                return obsLocation.getDistanceThresL() - Calculator.RESA - Calculator.stripEnd;
            }else {
                return obsLocation.getDistanceThresR() - Calculator.RESA - Calculator.stripEnd;
            }
        }
    }

    public static int calcTODA(Status s, RunwayDesignator r, Obstacle obs, ObstacleLocation obsLocation){
        if (s == Status.away) {
            return calcTORA(s,r,obs,obsLocation) + r.getClearway();
        }else {
            return calcTORA(s,r,obs,obsLocation);
        }
    }

    public static int calcASDA(Status s, RunwayDesignator r, Obstacle obs, ObstacleLocation obsLocation){
        if (s == Status.away) {
            return calcTORA(s,r,obs,obsLocation) + r.getStopway();
        }else {
            return calcTORA(s,r,obs,obsLocation);
        }
    }

    public static void printTORA(Status s, RunwayDesignator runwayDesignator,Obstacle obs, ObstacleLocation obsLocation){
        if (s==Status.away) {
            if (blastProtection >= (RESA + stripEnd)) {
                System.out.println("TORA = Original TORA - Blast protection - Distance from threshold - Displaced threshold");
                if (runwayDesignator.getRunwayDesignatorName().endsWith("L")) {
                    System.out.println(" = " + runwayDesignator.getTora() + " - " + blastProtection + " - " + obsLocation.getDistanceThresL() + " - " + runwayDesignator.getDisplacedThres());
                }
                else{
                    System.out.println(" = " + runwayDesignator.getTora() + " - " + blastProtection + " - " + obsLocation.getDistanceThresR() + " - " + runwayDesignator.getDisplacedThres());
                }
            }else {
                System.out.println("TORA = Original TORA - RESA - Strip end - Distance from threshold - Displaced threshold");
                if (runwayDesignator.getRunwayDesignatorName().endsWith("L")) {
                    System.out.println(" = " + runwayDesignator.getTora() + " - " + RESA + " - " + stripEnd + " - " + obsLocation.getDistanceThresL() + " - " + runwayDesignator.getDisplacedThres());
                }else{
                    System.out.println(" = " + runwayDesignator.getTora() + " - " + RESA + " - " + stripEnd + " - " + obsLocation.getDistanceThresR() + " - " + runwayDesignator.getDisplacedThres());
                }
            }
        }else if (s==Status.towards){
            if ((slope*obs.getHeight()) >= RESA){
                System.out.println("TORA = Distance from threshold - Slope calculation + Displaced Threshold - Strip end");
                if (runwayDesignator.getRunwayDesignatorName().endsWith("L")){
                    System.out.println(" = " + obsLocation.getDistanceThresL() + " - " + slope + "*" + obs.getHeight() + " + " + runwayDesignator.getDisplacedThres() + " - " + stripEnd);
                }else {
                    System.out.println(" = " + obsLocation.getDistanceThresR() + " - " + slope + "*" + obs.getHeight() + " + " + runwayDesignator.getDisplacedThres() + " - " + stripEnd);
                }
            }else{
                System.out.println("TORA = Distance from threshold - RESA + Displaced Threshold - Strip end");
                if (runwayDesignator.getRunwayDesignatorName().endsWith("L")){
                    System.out.println(" = " + obsLocation.getDistanceThresL() + " - " + RESA + " + " + runwayDesignator.getDisplacedThres() + " - " + stripEnd);
                }else {
                    System.out.println(" = " + obsLocation.getDistanceThresR() + " - " + RESA + " + " + runwayDesignator.getDisplacedThres() + " - " + stripEnd);
                }
            }
        }
        System.out.println(" = " + calcTORA(s,runwayDesignator,obs,obsLocation));
    }

    public static void printTODA(Status s, RunwayDesignator runwayDesignator,Obstacle obs, ObstacleLocation obsLocation){
        if (s==Status.away){
            System.out.println("TODA = Recalculated + ClearWay");
            System.out.println(" = " + calcTORA(Calculator.Status.away,runwayDesignator,obs,obsLocation) + " + (" + runwayDesignator.getToda() + " - " + runwayDesignator.getTora() + ")");
            System.out.println(" = " + calcTODA(Calculator.Status.away,runwayDesignator,obs,obsLocation));
        }else if (s==Status.towards){
            System.out.println("TODA = Recalculated TORA");
            System.out.println(" = " + calcTODA(Status.towards,runwayDesignator,obs,obsLocation));
        }else {
            System.out.println("status error");
        }
    }

    public static void printASDA(Status s, RunwayDesignator runwayDesignator,Obstacle obs, ObstacleLocation obsLocation){
        if (s==Status.away){
            System.out.println("ASDA = Recalculated TORA + StopWay");
            System.out.println(" = " + calcTORA(Calculator.Status.away,runwayDesignator,obs,obsLocation) + " + (" + runwayDesignator.getAsda() + " - " + runwayDesignator.getTora() + ")");
            System.out.println(" = " + calcASDA(Calculator.Status.away,runwayDesignator,obs,obsLocation));
        }else if (s==Status.towards){
            System.out.println("ASDA = Recalculated TORA");
            System.out.println(" = " + calcASDA(Status.towards,runwayDesignator,obs,obsLocation));
        }else {
            System.out.println("status error");
        }
    }

    public static void printLDA(Status s, RunwayDesignator runwayDesignator,Obstacle obs, ObstacleLocation obsLocation){
        if (s==Status.over){
            if ((slope*obs.getHeight()) >= RESA){
                System.out.println("LDA = Original LDA - Slope calculation - Distance from threshold - Strip end");
                if (runwayDesignator.getRunwayDesignatorName().endsWith("L")){
                    System.out.println(" = " + runwayDesignator.getLda() + " - " + slope + "*" + obs.getHeight() + " - " + obsLocation.getDistanceThresL() + " - " + stripEnd);
                }else {
                    System.out.println(" = " + runwayDesignator.getLda() + " - " + slope + "*" + obs.getHeight() + " - " + obsLocation.getDistanceThresR() + " - " + stripEnd);
                }
            }else{
                System.out.println("LDA = Original LDA - RESA - Distance from threshold - Strip end");
                if (runwayDesignator.getRunwayDesignatorName().endsWith("L")){
                    System.out.println(" = " + runwayDesignator.getLda() + " - " + RESA + " - " + obsLocation.getDistanceThresL() + " - " + stripEnd);
                }else {
                    System.out.println(" = " + runwayDesignator.getLda() + " - " + RESA + " - " + obsLocation.getDistanceThresR() + " - " + stripEnd);
                }
            }
            System.out.println(" = " + calcLDA(Calculator.Status.over,runwayDesignator,obs,obsLocation));
        }else if (s==Status.towards){
            if (runwayDesignator.getRunwayDesignatorName().endsWith("L")){
                System.out.println("LDA = Distance from threshold - RESA - Strip end");
                System.out.println(" = " + obsLocation.getDistanceThresL() + " - " + RESA + " - " + stripEnd);
                System.out.println(" = " + calcLDA(Status.towards,runwayDesignator,obs,obsLocation));
            }else {
                System.out.println("LDA = Distance from threshold - RESA - Strip end");
                System.out.println(" = " + obsLocation.getDistanceThresR() + " - " + RESA + " - " + stripEnd);
                System.out.println(" = " + calcLDA(Status.towards,runwayDesignator,obs,obsLocation));
            }
        }else {
            System.out.println("status error");
        }
    }


    //first, check if TORA is > min value
    //proceed if yes
    public static void calcAll(Runway r, Obstacle obs, ObstacleLocation obsLocation){
        RunwayDesignator left = r.getLeftDesignator();
        RunwayDesignator right = r.getRightDesignator();

        int newTORA_L_A = calcTORA(Status.away,left,obs,obsLocation);
        int newTORA_L_T = calcTORA(Status.towards,left,obs,obsLocation);
        int newLDA_L_O = calcLDA(Status.over,left,obs,obsLocation);
        int newLDA_L_T = calcLDA(Status.towards,left,obs,obsLocation);
        //starting from LEFT side
        //for TORA TODA ASDA
        System.out.println("Runway designator: " + left.getRunwayDesignatorName());
        System.out.println("----Take off away, landing over----");
        if (newTORA_L_A >= minRunDistance){
            printTORA(Status.away,left,obs,obsLocation);
            printTODA(Status.away,left,obs,obsLocation);
            printASDA(Status.away,left,obs,obsLocation);
        }else{
            System.out.println(left.getRunwayDesignatorName() + " IS NOT SUITABLE FOR TAKE OFF AWAY");
        }
        //for LDA
        if (newLDA_L_O >= minLandingDistance){
            printLDA(Status.over,left,obs,obsLocation);
        }else{
            System.out.println(left.getRunwayDesignatorName() + " IS NOT SUITABLE FOR LANDING OVER");
        }
        //for TORA TODA ASDA
        System.out.println("----Take off towards, landing towards----");
        if (newTORA_L_T >= minRunDistance){
            printTORA(Status.towards,left,obs,obsLocation);
            printTODA(Status.towards,left,obs,obsLocation);
            printASDA(Status.towards,left,obs,obsLocation);
        }else{
            System.out.println(left.getRunwayDesignatorName() + " IS NOT SUITABLE FOR TAKE OFF TOWARDS");
        }
        //for LDA
        if (newLDA_L_T >= minLandingDistance){
            printLDA(Status.towards,left,obs,obsLocation);
        }else{
            System.out.println(left.getRunwayDesignatorName() + " IS NOT SUITABLE FOR LANDING TOWARDS");
        }


        int newTORA_R_A = calcTORA(Status.away,right,obs,obsLocation);
        int newTORA_R_T = calcTORA(Status.towards,right,obs,obsLocation);
        int newLDA_R_O = calcLDA(Status.over,right,obs,obsLocation);
        int newLDA_R_T = calcLDA(Status.towards,right,obs,obsLocation);

        System.out.println("Runway designator: " + right.getRunwayDesignatorName());
        System.out.println("----Take off away, landing over----");
        if (newTORA_R_A >= minRunDistance){
            printTORA(Status.away,right,obs,obsLocation);
            printTODA(Status.away,right,obs,obsLocation);
            printASDA(Status.away,right,obs,obsLocation);
        }else{
            System.out.println(right.getRunwayDesignatorName() + " IS NOT SUITABLE FOR TAKE OFF AWAY");
        }
        //for LDA
        if (newLDA_R_O >= minLandingDistance){
            printLDA(Status.over,right,obs,obsLocation);
        }else{
            System.out.println(right.getRunwayDesignatorName() + " IS NOT SUITABLE FOR LANDING OVER");
        }
        //for TORA TODA ASDA
        System.out.println("----Take off towards, landing towards----");
        if (newTORA_R_T >= minRunDistance){
            printTORA(Status.towards,right,obs,obsLocation);
            printTODA(Status.towards,right,obs,obsLocation);
            printASDA(Status.towards,right,obs,obsLocation);
        }else{
            System.out.println(right.getRunwayDesignatorName() + " IS NOT SUITABLE FOR TAKE OFF TOWARDS");
        }
        //for LDA
        if (newLDA_R_T >= minLandingDistance){
            printLDA(Status.towards,right,obs,obsLocation);
        }else{
            System.out.println(right.getRunwayDesignatorName() + " IS NOT SUITABLE FOR LANDING TOWARDS");
        }

    }


    /*public static void printDistances(RunwayDesignator left, RunwayDesignator right, Obstacle obs, ObstacleLocation obsLocation){

        System.out.println(left.getRunwayDesignatorName() + " ----take off away, landing over----");
        if (blastProtection >= (RESA+stripEnd)) {
            System.out.println("TORA = Original TORA - Blast protection - Distance from threshold - Displaced threshold");
            if (obsLocation.getDistanceThresL() <= obsLocation.getDistanceThresR()){
                System.out.println(" = " + left.getTora() + " - " + blastProtection + " - " + obsLocation.getDistanceThresL() + " - " + left.getDisplacedThres());

            }else {
                System.out.println(" = " + left.getTora() + " - " + blastProtection + " - " + obsLocation.getDistanceThresR() + " - " + left.getDisplacedThres());
            }
        }else{
            System.out.println("TORA = Original TORA - RESA - Strip end - Distance from threshold - Displaced threshold");
            if (obsLocation.getDistanceThresL() <= obsLocation.getDistanceThresR()){
                System.out.println(" = " + left.getTora() + " - " + RESA + " - " + stripEnd + " - " + obsLocation.getDistanceThresL() + " - " + left.getDisplacedThres());

            }else {
                System.out.println(" = " + left.getTora() + " - " + RESA + " - " + stripEnd + " - " + obsLocation.getDistanceThresR() + " - " + left.getDisplacedThres());

            }
        }
        System.out.println(" = " + calcTORA(Calculator.Status.away,left,obs,obsLocation));
        System.out.println("ASDA = Recalculated TORA + StopWay");
        System.out.println(" = " + calcTORA(Calculator.Status.away,left,obs,obsLocation) + " + (" + left.getAsda() + " - " + left.getTora() + ")");
        System.out.println(" = " + calcASDA(Calculator.Status.away,left,obs,obsLocation));
        System.out.println("TODA = Recalculated + ClearWay");
        System.out.println(" = " + calcTORA(Calculator.Status.away,left,obs,obsLocation) + " + (" + left.getToda() + " - " + left.getTora() + ")");
        System.out.println(" = " + calcTODA(Calculator.Status.away,left,obs,obsLocation));
        if ((slope*obs.getHeight()) >= RESA){
            System.out.println("LDA = Original LDA - Slope calculation - Distance from threshold - Strip end");
            if (obsLocation.getDistanceThresL() <= obsLocation.getDistanceThresR()){
                System.out.println(" = " + left.getLda() + " - " + slope + "*" + obs.getHeight() + " - " + obsLocation.getDistanceThresL() + " - " + stripEnd);
            }else {
                System.out.println(" = " + left.getLda() + " - " + slope + "*" + obs.getHeight() + " - " + obsLocation.getDistanceThresR() + " - " + stripEnd);
            }
        }else{
            System.out.println("LDA = Original LDA - RESA - Distance from threshold - Strip end");
            if (obsLocation.getDistanceThresL() <= obsLocation.getDistanceThresR()){
                System.out.println(" = " + left.getLda() + " - " + RESA + " - " + obsLocation.getDistanceThresL() + " - " + stripEnd);
            }else {
                System.out.println(" = " + left.getLda() + " - " + RESA + " - " + obsLocation.getDistanceThresR() + " - " + stripEnd);
            }
        }
        System.out.println(" = " + calcLDA(Calculator.Status.over,left,obs,obsLocation));

        System.out.println(right.getRunwayDesignatorName() + " ----take off towards, landing towards----");
        if ((slope*obs.getHeight()) >= RESA){
            System.out.println("TORA = Distance from threshold - Slope calculation + Displaced Threshold - Strip end");
            if (obsLocation.getDistanceThresL() <= obsLocation.getDistanceThresR()){
                System.out.println(" = " + obsLocation.getDistanceThresR() + " - " + slope + "*" + obs.getHeight() + " + " + right.getDisplacedThres() + " - " + stripEnd);
            }else {
                System.out.println(" = " + obsLocation.getDistanceThresL() + " - " + slope + "*" + obs.getHeight() + " + " + right.getDisplacedThres() + " - " + stripEnd);
            }
        }else{
            System.out.println("TORA = Distance from threshold - RESA + Displaced Threshold - Strip end");
            if (obsLocation.getDistanceThresL() <= obsLocation.getDistanceThresR()){
                System.out.println(" = " + obsLocation.getDistanceThresR() + " - " + RESA + " + " + right.getDisplacedThres() + " - " + stripEnd);
            }else {
                System.out.println(" = " + obsLocation.getDistanceThresL() + " - " + RESA + " + " + right.getDisplacedThres() + " - " + stripEnd);
            }
        }
        System.out.println(" = " + calcTORA(Status.towards,right,obs,obsLocation));
        System.out.println("ASDA = Recalculated TORA");
        System.out.println(" = " + calcASDA(Status.towards,right,obs,obsLocation));
        System.out.println("TODA = Recalculated TORA");
        System.out.println(" = " + calcTODA(Status.towards,right,obs,obsLocation));
        System.out.println("LDA = Distance from threshold - RESA - Strip end");
        if (obsLocation.getDistanceThresL() <= obsLocation.getDistanceThresR()){
            System.out.println(" = " + obsLocation.getDistanceThresR() + " - " + RESA + " - " + stripEnd);

        }else {
            System.out.println(" = " + obsLocation.getDistanceThresL() + " - " + RESA + " - " + stripEnd);

        }
        System.out.println(" = " + calcLDA(Status.towards,right,obs,obsLocation));
        System.out.println();
    }*/

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

    /*public static void calcAll(Runway runway, Obstacle obs, ObstacleLocation obsLocation){
        printDistances(runway.getLeftDesignator(), runway.getRightDesignator(), obs, obsLocation);
        //printDistances(runway.getRightDesignator(), runway.getLeftDesignator(), obs, obsLocation);
        if (obsLocation.getDistanceThresL() <= obsLocation.getDistanceThresR()){
            printDistances(runway.getLeftDesignator(), runway.getRightDesignator(), obs, obsLocation);

        }else {
            printDistances(runway.getRightDesignator(), runway.getLeftDesignator(), obs, obsLocation);

        }
    }*/







}
