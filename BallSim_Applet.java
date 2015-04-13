import java.util.Scanner;
import java.util.ArrayList;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

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

/*
<applet code="BallSim_Applet" width=1000 height=500></applet>
*/

public class BallSim_Applet extends Applet implements ActionListener {

    // Constants. Values set for testing, non-set commented out. Constants are nice cause they're easy to see
    int NUMBALLS =        3;
    double SEPARATION =   0.1;
    double INITHEIGHT =   1;
    double LARGESTMASS =  1;
    double MASSDIFF =     0.2;
    double LARGESTRAD =   1;
    double RADIUSDIFF =   0;
    double INITVEL =      0;
        // int NUMBALLS;         // number of balls
        // double SEPARATION;    // separation of consecutive balls ~~meters~~
        // double INITHEIGHT;    // initial height of lowest ball ~~meters~~
        // double LARGESTMASS;   // the mass of the largest/bottom ball ~~kg~~
        // double MASSDIFF;      // the difference in mass between consecutive balls ~~kg~~
        // double LARGESTRAD;    // the radius of the largest/bottom ball ~~meters~~
        // double RADIUSDIFF;    // the difference in radius between consecutive balls ~~meters~~
        // double INITVEL;       // initial velocity of the balls, if we want one ~~m/s~~

    final double GRAV = 9.8;    // NOTE: GRAVITY ACC IS POSITIVE ~~m/s^2~~
    final double DT =   0.01;   // ~~seconds~~

    double t = 0; // ~~seconds~~
    ArrayList<Ball> balls = new ArrayList<Ball>(NUMBALLS); // the list of balls

    Button start, resetDefault, goToSpace, blowUpMoon, escapeSolarSystem;
    TextField numBallsText, separationText, initHeightText, largestMassText, massDiffText, largestRadText, radDiffText, initVelText;
    TextField maxHeightText, maxEnergyText, maxSpeedText;

    public void init() {
        populateBalls();

        // set up buttons, text boxes, etc.
        separationText = new TextField(8);
        separationText.addActionListener(this);
        add(separationText);
        separationText.setText("0.1");

        initHeightText = new TextField(8);
        initHeightText.addActionListener(this);
        add(initHeightText);
        initHeightText.setText("1");

        largestMassText = new TextField(8);
        largestMassText.addActionListener(this);
        add(largestMassText);
        largestMassText.setText("1");

        massDiffText = new TextField(8);
        massDiffText.addActionListener(this);
        add(massDiffText);
        massDiffText.setText("0.2");

        largestRadText = new TextField(8);
        largestRadText.addActionListener(this);
        add(largestRadText);
        largestRadText.setText("1");

        radDiffText = new TextField(8);
        radDiffText.addActionListener(this);
        add(radDiffText);
        radDiffText.setText("0");

        initVelText = new TextField(8);
        initVelText.addActionListener(this);
        add(initVelText);
        initVelText.setText("0");

        numBallsText = new TextField(8);
        numBallsText.addActionListener(this);
        add(numBallsText);
        numBallsText.setText("3");

        maxHeightText = new TextField(8);
        maxHeightText.addActionListener(this);
        add(maxHeightText);
        maxHeightText.setText("N/A");

        maxEnergyText = new TextField(8);
        maxEnergyText.addActionListener(this);
        add(maxEnergyText);
        maxEnergyText.setText("N/A");

        maxSpeedText = new TextField(8);
        maxSpeedText.addActionListener(this);
        add(maxSpeedText);
        maxSpeedText.setText("N/A");

        goToSpace = new Button("Go to space!");
        goToSpace.addActionListener(this);
        add(goToSpace);

        blowUpMoon = new Button("Go to the moon!");
        blowUpMoon.addActionListener(this);
        add(blowUpMoon);

        escapeSolarSystem = new Button("Escape the sun!");
        escapeSolarSystem.addActionListener(this);
        add(escapeSolarSystem);

        start = new Button("Start");
        start.addActionListener(this);
        add(start);

        resetDefault = new Button("Reset defaults");
        resetDefault.addActionListener(this);
        add(resetDefault);
    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);

