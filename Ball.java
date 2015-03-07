/*
Class that represents Ball objects. The variables can be accessed directly by BallSim by calling:
    <ballName>.<variable>
    e.g. ball2.velocity

This class is less important for your sake. I can show you how to do stuff in here if you like; otherwise, just let me know if there's something you want a ball to be able to do or some attribute you want it to have.
*/

public class Ball {
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
