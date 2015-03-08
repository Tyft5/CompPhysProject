import java.util.Scanner;
import java.util.ArrayList;

/*
    This is the driver class that will be doing all the stuff.
    Make sure this file and Ball.java are always in the same folder, or shit will get ugly.

    Standard coding practice is to split stuff you want to do up into logical functional groupings and code each functionality into a method. The main() method is what is run when the code is called, and it should be fairly abstract, essentially (if your methods are named well) outlining what will happen. This makes debugging way easier and makes the code look nicer overall. Put your methods before main() and make them static (anything in the class but not in a method needs to be static).

    Constructor for a ball: Ball(double position, double mass, double radius, double velocity)
        Needs at minimum a position.

    OUTSTANDING QUESTIONS
    - How do we want to initialize constants? Terminal input, just typing them in the code, etc? Eventually some sort of input would be nice for testing different parameters.

    TO DO
    - Method to move balls. Maybe multiple with different conditions for different end goals (height, speed, etc)?
    - Method to calculate energy(s)
    - User interface
    - Develop consistent theory of quantum love

 */

    // side note: I comment the hell out of my code. You don't have to stick to my conventions if you don't want to. Just as long as we can both tell what's going on.

public class BallSim {

    // Constants. Values set for testing, non-set commented out. Constants are nice cause they're easy to see
    final static int NUMBALLS =        3;
    final static double SEPARATION =   0.1;
    final static double INITHEIGHT =   1;
    final static double LARGESTMASS =  1;
    final static double MASSDIFF =     0.2;
    final static double LARGESTRAD =   1;
    final static double RADIUSDIFF =   0;
    final static double INITVEL =      0;
        // final static int NUMBALLS;         // number of balls
        // final static double SEPARATION;    // separation of consecutive balls ~~meters~~
        // final static double INITHEIGHT;    // initial height of lowest ball ~~meters~~
        // final static double LARGESTMASS;   // the mass of the largest/bottom ball ~~kg~~
        // final static double MASSDIFF;      // the difference in mass between consecutive balls ~~kg~~
        // final static double LARGESTRAD;    // the radius of the largest/bottom ball ~~meters~~
        // final static double RADIUSDIFF;    // the difference in radius between consecutive balls ~~meters~~
        // final static double INITVEL;       // initial velocity of the balls, if we want one ~~m/s~~

    final static double GRAV = 9.8;    // NOTE: GRAVITY ACC IS POSITIVE ~~m/s^2~~
    final static double DT =   0.01;   // ~~seconds~~

    static double t = 0; // ~~seconds~~
    static ArrayList<Ball> balls = new ArrayList<Ball>(NUMBALLS); // the list of balls

    public static void populateBalls() {
        // Create the balls and populate the arraylist
        double pos = INITHEIGHT;
        double mass = LARGESTMASS;
        double radius = LARGESTRAD;
        double vel = INITVEL;
        for (int b = 0; b < NUMBALLS; b++) {
            Ball newBall = new Ball(pos, mass, radius, vel);
            balls.add(newBall);
            pos += radius;
            pos += SEPARATION;
            radius -= RADIUSDIFF;       // Change shit for next ball
            mass -= MASSDIFF;
            pos += radius;
        }
    }

    public static void main(String[] args) {
        System.out.println("*insert testicle joke here*");

        populateBalls(); // Creates all the balls w/ they're info
    }
}
