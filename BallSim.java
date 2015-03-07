import java.util.Scanner;

/*
This is the driver class that will be doing all the stuff.
Make sure this file and Ball.java are always in the same folder, or shit will get ugly.
*/

public class BallSim {

    public static void main(String[] args) {
        Ball ball = new Ball(0);
        System.out.println("Mass: " + ball.mass);
        System.out.println("Radius: " + ball.radius);
        System.out.println("Position: " + ball.position);
        System.out.println("Velocity: " + ball.velocity);
    }
}