        // Labels for buttons and instructions

        //Doing calculations
        boolean condition = true;
        while (condition) {
            for (int i = 0; i < balls.size(); i++) {
                // move ball

                if (cCheck(balls[i], balls[i+1])) {
                    collision(balls[i], balls[i+1]);
                }
            }
        }
    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source.equals(start)) {
            balls.clear();
            populateBalls();

            repaint();
        } else if (source.equals(resetDefault)) {
            balls.clear();
            populateBalls();

            NUMBALLS =     3;
            numBallsText.setText("3");
            SEPARATION =   0.1;
            separationText.setText("0.1");
            INITHEIGHT =   1;
            initHeightText.setText("1");
            LARGESTMASS =  1;
            largestMassText.setText("1");
            MASSDIFF =     0.2;
            massDiffText.setText("0.2");
            LARGESTRAD =   1;
            largestRadText.setText("1");
            RADIUSDIFF =   0;
            radDiffText.setText("0");
            INITVEL =      0;
            initVelText.setText("0");
            maxHeightText.setText("N/A");
            maxEnergyText.setText("N/A");
            maxSpeedText.setText("N/A");

            repaint();
        } else if (source.equals(goToSpace)) {
            balls.clear();
            populateBalls();

            repaint();
        } else if (source.equals(blowUpMoon)) {
            balls.clear();
            populateBalls();

            repaint();
        } else if (source.equals(escapeSolarSystem)) {
            balls.clear();
            populateBalls();

            repaint();
        } else { repaint(); }
    }

    public void populateBalls() {
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
	
	//So here's code to test for a collision
	public boolean cCheck(Ball ball1, Ball ball2) {
		//Create a variable I'll set as 0, but the code will change to 1 if there is a collision, thus when this is run we can do, if bool=1, collision, if bool=0, skip.
		//I made it a public int as this means it will be available throughout the code not just this method, correct?
		if ((ball1.pos + ball1.radius) >= (ball2.pos - ball2.radius)) {
			return true;
		}
		else {
			return false;
		}
	}
	public void collision(Ball ball1, Ball ball2) {
		//first we'll define the initial values to use in calculations so that we can modify values without a problem
		double vel1i = ball1.vel;
		double vel2i = ball2.vel;

		//I'm going to use some intermediate values for readability's sake. denom=denominator and such
		double denom = 2 * (ball2.mass + (ball1.mass * ball2.mass));
		double radicate = Math.pow((-2 * ball2.mass * (ball1.mass*vel1i - ball2.mass*vel2i)),2) - (4*(ball2.mass + ball1.mass*ball2.mass)
            *(Math.pow((ball1.mass*vel1i),2) + ball1.mass*ball2.mass*Math.pow(vel2i,2) - Math.pow(ball1.mass*vel1i + ball2.mass*vel2i,2));
		double ratitatta = 2*ball2.mass*(ball1.mass*vel1i - ball2.mass*vel2i)); //<-- added a parenthese b/c it was missing one. double to check to see where it actually goes
        
		ball2.vel = (ratitatta - Math.sqrt(radicate)) / denom;
		ball1.vel = (ball1.mass*vel1i + ball2.mass*vel2i - balls.mass*ball2.vel) / ball1.mass;
		//I haven't run it to check for errors, and there's still a 5% chance this is wrong (I'll explain in person if you want), but I think this'll be a good collision code.
	}

    public void moveBalls() {
        for (Ball b: balls) {
            // physics equations for moving ball b over timestep DT here. GRAV is a positive value fyi
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////

    /*
    Class that represents Ball objects. The variables can be accessed directly by BallSim by calling:
        <ballName>.<variable>
        e.g. ball2.velocity
    */

    private class Ball {
        double mass;
        double radius;
        double position;
        double velocity;

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
            this.position = position;
            this.velocity = velocity;
        }
    }
}
