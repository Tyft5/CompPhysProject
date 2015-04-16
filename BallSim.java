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

    final static double DT =   0.01;   // ~~seconds~~

    static double t = 0; // ~~seconds~~
    static ArrayList<Ball> balls = new ArrayList<Ball>(NUMBALLS); // the list of balls
    static double bigG = 6.67384 * Math.pow(10,-11);
    static double earthM = 5.9721 * Math.pow(10,24);

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
	
	//Method for the motion of each ball. Should be relatively easily. 
	public static void ballMotion(Ball ball1){
		//First establish the gravitational force on the ball.
		double gForce = (bigG * earthM * ball1.mass)/(ball1.eRadius * ball1.eRadius);
		double accel = -gForce/ball1.mass;
		ball1.vel = ball1.vel + (accel * DT);
		ball1.pos = ball1.pos + (ball1.vel * DT);
        t += DT;
	}
	
	//So here's code to test for a collision
	public static boolean cCheck(Ball ball1, Ball ball2) {
		//Create a variable I'll set as 0, but the code will change to 1 if there is a collision, thus when this is run we can do, if bool=1, collision, if bool=0, skip.
		//I made it a public int as this means it will be available throughout the code not just this method, correct?
		if ((ball1.pos + ball1.radius) >= (ball2.pos - ball2.radius)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void collision(Ball ball1, Ball ball2) {
		//first we'll define the initial values to use in calculations so that we can modify values without a problem
		double vel1i = ball1.vel;
		double vel2i = ball2.vel;

		//I'm going to use some intermediate values for readability's sake. denom=denominator and such
		double denom = 2 * (ball2.mass + (ball1.mass * ball2.mass));
		double radicate = Math.pow((-2 * ball2.mass * (ball1.mass*vel1i - ball2.mass*vel2i)),2) - (4*(ball2.mass + ball1.mass*ball2.mass)
            *(Math.pow((ball1.mass*vel1i),2) + ball1.mass*ball2.mass*Math.pow(vel2i,2) - Math.pow(ball1.mass*vel1i + ball2.mass*vel2i,2)));
		double ratitatta = 2*ball2.mass*(ball1.mass*vel1i - ball2.mass*vel2i);
        
		ball2.vel = (ratitatta - Math.sqrt(radicate)) / denom;
		ball1.vel = (ball1.mass*vel1i + ball2.mass*vel2i - ball2.mass*ball2.vel) / ball1.mass;
		//I haven't run it to check for errors, and there's still a 5% chance this is wrong (I'll explain in person if you want), but I think this'll be a good collision code.

        //Ok so here's my next attempt, I've tested the math a couple of times. It should work now.
        ball2.vel = (2*ball1.mass*vel1i-vel2i*(ball2.mass-ball1.mass))/(ball1.mass+ball2.mass);
        ball1.vel = (ball1.mass*vel1i + ball2.mass*vel2i - balls.mass*ball2.vel) / ball1.mass;
	}

    public static boolean bounceCheck(Ball ball1) {
        return ball1.pos <= 0.005;
    }

    public static void bounce(Ball ball1) {
        ball1.vel = -ball1.vel;
    }
	
	public static void GoalCheck(Ball ball1) {
		double escapeVel = Math.sqrt((2*bigG*earthM)/ball1.eRadius);
		if (ball1.vel>=escapeVel){
			System.out.println("You escaped Mother Fucker.");
		}
	}

    public static void main(String[] args) {
        System.out.println("*insert testicle joke here*");

        populateBalls(); // Creates all the balls w/ their info

        boolean run = true;
        System.out.println(NUMBALLS);
        System.out.println(balls.get(0).pos + ", " + balls.get(1).pos + ", " + balls.get(2).pos);

        while (run) {
            // move them
            for (Ball b: balls) {
                ballMotion(b);
            }

            // bounce if needed
            for (Ball b: balls) {
                if (bounceCheck(b)) {
                    bounce(b);
                }
            }

            // if needed, do collisions
            for (int i = 0; i < balls.size()-1; i++) {
                if ( cCheck(balls.get(i), balls.get(i + 1)) ) {
                    collision(balls.get(i), balls.get(i + 1));
                }
            }

            if (balls.get(balls.size()-1).pos <= 0) {
                run = false;
            }
            System.out.println(balls.get(0).pos);
            // System.out.println(balls.get(balls.size()-1).pos);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////

    /*
    Class that represents Ball objects. The variables can be accessed directly by BallSim by calling:
        <ballName>.<variable>
        e.g. ball2.vel
    */

    private static class Ball {
        double mass;
        double radius;
        double pos;
        double vel;
		double eRadius;

        // Constructor methods for a ball. Instantiates instance variables.
        public Ball(double position) {
            this(position, 1, 1, 0);
        }

        public Ball(double position, double mass) {
            this(position, mass, 1, 0);
        }

        public Ball(double position, double mass, double radius) {
            this(position, mass, radius, 0);
        }

        public Ball(double position, double mass, double radius, double velocity) {
            this.mass = mass;
            this.radius = radius;
            this.pos = position;
            this.vel = velocity;
			this.eRadius = this.pos + 6371000;
        }
    }
}
